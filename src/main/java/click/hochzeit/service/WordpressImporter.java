package click.hochzeit.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import click.hochzeit.domain.Profile;
import click.hochzeit.domain.Statistic;
import click.hochzeit.domain.enumeration.Gender;
import click.hochzeit.domain.enumeration.ProPackage;
import click.hochzeit.repository.StatisticRepository;

@Service
@Transactional
public class WordpressImporter {

	private static final Logger log = LoggerFactory.getLogger(WordpressImporter.class);

	private final ProfileService profileService;
	
	private final StatisticRepository statisticRepository;
	
	private JdbcTemplate wordpressJdbcTemplate;


	
	public WordpressImporter(ProfileService profileService,
			@Qualifier("wordpressJdbcTemplate") JdbcTemplate wordpressJdbcTemplate, StatisticRepository statisticRepository) {
				
		this.wordpressJdbcTemplate = wordpressJdbcTemplate;
		this.profileService = profileService;
		this.statisticRepository = statisticRepository;


	}
	
	public List<String> getFeatures(ProPackage proPackage) {
		
		List<String> featureList = new ArrayList<>();
		
				
		if(ProPackage.FREE.equals(proPackage)) {
			return featureList;
		}		
		
		featureList.add("pro_slider");
		featureList.add("no_banners_header");
		featureList.add("customer_quote");
		featureList.add("show_highlights");
		featureList.add("show_backling");
		featureList.add("show_phone");
		
		if(ProPackage.PRO_STANDARD.equals(proPackage)) {
			return featureList;
		}
		
		featureList.add("pro_faq_enabled");
		featureList.add("pro_portfolio_images");
		featureList.add("pro_location_galleries");
		featureList.add("dl_marketing");
		featureList.add("networking");
		featureList.add("social_links");
		featureList.add("multiple_locations");
		featureList.add("view_statistics");
		featureList.add("banner_lists");
		featureList.add("banner_landingpage");
		featureList.add("landing_page");
		
		if(ProPackage.PRO_ADVANCED.equals(proPackage)) {
			return featureList;
		}
		
		//VIP features
		featureList.add("show_diamond");
		featureList.add("mail_recommendations");
		
		return featureList;
		
	}
	
//	public void storeFeatures() {
//		
//		List<Feature> features = getFeatures(ProPackage.VIP).stream().map((featureName) -> new Feature(featureName)).collect(Collectors.toList());
//		for (Feature feature : features) {
//			System.out.println(feature);
//		}
//		featureRepository.save(features);
//	}
	
//	public static void main(String[] args) {
//		new WordpressImporter(null, null, null, null).storeFeatures();
//	}
	
	public String importWordpressDB(Integer limit) {
		
		log.debug("Importing " + limit + " profiles from WP");
		Integer rowsImported = 0;

		try {

			//TODO: fetch all filters from post_metadata
			String sql = "SELECT " +
					"    p.ID," +
					"    p.post_author, p.post_date," +
					"    p.post_modified," +
					"    p.post_status, "+
					"    p.post_title," +
					"    p.post_name," +
					"    p.comment_count," +
					"    u.ID AS user_id," +
					"    u.display_name," +
					"    u.user_email," +
					"    u.user_registered," +
					"    user_url," +
					"    m.agb_check," +
					"    m.package," +
					"    m.branch," +
					"    m.gender," +
					"    m.fname," +
					"    m.lname," +
					"    m.phone," +
					"    m.street," +
					"    m.city," +
					"    m.postcode," +
					"    m.score," +
					"    m.no_of_faq," +
					"    m.no_of_galleries," +
					"    m.no_of_inquiries," +
					"    m.no_of_linked_provider," +
					"    m.no_of_portfolio_img, " +
					"    pm.meta_value post_views_count " +
					"FROM" +
					"    wp_posts p," +
					"    wp_users u," +
					"    wp_master_list m," +
					"    wp_postmeta pm " +
					"WHERE" +
					"    p.post_type = 'fotograf'" +
					"    AND p.post_status = 'publish'" +
					"    AND u.ID = p.post_author" +
					"    AND m.profile_id = p.ID" +
					"    AND pm.post_id = p.ID" +
					"    AND pm.meta_key = 'post_views_count' " +
					"order by post_modified desc " + 
					"LIMIT ?;";		
			
			// RowMapper<Profile> rowMapper = (rs, rowNum) -> {

			rowsImported = wordpressJdbcTemplate.query(sql, new Integer[] { limit }, (rs, rowNum) -> {
				log.debug("Mapping profile :" + rs.getLong("ID"));
				
				Profile profile = new Profile();
				profile.setId(rs.getLong("ID"));
				profile.setCreatedDate(getZonedDateTime(rs, "user_registered"));
				profile.setLastUpdatedDate(getZonedDateTime(rs, "post_modified"));
				profile.setTitle(rs.getString("post_title"));
				profile.setFirstName(rs.getString("fname"));
				profile.setLastName(rs.getString("lname"));
				profile.setPhoneNumber(rs.getString("phone"));
				// profile.setDisplayName, setPublishedDate, setUrl
				profile.setPublished(("publish".equals(rs.getString("post_status"))));
				profile.setAgbCheck(rs.getBoolean("agb_check"));
				profile.setScore(rs.getLong("score"));
				profile.setEmail(rs.getString("user_email"));
				profile.setBranch(rs.getString("branch"));
				profile.setStreet(rs.getString("street"));
				profile.setCity(rs.getString("city"));
				profile.setPostCode(rs.getString("postcode"));
				profile.setCountry("Österreich");

				if ("Herr".equals(rs.getString("gender"))) {
					profile.setGender(Gender.M);
				} else if ("Frau".equals(rs.getString("gender"))) {
					profile.setGender(Gender.F);
				}

			
				if (rs.getString("package") == null || rs.getString("package").length() < 1) {
					profile.setProPackage(ProPackage.FREE);										
				} else {
					profile.setProPackage(ProPackage.PRO_ADVANCED);
				}
				
				// map features
				//Set<Feature> features = getFeaturesFromDB(profile.getProPackage());
				
				//log.debug("Assigning " + features.size() + " features" );
				
				String featureStr = String.join(",", getFeatures(profile.getProPackage()));
				
				profile.setFeatureStr(featureStr);
				
				//profile.setFeatures(features);
				
				
				String imgUrl = getImageFromWp(profile.getId());
				profile.setImgUrl(imgUrl);
				profile = profileService.save(profile);

//				boolean profileExists = profileRepository.exists(profile.getId());
//
//				if (profileExists) {
//					log.debug("merging profile");
//					profileRepository.save(profile);
//				} else {
//					log.debug("creating profile");
//					profileRepository.saveAndFlush(profile);
//				}

				Statistic statistic = new Statistic();

				statistic.setId(profile.getId());

				statistic.setProfile(profile);

				statistic.setNoOfFag(rs.getInt("no_of_faq"));
				statistic.setNoOfGalleries(rs.getInt("no_of_galleries"));
				statistic.setNoOfInquiries(rs.getInt("no_of_inquiries"));
				statistic.setNoOfLinkedProvider(rs.getInt("no_of_linked_provider"));
				statistic.setNoOfPortfolioImg(rs.getInt("no_of_portfolio_img"));
				statistic.setViewsTotalWP(Integer.parseInt(rs.getString("post_views_count")));

				statisticRepository.save(statistic);

				return 1; // we don't need the profile list any more, we simply
							// count the rows imported.
			}).stream().mapToInt(Integer::intValue).sum();

			// List<Profile> profileList = wordpressJdbcTemplate.query(sql,
			// rowMapper);

			// profileList.forEach(profile -> log.debug("Profile stored: " +
			// profile));

			// for (Profile profile : profileList) {
			// log.debug("Storing profile: " + profile.toString());
			// profileRepository.save(profile);
			// //System.out.println(entityManager.merge(profile));
			//
			// }
		} catch (Exception e) {
			log.error("Could not import data from wordpress", e);
			return "error";
		}

		return "success (" + rowsImported + ")";
	}

	private String getImageFromWp(Long profileId) {
		
		String sql = "SELECT p.guid FROM wp_postmeta pm, wp_posts p WHERE p.post_parent = ? AND pm.meta_key = '_thumbnail_id' AND p.ID = pm.meta_value";		

		try{
		return wordpressJdbcTemplate.queryForObject(sql, new Long[] { profileId }, String.class);
		} 
		catch (EmptyResultDataAccessException e) {
		if(log.isDebugEnabled()){
			log.debug(e.toString());
		}
	}
		return null;
		
		// return wordpressJdbcTemplate.queryForObject(sql, new Long[] { profileId }, (rs, rowNum) -> {
		// 	log.debug("Image loaded :" + rs.getString("guid"));
		// 	return rs.getString("guid");
		// });		
	}

//	private Set<Feature> getFeaturesFromDB(ProPackage proPackage) {
//		List<String> featureStr = this.getFeatures(proPackage);
//		List<Feature> allFeatures = featureRepository.findAll();
//		
//		Set<Feature> featuresOfPackage = new HashSet<>();
//		for (Feature feature : allFeatures) {
//			if (featureStr.contains(feature.getName())) {
//				featuresOfPackage.add(feature);
//			}
//		}
//	return featuresOfPackage;
//}

	private ZonedDateTime getZonedDateTime(ResultSet rs, String column) throws SQLException {
		if (rs.getTimestamp(column) == null) {
			return null;
		}
		return rs.getTimestamp(column).toLocalDateTime().atZone(ZoneId.of("Europe/Vienna"));
	}
}
