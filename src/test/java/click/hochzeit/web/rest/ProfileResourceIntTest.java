package click.hochzeit.web.rest;

import click.hochzeit.Hochzeitclick11App;

import click.hochzeit.domain.Profile;
import click.hochzeit.domain.Feature;
import click.hochzeit.repository.ProfileRepository;
import click.hochzeit.service.ProfileService;
import click.hochzeit.repository.search.ProfileSearchRepository;
import click.hochzeit.web.rest.errors.ExceptionTranslator;
import click.hochzeit.service.dto.ProfileCriteria;
import click.hochzeit.service.ProfileQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static click.hochzeit.web.rest.TestUtil.sameInstant;
import static click.hochzeit.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import click.hochzeit.domain.enumeration.ProPackage;
import click.hochzeit.domain.enumeration.Gender;
/**
 * Test class for the ProfileResource REST controller.
 *
 * @see ProfileResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Hochzeitclick11App.class)
public class ProfileResourceIntTest {

    private static final ZonedDateTime DEFAULT_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_LAST_UPDATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Boolean DEFAULT_PUBLISHED = false;
    private static final Boolean UPDATED_PUBLISHED = true;

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final String DEFAULT_BRANCH = "AAAAAAAAAA";
    private static final String UPDATED_BRANCH = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final Long DEFAULT_SCORE = 1L;
    private static final Long UPDATED_SCORE = 2L;

    private static final ProPackage DEFAULT_PRO_PACKAGE = ProPackage.FREE;
    private static final ProPackage UPDATED_PRO_PACKAGE = ProPackage.PRO_STANDARD;

    private static final Long DEFAULT_LNG = 1L;
    private static final Long UPDATED_LNG = 2L;

    private static final Long DEFAULT_LAT = 1L;
    private static final Long UPDATED_LAT = 2L;

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_STATE = "BBBBBBBBBB";

    private static final String DEFAULT_STREET = "AAAAAAAAAA";
    private static final String UPDATED_STREET = "BBBBBBBBBB";

    private static final String DEFAULT_POST_CODE = "AAAAAAAAAA";
    private static final String UPDATED_POST_CODE = "BBBBBBBBBB";

    private static final Gender DEFAULT_GENDER = Gender.F;
    private static final Gender UPDATED_GENDER = Gender.M;

    private static final Boolean DEFAULT_AGB_CHECK = false;
    private static final Boolean UPDATED_AGB_CHECK = true;

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_LOC_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_LOC_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_LOC_GEO = "AAAAAAAAAA";
    private static final String UPDATED_LOC_GEO = "BBBBBBBBBB";

    private static final String DEFAULT_LOC_CAPACITY = "AAAAAAAAAA";
    private static final String UPDATED_LOC_CAPACITY = "BBBBBBBBBB";

    private static final String DEFAULT_SP_AVAILABLE_REGION = "AAAAAAAAAA";
    private static final String UPDATED_SP_AVAILABLE_REGION = "BBBBBBBBBB";

    private static final String DEFAULT_FEATURE_STR = "AAAAAAAAAA";
    private static final String UPDATED_FEATURE_STR = "BBBBBBBBBB";

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private ProfileSearchRepository profileSearchRepository;

    @Autowired
    private ProfileQueryService profileQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProfileMockMvc;

    private Profile profile;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProfileResource profileResource = new ProfileResource(profileService, profileQueryService);
        this.restProfileMockMvc = MockMvcBuilders.standaloneSetup(profileResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Profile createEntity(EntityManager em) {
        Profile profile = new Profile()
            .createdDate(DEFAULT_CREATED_DATE)
            .lastUpdatedDate(DEFAULT_LAST_UPDATED_DATE)
            .published(DEFAULT_PUBLISHED)
            .url(DEFAULT_URL)
            .branch(DEFAULT_BRANCH)
            .title(DEFAULT_TITLE)
            .email(DEFAULT_EMAIL)
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .score(DEFAULT_SCORE)
            .proPackage(DEFAULT_PRO_PACKAGE)
            .lng(DEFAULT_LNG)
            .lat(DEFAULT_LAT)
            .country(DEFAULT_COUNTRY)
            .city(DEFAULT_CITY)
            .state(DEFAULT_STATE)
            .street(DEFAULT_STREET)
            .postCode(DEFAULT_POST_CODE)
            .gender(DEFAULT_GENDER)
            .agbCheck(DEFAULT_AGB_CHECK)
            .status(DEFAULT_STATUS)
            .locType(DEFAULT_LOC_TYPE)
            .locGeo(DEFAULT_LOC_GEO)
            .locCapacity(DEFAULT_LOC_CAPACITY)
            .spAvailableRegion(DEFAULT_SP_AVAILABLE_REGION)
            .featureStr(DEFAULT_FEATURE_STR);
        return profile;
    }

    @Before
    public void initTest() {
        profileSearchRepository.deleteAll();
        profile = createEntity(em);
    }

    @Test
    @Transactional
    public void createProfile() throws Exception {
        int databaseSizeBeforeCreate = profileRepository.findAll().size();

        // Create the Profile
        restProfileMockMvc.perform(post("/api/profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profile)))
            .andExpect(status().isCreated());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeCreate + 1);
        Profile testProfile = profileList.get(profileList.size() - 1);
        assertThat(testProfile.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProfile.getLastUpdatedDate()).isEqualTo(DEFAULT_LAST_UPDATED_DATE);
        assertThat(testProfile.isPublished()).isEqualTo(DEFAULT_PUBLISHED);
        assertThat(testProfile.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testProfile.getBranch()).isEqualTo(DEFAULT_BRANCH);
        assertThat(testProfile.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testProfile.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testProfile.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testProfile.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testProfile.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testProfile.getScore()).isEqualTo(DEFAULT_SCORE);
        assertThat(testProfile.getProPackage()).isEqualTo(DEFAULT_PRO_PACKAGE);
        assertThat(testProfile.getLng()).isEqualTo(DEFAULT_LNG);
        assertThat(testProfile.getLat()).isEqualTo(DEFAULT_LAT);
        assertThat(testProfile.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testProfile.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testProfile.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testProfile.getStreet()).isEqualTo(DEFAULT_STREET);
        assertThat(testProfile.getPostCode()).isEqualTo(DEFAULT_POST_CODE);
        assertThat(testProfile.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testProfile.isAgbCheck()).isEqualTo(DEFAULT_AGB_CHECK);
        assertThat(testProfile.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testProfile.getLocType()).isEqualTo(DEFAULT_LOC_TYPE);
        assertThat(testProfile.getLocGeo()).isEqualTo(DEFAULT_LOC_GEO);
        assertThat(testProfile.getLocCapacity()).isEqualTo(DEFAULT_LOC_CAPACITY);
        assertThat(testProfile.getSpAvailableRegion()).isEqualTo(DEFAULT_SP_AVAILABLE_REGION);
        assertThat(testProfile.getFeatureStr()).isEqualTo(DEFAULT_FEATURE_STR);

        // Validate the Profile in Elasticsearch
        Profile profileEs = profileSearchRepository.findOne(testProfile.getId());
        assertThat(testProfile.getCreatedDate()).isEqualTo(testProfile.getCreatedDate());
        assertThat(testProfile.getLastUpdatedDate()).isEqualTo(testProfile.getLastUpdatedDate());
        assertThat(profileEs).isEqualToIgnoringGivenFields(testProfile, "createdDate", "lastUpdatedDate");
    }

    @Test
    @Transactional
    public void createProfileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = profileRepository.findAll().size();

        // Create the Profile with an existing ID
        profile.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProfileMockMvc.perform(post("/api/profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profile)))
            .andExpect(status().isBadRequest());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllProfiles() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList
        restProfileMockMvc.perform(get("/api/profiles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(profile.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE))))
            .andExpect(jsonPath("$.[*].published").value(hasItem(DEFAULT_PUBLISHED.booleanValue())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
            .andExpect(jsonPath("$.[*].branch").value(hasItem(DEFAULT_BRANCH.toString())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE.intValue())))
            .andExpect(jsonPath("$.[*].proPackage").value(hasItem(DEFAULT_PRO_PACKAGE.toString())))
            .andExpect(jsonPath("$.[*].lng").value(hasItem(DEFAULT_LNG.intValue())))
            .andExpect(jsonPath("$.[*].lat").value(hasItem(DEFAULT_LAT.intValue())))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
            .andExpect(jsonPath("$.[*].street").value(hasItem(DEFAULT_STREET.toString())))
            .andExpect(jsonPath("$.[*].postCode").value(hasItem(DEFAULT_POST_CODE.toString())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].agbCheck").value(hasItem(DEFAULT_AGB_CHECK.booleanValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].locType").value(hasItem(DEFAULT_LOC_TYPE.toString())))
            .andExpect(jsonPath("$.[*].locGeo").value(hasItem(DEFAULT_LOC_GEO.toString())))
            .andExpect(jsonPath("$.[*].locCapacity").value(hasItem(DEFAULT_LOC_CAPACITY.toString())))
            .andExpect(jsonPath("$.[*].spAvailableRegion").value(hasItem(DEFAULT_SP_AVAILABLE_REGION.toString())))
            .andExpect(jsonPath("$.[*].featureStr").value(hasItem(DEFAULT_FEATURE_STR.toString())));
    }

    @Test
    @Transactional
    public void getProfile() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get the profile
        restProfileMockMvc.perform(get("/api/profiles/{id}", profile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(profile.getId().intValue()))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)))
            .andExpect(jsonPath("$.lastUpdatedDate").value(sameInstant(DEFAULT_LAST_UPDATED_DATE)))
            .andExpect(jsonPath("$.published").value(DEFAULT_PUBLISHED.booleanValue()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()))
            .andExpect(jsonPath("$.branch").value(DEFAULT_BRANCH.toString()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER.toString()))
            .andExpect(jsonPath("$.score").value(DEFAULT_SCORE.intValue()))
            .andExpect(jsonPath("$.proPackage").value(DEFAULT_PRO_PACKAGE.toString()))
            .andExpect(jsonPath("$.lng").value(DEFAULT_LNG.intValue()))
            .andExpect(jsonPath("$.lat").value(DEFAULT_LAT.intValue()))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()))
            .andExpect(jsonPath("$.street").value(DEFAULT_STREET.toString()))
            .andExpect(jsonPath("$.postCode").value(DEFAULT_POST_CODE.toString()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.agbCheck").value(DEFAULT_AGB_CHECK.booleanValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.locType").value(DEFAULT_LOC_TYPE.toString()))
            .andExpect(jsonPath("$.locGeo").value(DEFAULT_LOC_GEO.toString()))
            .andExpect(jsonPath("$.locCapacity").value(DEFAULT_LOC_CAPACITY.toString()))
            .andExpect(jsonPath("$.spAvailableRegion").value(DEFAULT_SP_AVAILABLE_REGION.toString()))
            .andExpect(jsonPath("$.featureStr").value(DEFAULT_FEATURE_STR.toString()));
    }

    @Test
    @Transactional
    public void getAllProfilesByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where createdDate equals to DEFAULT_CREATED_DATE
        defaultProfileShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the profileList where createdDate equals to UPDATED_CREATED_DATE
        defaultProfileShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllProfilesByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultProfileShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the profileList where createdDate equals to UPDATED_CREATED_DATE
        defaultProfileShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllProfilesByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where createdDate is not null
        defaultProfileShouldBeFound("createdDate.specified=true");

        // Get all the profileList where createdDate is null
        defaultProfileShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllProfilesByCreatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where createdDate greater than or equals to DEFAULT_CREATED_DATE
        defaultProfileShouldBeFound("createdDate.greaterOrEqualThan=" + DEFAULT_CREATED_DATE);

        // Get all the profileList where createdDate greater than or equals to UPDATED_CREATED_DATE
        defaultProfileShouldNotBeFound("createdDate.greaterOrEqualThan=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllProfilesByCreatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where createdDate less than or equals to DEFAULT_CREATED_DATE
        defaultProfileShouldNotBeFound("createdDate.lessThan=" + DEFAULT_CREATED_DATE);

        // Get all the profileList where createdDate less than or equals to UPDATED_CREATED_DATE
        defaultProfileShouldBeFound("createdDate.lessThan=" + UPDATED_CREATED_DATE);
    }


    @Test
    @Transactional
    public void getAllProfilesByLastUpdatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where lastUpdatedDate equals to DEFAULT_LAST_UPDATED_DATE
        defaultProfileShouldBeFound("lastUpdatedDate.equals=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the profileList where lastUpdatedDate equals to UPDATED_LAST_UPDATED_DATE
        defaultProfileShouldNotBeFound("lastUpdatedDate.equals=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllProfilesByLastUpdatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where lastUpdatedDate in DEFAULT_LAST_UPDATED_DATE or UPDATED_LAST_UPDATED_DATE
        defaultProfileShouldBeFound("lastUpdatedDate.in=" + DEFAULT_LAST_UPDATED_DATE + "," + UPDATED_LAST_UPDATED_DATE);

        // Get all the profileList where lastUpdatedDate equals to UPDATED_LAST_UPDATED_DATE
        defaultProfileShouldNotBeFound("lastUpdatedDate.in=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllProfilesByLastUpdatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where lastUpdatedDate is not null
        defaultProfileShouldBeFound("lastUpdatedDate.specified=true");

        // Get all the profileList where lastUpdatedDate is null
        defaultProfileShouldNotBeFound("lastUpdatedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllProfilesByLastUpdatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where lastUpdatedDate greater than or equals to DEFAULT_LAST_UPDATED_DATE
        defaultProfileShouldBeFound("lastUpdatedDate.greaterOrEqualThan=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the profileList where lastUpdatedDate greater than or equals to UPDATED_LAST_UPDATED_DATE
        defaultProfileShouldNotBeFound("lastUpdatedDate.greaterOrEqualThan=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllProfilesByLastUpdatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where lastUpdatedDate less than or equals to DEFAULT_LAST_UPDATED_DATE
        defaultProfileShouldNotBeFound("lastUpdatedDate.lessThan=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the profileList where lastUpdatedDate less than or equals to UPDATED_LAST_UPDATED_DATE
        defaultProfileShouldBeFound("lastUpdatedDate.lessThan=" + UPDATED_LAST_UPDATED_DATE);
    }


    @Test
    @Transactional
    public void getAllProfilesByPublishedIsEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where published equals to DEFAULT_PUBLISHED
        defaultProfileShouldBeFound("published.equals=" + DEFAULT_PUBLISHED);

        // Get all the profileList where published equals to UPDATED_PUBLISHED
        defaultProfileShouldNotBeFound("published.equals=" + UPDATED_PUBLISHED);
    }

    @Test
    @Transactional
    public void getAllProfilesByPublishedIsInShouldWork() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where published in DEFAULT_PUBLISHED or UPDATED_PUBLISHED
        defaultProfileShouldBeFound("published.in=" + DEFAULT_PUBLISHED + "," + UPDATED_PUBLISHED);

        // Get all the profileList where published equals to UPDATED_PUBLISHED
        defaultProfileShouldNotBeFound("published.in=" + UPDATED_PUBLISHED);
    }

    @Test
    @Transactional
    public void getAllProfilesByPublishedIsNullOrNotNull() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where published is not null
        defaultProfileShouldBeFound("published.specified=true");

        // Get all the profileList where published is null
        defaultProfileShouldNotBeFound("published.specified=false");
    }

    @Test
    @Transactional
    public void getAllProfilesByUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where url equals to DEFAULT_URL
        defaultProfileShouldBeFound("url.equals=" + DEFAULT_URL);

        // Get all the profileList where url equals to UPDATED_URL
        defaultProfileShouldNotBeFound("url.equals=" + UPDATED_URL);
    }

    @Test
    @Transactional
    public void getAllProfilesByUrlIsInShouldWork() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where url in DEFAULT_URL or UPDATED_URL
        defaultProfileShouldBeFound("url.in=" + DEFAULT_URL + "," + UPDATED_URL);

        // Get all the profileList where url equals to UPDATED_URL
        defaultProfileShouldNotBeFound("url.in=" + UPDATED_URL);
    }

    @Test
    @Transactional
    public void getAllProfilesByUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where url is not null
        defaultProfileShouldBeFound("url.specified=true");

        // Get all the profileList where url is null
        defaultProfileShouldNotBeFound("url.specified=false");
    }

    @Test
    @Transactional
    public void getAllProfilesByBranchIsEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where branch equals to DEFAULT_BRANCH
        defaultProfileShouldBeFound("branch.equals=" + DEFAULT_BRANCH);

        // Get all the profileList where branch equals to UPDATED_BRANCH
        defaultProfileShouldNotBeFound("branch.equals=" + UPDATED_BRANCH);
    }

    @Test
    @Transactional
    public void getAllProfilesByBranchIsInShouldWork() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where branch in DEFAULT_BRANCH or UPDATED_BRANCH
        defaultProfileShouldBeFound("branch.in=" + DEFAULT_BRANCH + "," + UPDATED_BRANCH);

        // Get all the profileList where branch equals to UPDATED_BRANCH
        defaultProfileShouldNotBeFound("branch.in=" + UPDATED_BRANCH);
    }

    @Test
    @Transactional
    public void getAllProfilesByBranchIsNullOrNotNull() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where branch is not null
        defaultProfileShouldBeFound("branch.specified=true");

        // Get all the profileList where branch is null
        defaultProfileShouldNotBeFound("branch.specified=false");
    }

    @Test
    @Transactional
    public void getAllProfilesByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where title equals to DEFAULT_TITLE
        defaultProfileShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the profileList where title equals to UPDATED_TITLE
        defaultProfileShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllProfilesByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultProfileShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the profileList where title equals to UPDATED_TITLE
        defaultProfileShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllProfilesByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where title is not null
        defaultProfileShouldBeFound("title.specified=true");

        // Get all the profileList where title is null
        defaultProfileShouldNotBeFound("title.specified=false");
    }

    @Test
    @Transactional
    public void getAllProfilesByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where email equals to DEFAULT_EMAIL
        defaultProfileShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the profileList where email equals to UPDATED_EMAIL
        defaultProfileShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllProfilesByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultProfileShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the profileList where email equals to UPDATED_EMAIL
        defaultProfileShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllProfilesByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where email is not null
        defaultProfileShouldBeFound("email.specified=true");

        // Get all the profileList where email is null
        defaultProfileShouldNotBeFound("email.specified=false");
    }

    @Test
    @Transactional
    public void getAllProfilesByFirstNameIsEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where firstName equals to DEFAULT_FIRST_NAME
        defaultProfileShouldBeFound("firstName.equals=" + DEFAULT_FIRST_NAME);

        // Get all the profileList where firstName equals to UPDATED_FIRST_NAME
        defaultProfileShouldNotBeFound("firstName.equals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllProfilesByFirstNameIsInShouldWork() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where firstName in DEFAULT_FIRST_NAME or UPDATED_FIRST_NAME
        defaultProfileShouldBeFound("firstName.in=" + DEFAULT_FIRST_NAME + "," + UPDATED_FIRST_NAME);

        // Get all the profileList where firstName equals to UPDATED_FIRST_NAME
        defaultProfileShouldNotBeFound("firstName.in=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllProfilesByFirstNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where firstName is not null
        defaultProfileShouldBeFound("firstName.specified=true");

        // Get all the profileList where firstName is null
        defaultProfileShouldNotBeFound("firstName.specified=false");
    }

    @Test
    @Transactional
    public void getAllProfilesByLastNameIsEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where lastName equals to DEFAULT_LAST_NAME
        defaultProfileShouldBeFound("lastName.equals=" + DEFAULT_LAST_NAME);

        // Get all the profileList where lastName equals to UPDATED_LAST_NAME
        defaultProfileShouldNotBeFound("lastName.equals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllProfilesByLastNameIsInShouldWork() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where lastName in DEFAULT_LAST_NAME or UPDATED_LAST_NAME
        defaultProfileShouldBeFound("lastName.in=" + DEFAULT_LAST_NAME + "," + UPDATED_LAST_NAME);

        // Get all the profileList where lastName equals to UPDATED_LAST_NAME
        defaultProfileShouldNotBeFound("lastName.in=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllProfilesByLastNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where lastName is not null
        defaultProfileShouldBeFound("lastName.specified=true");

        // Get all the profileList where lastName is null
        defaultProfileShouldNotBeFound("lastName.specified=false");
    }

    @Test
    @Transactional
    public void getAllProfilesByPhoneNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where phoneNumber equals to DEFAULT_PHONE_NUMBER
        defaultProfileShouldBeFound("phoneNumber.equals=" + DEFAULT_PHONE_NUMBER);

        // Get all the profileList where phoneNumber equals to UPDATED_PHONE_NUMBER
        defaultProfileShouldNotBeFound("phoneNumber.equals=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllProfilesByPhoneNumberIsInShouldWork() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where phoneNumber in DEFAULT_PHONE_NUMBER or UPDATED_PHONE_NUMBER
        defaultProfileShouldBeFound("phoneNumber.in=" + DEFAULT_PHONE_NUMBER + "," + UPDATED_PHONE_NUMBER);

        // Get all the profileList where phoneNumber equals to UPDATED_PHONE_NUMBER
        defaultProfileShouldNotBeFound("phoneNumber.in=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllProfilesByPhoneNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where phoneNumber is not null
        defaultProfileShouldBeFound("phoneNumber.specified=true");

        // Get all the profileList where phoneNumber is null
        defaultProfileShouldNotBeFound("phoneNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllProfilesByScoreIsEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where score equals to DEFAULT_SCORE
        defaultProfileShouldBeFound("score.equals=" + DEFAULT_SCORE);

        // Get all the profileList where score equals to UPDATED_SCORE
        defaultProfileShouldNotBeFound("score.equals=" + UPDATED_SCORE);
    }

    @Test
    @Transactional
    public void getAllProfilesByScoreIsInShouldWork() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where score in DEFAULT_SCORE or UPDATED_SCORE
        defaultProfileShouldBeFound("score.in=" + DEFAULT_SCORE + "," + UPDATED_SCORE);

        // Get all the profileList where score equals to UPDATED_SCORE
        defaultProfileShouldNotBeFound("score.in=" + UPDATED_SCORE);
    }

    @Test
    @Transactional
    public void getAllProfilesByScoreIsNullOrNotNull() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where score is not null
        defaultProfileShouldBeFound("score.specified=true");

        // Get all the profileList where score is null
        defaultProfileShouldNotBeFound("score.specified=false");
    }

    @Test
    @Transactional
    public void getAllProfilesByScoreIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where score greater than or equals to DEFAULT_SCORE
        defaultProfileShouldBeFound("score.greaterOrEqualThan=" + DEFAULT_SCORE);

        // Get all the profileList where score greater than or equals to UPDATED_SCORE
        defaultProfileShouldNotBeFound("score.greaterOrEqualThan=" + UPDATED_SCORE);
    }

    @Test
    @Transactional
    public void getAllProfilesByScoreIsLessThanSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where score less than or equals to DEFAULT_SCORE
        defaultProfileShouldNotBeFound("score.lessThan=" + DEFAULT_SCORE);

        // Get all the profileList where score less than or equals to UPDATED_SCORE
        defaultProfileShouldBeFound("score.lessThan=" + UPDATED_SCORE);
    }


    @Test
    @Transactional
    public void getAllProfilesByProPackageIsEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where proPackage equals to DEFAULT_PRO_PACKAGE
        defaultProfileShouldBeFound("proPackage.equals=" + DEFAULT_PRO_PACKAGE);

        // Get all the profileList where proPackage equals to UPDATED_PRO_PACKAGE
        defaultProfileShouldNotBeFound("proPackage.equals=" + UPDATED_PRO_PACKAGE);
    }

    @Test
    @Transactional
    public void getAllProfilesByProPackageIsInShouldWork() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where proPackage in DEFAULT_PRO_PACKAGE or UPDATED_PRO_PACKAGE
        defaultProfileShouldBeFound("proPackage.in=" + DEFAULT_PRO_PACKAGE + "," + UPDATED_PRO_PACKAGE);

        // Get all the profileList where proPackage equals to UPDATED_PRO_PACKAGE
        defaultProfileShouldNotBeFound("proPackage.in=" + UPDATED_PRO_PACKAGE);
    }

    @Test
    @Transactional
    public void getAllProfilesByProPackageIsNullOrNotNull() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where proPackage is not null
        defaultProfileShouldBeFound("proPackage.specified=true");

        // Get all the profileList where proPackage is null
        defaultProfileShouldNotBeFound("proPackage.specified=false");
    }

    @Test
    @Transactional
    public void getAllProfilesByLngIsEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where lng equals to DEFAULT_LNG
        defaultProfileShouldBeFound("lng.equals=" + DEFAULT_LNG);

        // Get all the profileList where lng equals to UPDATED_LNG
        defaultProfileShouldNotBeFound("lng.equals=" + UPDATED_LNG);
    }

    @Test
    @Transactional
    public void getAllProfilesByLngIsInShouldWork() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where lng in DEFAULT_LNG or UPDATED_LNG
        defaultProfileShouldBeFound("lng.in=" + DEFAULT_LNG + "," + UPDATED_LNG);

        // Get all the profileList where lng equals to UPDATED_LNG
        defaultProfileShouldNotBeFound("lng.in=" + UPDATED_LNG);
    }

    @Test
    @Transactional
    public void getAllProfilesByLngIsNullOrNotNull() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where lng is not null
        defaultProfileShouldBeFound("lng.specified=true");

        // Get all the profileList where lng is null
        defaultProfileShouldNotBeFound("lng.specified=false");
    }

    @Test
    @Transactional
    public void getAllProfilesByLngIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where lng greater than or equals to DEFAULT_LNG
        defaultProfileShouldBeFound("lng.greaterOrEqualThan=" + DEFAULT_LNG);

        // Get all the profileList where lng greater than or equals to UPDATED_LNG
        defaultProfileShouldNotBeFound("lng.greaterOrEqualThan=" + UPDATED_LNG);
    }

    @Test
    @Transactional
    public void getAllProfilesByLngIsLessThanSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where lng less than or equals to DEFAULT_LNG
        defaultProfileShouldNotBeFound("lng.lessThan=" + DEFAULT_LNG);

        // Get all the profileList where lng less than or equals to UPDATED_LNG
        defaultProfileShouldBeFound("lng.lessThan=" + UPDATED_LNG);
    }


    @Test
    @Transactional
    public void getAllProfilesByLatIsEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where lat equals to DEFAULT_LAT
        defaultProfileShouldBeFound("lat.equals=" + DEFAULT_LAT);

        // Get all the profileList where lat equals to UPDATED_LAT
        defaultProfileShouldNotBeFound("lat.equals=" + UPDATED_LAT);
    }

    @Test
    @Transactional
    public void getAllProfilesByLatIsInShouldWork() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where lat in DEFAULT_LAT or UPDATED_LAT
        defaultProfileShouldBeFound("lat.in=" + DEFAULT_LAT + "," + UPDATED_LAT);

        // Get all the profileList where lat equals to UPDATED_LAT
        defaultProfileShouldNotBeFound("lat.in=" + UPDATED_LAT);
    }

    @Test
    @Transactional
    public void getAllProfilesByLatIsNullOrNotNull() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where lat is not null
        defaultProfileShouldBeFound("lat.specified=true");

        // Get all the profileList where lat is null
        defaultProfileShouldNotBeFound("lat.specified=false");
    }

    @Test
    @Transactional
    public void getAllProfilesByLatIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where lat greater than or equals to DEFAULT_LAT
        defaultProfileShouldBeFound("lat.greaterOrEqualThan=" + DEFAULT_LAT);

        // Get all the profileList where lat greater than or equals to UPDATED_LAT
        defaultProfileShouldNotBeFound("lat.greaterOrEqualThan=" + UPDATED_LAT);
    }

    @Test
    @Transactional
    public void getAllProfilesByLatIsLessThanSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where lat less than or equals to DEFAULT_LAT
        defaultProfileShouldNotBeFound("lat.lessThan=" + DEFAULT_LAT);

        // Get all the profileList where lat less than or equals to UPDATED_LAT
        defaultProfileShouldBeFound("lat.lessThan=" + UPDATED_LAT);
    }


    @Test
    @Transactional
    public void getAllProfilesByCountryIsEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where country equals to DEFAULT_COUNTRY
        defaultProfileShouldBeFound("country.equals=" + DEFAULT_COUNTRY);

        // Get all the profileList where country equals to UPDATED_COUNTRY
        defaultProfileShouldNotBeFound("country.equals=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    public void getAllProfilesByCountryIsInShouldWork() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where country in DEFAULT_COUNTRY or UPDATED_COUNTRY
        defaultProfileShouldBeFound("country.in=" + DEFAULT_COUNTRY + "," + UPDATED_COUNTRY);

        // Get all the profileList where country equals to UPDATED_COUNTRY
        defaultProfileShouldNotBeFound("country.in=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    public void getAllProfilesByCountryIsNullOrNotNull() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where country is not null
        defaultProfileShouldBeFound("country.specified=true");

        // Get all the profileList where country is null
        defaultProfileShouldNotBeFound("country.specified=false");
    }

    @Test
    @Transactional
    public void getAllProfilesByCityIsEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where city equals to DEFAULT_CITY
        defaultProfileShouldBeFound("city.equals=" + DEFAULT_CITY);

        // Get all the profileList where city equals to UPDATED_CITY
        defaultProfileShouldNotBeFound("city.equals=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    public void getAllProfilesByCityIsInShouldWork() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where city in DEFAULT_CITY or UPDATED_CITY
        defaultProfileShouldBeFound("city.in=" + DEFAULT_CITY + "," + UPDATED_CITY);

        // Get all the profileList where city equals to UPDATED_CITY
        defaultProfileShouldNotBeFound("city.in=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    public void getAllProfilesByCityIsNullOrNotNull() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where city is not null
        defaultProfileShouldBeFound("city.specified=true");

        // Get all the profileList where city is null
        defaultProfileShouldNotBeFound("city.specified=false");
    }

    @Test
    @Transactional
    public void getAllProfilesByStateIsEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where state equals to DEFAULT_STATE
        defaultProfileShouldBeFound("state.equals=" + DEFAULT_STATE);

        // Get all the profileList where state equals to UPDATED_STATE
        defaultProfileShouldNotBeFound("state.equals=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    public void getAllProfilesByStateIsInShouldWork() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where state in DEFAULT_STATE or UPDATED_STATE
        defaultProfileShouldBeFound("state.in=" + DEFAULT_STATE + "," + UPDATED_STATE);

        // Get all the profileList where state equals to UPDATED_STATE
        defaultProfileShouldNotBeFound("state.in=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    public void getAllProfilesByStateIsNullOrNotNull() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where state is not null
        defaultProfileShouldBeFound("state.specified=true");

        // Get all the profileList where state is null
        defaultProfileShouldNotBeFound("state.specified=false");
    }

    @Test
    @Transactional
    public void getAllProfilesByStreetIsEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where street equals to DEFAULT_STREET
        defaultProfileShouldBeFound("street.equals=" + DEFAULT_STREET);

        // Get all the profileList where street equals to UPDATED_STREET
        defaultProfileShouldNotBeFound("street.equals=" + UPDATED_STREET);
    }

    @Test
    @Transactional
    public void getAllProfilesByStreetIsInShouldWork() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where street in DEFAULT_STREET or UPDATED_STREET
        defaultProfileShouldBeFound("street.in=" + DEFAULT_STREET + "," + UPDATED_STREET);

        // Get all the profileList where street equals to UPDATED_STREET
        defaultProfileShouldNotBeFound("street.in=" + UPDATED_STREET);
    }

    @Test
    @Transactional
    public void getAllProfilesByStreetIsNullOrNotNull() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where street is not null
        defaultProfileShouldBeFound("street.specified=true");

        // Get all the profileList where street is null
        defaultProfileShouldNotBeFound("street.specified=false");
    }

    @Test
    @Transactional
    public void getAllProfilesByPostCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where postCode equals to DEFAULT_POST_CODE
        defaultProfileShouldBeFound("postCode.equals=" + DEFAULT_POST_CODE);

        // Get all the profileList where postCode equals to UPDATED_POST_CODE
        defaultProfileShouldNotBeFound("postCode.equals=" + UPDATED_POST_CODE);
    }

    @Test
    @Transactional
    public void getAllProfilesByPostCodeIsInShouldWork() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where postCode in DEFAULT_POST_CODE or UPDATED_POST_CODE
        defaultProfileShouldBeFound("postCode.in=" + DEFAULT_POST_CODE + "," + UPDATED_POST_CODE);

        // Get all the profileList where postCode equals to UPDATED_POST_CODE
        defaultProfileShouldNotBeFound("postCode.in=" + UPDATED_POST_CODE);
    }

    @Test
    @Transactional
    public void getAllProfilesByPostCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where postCode is not null
        defaultProfileShouldBeFound("postCode.specified=true");

        // Get all the profileList where postCode is null
        defaultProfileShouldNotBeFound("postCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllProfilesByGenderIsEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where gender equals to DEFAULT_GENDER
        defaultProfileShouldBeFound("gender.equals=" + DEFAULT_GENDER);

        // Get all the profileList where gender equals to UPDATED_GENDER
        defaultProfileShouldNotBeFound("gender.equals=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    public void getAllProfilesByGenderIsInShouldWork() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where gender in DEFAULT_GENDER or UPDATED_GENDER
        defaultProfileShouldBeFound("gender.in=" + DEFAULT_GENDER + "," + UPDATED_GENDER);

        // Get all the profileList where gender equals to UPDATED_GENDER
        defaultProfileShouldNotBeFound("gender.in=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    public void getAllProfilesByGenderIsNullOrNotNull() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where gender is not null
        defaultProfileShouldBeFound("gender.specified=true");

        // Get all the profileList where gender is null
        defaultProfileShouldNotBeFound("gender.specified=false");
    }

    @Test
    @Transactional
    public void getAllProfilesByAgbCheckIsEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where agbCheck equals to DEFAULT_AGB_CHECK
        defaultProfileShouldBeFound("agbCheck.equals=" + DEFAULT_AGB_CHECK);

        // Get all the profileList where agbCheck equals to UPDATED_AGB_CHECK
        defaultProfileShouldNotBeFound("agbCheck.equals=" + UPDATED_AGB_CHECK);
    }

    @Test
    @Transactional
    public void getAllProfilesByAgbCheckIsInShouldWork() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where agbCheck in DEFAULT_AGB_CHECK or UPDATED_AGB_CHECK
        defaultProfileShouldBeFound("agbCheck.in=" + DEFAULT_AGB_CHECK + "," + UPDATED_AGB_CHECK);

        // Get all the profileList where agbCheck equals to UPDATED_AGB_CHECK
        defaultProfileShouldNotBeFound("agbCheck.in=" + UPDATED_AGB_CHECK);
    }

    @Test
    @Transactional
    public void getAllProfilesByAgbCheckIsNullOrNotNull() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where agbCheck is not null
        defaultProfileShouldBeFound("agbCheck.specified=true");

        // Get all the profileList where agbCheck is null
        defaultProfileShouldNotBeFound("agbCheck.specified=false");
    }

    @Test
    @Transactional
    public void getAllProfilesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where status equals to DEFAULT_STATUS
        defaultProfileShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the profileList where status equals to UPDATED_STATUS
        defaultProfileShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllProfilesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultProfileShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the profileList where status equals to UPDATED_STATUS
        defaultProfileShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllProfilesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where status is not null
        defaultProfileShouldBeFound("status.specified=true");

        // Get all the profileList where status is null
        defaultProfileShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllProfilesByLocTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where locType equals to DEFAULT_LOC_TYPE
        defaultProfileShouldBeFound("locType.equals=" + DEFAULT_LOC_TYPE);

        // Get all the profileList where locType equals to UPDATED_LOC_TYPE
        defaultProfileShouldNotBeFound("locType.equals=" + UPDATED_LOC_TYPE);
    }

    @Test
    @Transactional
    public void getAllProfilesByLocTypeIsInShouldWork() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where locType in DEFAULT_LOC_TYPE or UPDATED_LOC_TYPE
        defaultProfileShouldBeFound("locType.in=" + DEFAULT_LOC_TYPE + "," + UPDATED_LOC_TYPE);

        // Get all the profileList where locType equals to UPDATED_LOC_TYPE
        defaultProfileShouldNotBeFound("locType.in=" + UPDATED_LOC_TYPE);
    }

    @Test
    @Transactional
    public void getAllProfilesByLocTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where locType is not null
        defaultProfileShouldBeFound("locType.specified=true");

        // Get all the profileList where locType is null
        defaultProfileShouldNotBeFound("locType.specified=false");
    }

    @Test
    @Transactional
    public void getAllProfilesByLocGeoIsEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where locGeo equals to DEFAULT_LOC_GEO
        defaultProfileShouldBeFound("locGeo.equals=" + DEFAULT_LOC_GEO);

        // Get all the profileList where locGeo equals to UPDATED_LOC_GEO
        defaultProfileShouldNotBeFound("locGeo.equals=" + UPDATED_LOC_GEO);
    }

    @Test
    @Transactional
    public void getAllProfilesByLocGeoIsInShouldWork() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where locGeo in DEFAULT_LOC_GEO or UPDATED_LOC_GEO
        defaultProfileShouldBeFound("locGeo.in=" + DEFAULT_LOC_GEO + "," + UPDATED_LOC_GEO);

        // Get all the profileList where locGeo equals to UPDATED_LOC_GEO
        defaultProfileShouldNotBeFound("locGeo.in=" + UPDATED_LOC_GEO);
    }

    @Test
    @Transactional
    public void getAllProfilesByLocGeoIsNullOrNotNull() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where locGeo is not null
        defaultProfileShouldBeFound("locGeo.specified=true");

        // Get all the profileList where locGeo is null
        defaultProfileShouldNotBeFound("locGeo.specified=false");
    }

    @Test
    @Transactional
    public void getAllProfilesByLocCapacityIsEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where locCapacity equals to DEFAULT_LOC_CAPACITY
        defaultProfileShouldBeFound("locCapacity.equals=" + DEFAULT_LOC_CAPACITY);

        // Get all the profileList where locCapacity equals to UPDATED_LOC_CAPACITY
        defaultProfileShouldNotBeFound("locCapacity.equals=" + UPDATED_LOC_CAPACITY);
    }

    @Test
    @Transactional
    public void getAllProfilesByLocCapacityIsInShouldWork() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where locCapacity in DEFAULT_LOC_CAPACITY or UPDATED_LOC_CAPACITY
        defaultProfileShouldBeFound("locCapacity.in=" + DEFAULT_LOC_CAPACITY + "," + UPDATED_LOC_CAPACITY);

        // Get all the profileList where locCapacity equals to UPDATED_LOC_CAPACITY
        defaultProfileShouldNotBeFound("locCapacity.in=" + UPDATED_LOC_CAPACITY);
    }

    @Test
    @Transactional
    public void getAllProfilesByLocCapacityIsNullOrNotNull() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where locCapacity is not null
        defaultProfileShouldBeFound("locCapacity.specified=true");

        // Get all the profileList where locCapacity is null
        defaultProfileShouldNotBeFound("locCapacity.specified=false");
    }

    @Test
    @Transactional
    public void getAllProfilesBySpAvailableRegionIsEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where spAvailableRegion equals to DEFAULT_SP_AVAILABLE_REGION
        defaultProfileShouldBeFound("spAvailableRegion.equals=" + DEFAULT_SP_AVAILABLE_REGION);

        // Get all the profileList where spAvailableRegion equals to UPDATED_SP_AVAILABLE_REGION
        defaultProfileShouldNotBeFound("spAvailableRegion.equals=" + UPDATED_SP_AVAILABLE_REGION);
    }

    @Test
    @Transactional
    public void getAllProfilesBySpAvailableRegionIsInShouldWork() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where spAvailableRegion in DEFAULT_SP_AVAILABLE_REGION or UPDATED_SP_AVAILABLE_REGION
        defaultProfileShouldBeFound("spAvailableRegion.in=" + DEFAULT_SP_AVAILABLE_REGION + "," + UPDATED_SP_AVAILABLE_REGION);

        // Get all the profileList where spAvailableRegion equals to UPDATED_SP_AVAILABLE_REGION
        defaultProfileShouldNotBeFound("spAvailableRegion.in=" + UPDATED_SP_AVAILABLE_REGION);
    }

    @Test
    @Transactional
    public void getAllProfilesBySpAvailableRegionIsNullOrNotNull() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where spAvailableRegion is not null
        defaultProfileShouldBeFound("spAvailableRegion.specified=true");

        // Get all the profileList where spAvailableRegion is null
        defaultProfileShouldNotBeFound("spAvailableRegion.specified=false");
    }

    @Test
    @Transactional
    public void getAllProfilesByFeatureStrIsEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where featureStr equals to DEFAULT_FEATURE_STR
        defaultProfileShouldBeFound("featureStr.equals=" + DEFAULT_FEATURE_STR);

        // Get all the profileList where featureStr equals to UPDATED_FEATURE_STR
        defaultProfileShouldNotBeFound("featureStr.equals=" + UPDATED_FEATURE_STR);
    }

    @Test
    @Transactional
    public void getAllProfilesByFeatureStrIsInShouldWork() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where featureStr in DEFAULT_FEATURE_STR or UPDATED_FEATURE_STR
        defaultProfileShouldBeFound("featureStr.in=" + DEFAULT_FEATURE_STR + "," + UPDATED_FEATURE_STR);

        // Get all the profileList where featureStr equals to UPDATED_FEATURE_STR
        defaultProfileShouldNotBeFound("featureStr.in=" + UPDATED_FEATURE_STR);
    }

    @Test
    @Transactional
    public void getAllProfilesByFeatureStrIsNullOrNotNull() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where featureStr is not null
        defaultProfileShouldBeFound("featureStr.specified=true");

        // Get all the profileList where featureStr is null
        defaultProfileShouldNotBeFound("featureStr.specified=false");
    }

    @Test
    @Transactional
    public void getAllProfilesByFeaturesIsEqualToSomething() throws Exception {
        // Initialize the database
        Feature features = FeatureResourceIntTest.createEntity(em);
        em.persist(features);
        em.flush();
        profile.addFeatures(features);
        profileRepository.saveAndFlush(profile);
        Long featuresId = features.getId();

        // Get all the profileList where features equals to featuresId
        defaultProfileShouldBeFound("featuresId.equals=" + featuresId);

        // Get all the profileList where features equals to featuresId + 1
        defaultProfileShouldNotBeFound("featuresId.equals=" + (featuresId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultProfileShouldBeFound(String filter) throws Exception {
        restProfileMockMvc.perform(get("/api/profiles?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(profile.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE))))
            .andExpect(jsonPath("$.[*].published").value(hasItem(DEFAULT_PUBLISHED.booleanValue())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
            .andExpect(jsonPath("$.[*].branch").value(hasItem(DEFAULT_BRANCH.toString())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE.intValue())))
            .andExpect(jsonPath("$.[*].proPackage").value(hasItem(DEFAULT_PRO_PACKAGE.toString())))
            .andExpect(jsonPath("$.[*].lng").value(hasItem(DEFAULT_LNG.intValue())))
            .andExpect(jsonPath("$.[*].lat").value(hasItem(DEFAULT_LAT.intValue())))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
            .andExpect(jsonPath("$.[*].street").value(hasItem(DEFAULT_STREET.toString())))
            .andExpect(jsonPath("$.[*].postCode").value(hasItem(DEFAULT_POST_CODE.toString())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].agbCheck").value(hasItem(DEFAULT_AGB_CHECK.booleanValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].locType").value(hasItem(DEFAULT_LOC_TYPE.toString())))
            .andExpect(jsonPath("$.[*].locGeo").value(hasItem(DEFAULT_LOC_GEO.toString())))
            .andExpect(jsonPath("$.[*].locCapacity").value(hasItem(DEFAULT_LOC_CAPACITY.toString())))
            .andExpect(jsonPath("$.[*].spAvailableRegion").value(hasItem(DEFAULT_SP_AVAILABLE_REGION.toString())))
            .andExpect(jsonPath("$.[*].featureStr").value(hasItem(DEFAULT_FEATURE_STR.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultProfileShouldNotBeFound(String filter) throws Exception {
        restProfileMockMvc.perform(get("/api/profiles?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingProfile() throws Exception {
        // Get the profile
        restProfileMockMvc.perform(get("/api/profiles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProfile() throws Exception {
        // Initialize the database
        profileService.save(profile);

        int databaseSizeBeforeUpdate = profileRepository.findAll().size();

        // Update the profile
        Profile updatedProfile = profileRepository.findOne(profile.getId());
        // Disconnect from session so that the updates on updatedProfile are not directly saved in db
        em.detach(updatedProfile);
        updatedProfile
            .createdDate(UPDATED_CREATED_DATE)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .published(UPDATED_PUBLISHED)
            .url(UPDATED_URL)
            .branch(UPDATED_BRANCH)
            .title(UPDATED_TITLE)
            .email(UPDATED_EMAIL)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .score(UPDATED_SCORE)
            .proPackage(UPDATED_PRO_PACKAGE)
            .lng(UPDATED_LNG)
            .lat(UPDATED_LAT)
            .country(UPDATED_COUNTRY)
            .city(UPDATED_CITY)
            .state(UPDATED_STATE)
            .street(UPDATED_STREET)
            .postCode(UPDATED_POST_CODE)
            .gender(UPDATED_GENDER)
            .agbCheck(UPDATED_AGB_CHECK)
            .status(UPDATED_STATUS)
            .locType(UPDATED_LOC_TYPE)
            .locGeo(UPDATED_LOC_GEO)
            .locCapacity(UPDATED_LOC_CAPACITY)
            .spAvailableRegion(UPDATED_SP_AVAILABLE_REGION)
            .featureStr(UPDATED_FEATURE_STR);

        restProfileMockMvc.perform(put("/api/profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProfile)))
            .andExpect(status().isOk());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeUpdate);
        Profile testProfile = profileList.get(profileList.size() - 1);
        assertThat(testProfile.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProfile.getLastUpdatedDate()).isEqualTo(UPDATED_LAST_UPDATED_DATE);
        assertThat(testProfile.isPublished()).isEqualTo(UPDATED_PUBLISHED);
        assertThat(testProfile.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testProfile.getBranch()).isEqualTo(UPDATED_BRANCH);
        assertThat(testProfile.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testProfile.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testProfile.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testProfile.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testProfile.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testProfile.getScore()).isEqualTo(UPDATED_SCORE);
        assertThat(testProfile.getProPackage()).isEqualTo(UPDATED_PRO_PACKAGE);
        assertThat(testProfile.getLng()).isEqualTo(UPDATED_LNG);
        assertThat(testProfile.getLat()).isEqualTo(UPDATED_LAT);
        assertThat(testProfile.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testProfile.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testProfile.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testProfile.getStreet()).isEqualTo(UPDATED_STREET);
        assertThat(testProfile.getPostCode()).isEqualTo(UPDATED_POST_CODE);
        assertThat(testProfile.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testProfile.isAgbCheck()).isEqualTo(UPDATED_AGB_CHECK);
        assertThat(testProfile.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testProfile.getLocType()).isEqualTo(UPDATED_LOC_TYPE);
        assertThat(testProfile.getLocGeo()).isEqualTo(UPDATED_LOC_GEO);
        assertThat(testProfile.getLocCapacity()).isEqualTo(UPDATED_LOC_CAPACITY);
        assertThat(testProfile.getSpAvailableRegion()).isEqualTo(UPDATED_SP_AVAILABLE_REGION);
        assertThat(testProfile.getFeatureStr()).isEqualTo(UPDATED_FEATURE_STR);

        // Validate the Profile in Elasticsearch
        Profile profileEs = profileSearchRepository.findOne(testProfile.getId());
        assertThat(testProfile.getCreatedDate()).isEqualTo(testProfile.getCreatedDate());
        assertThat(testProfile.getLastUpdatedDate()).isEqualTo(testProfile.getLastUpdatedDate());
        assertThat(profileEs).isEqualToIgnoringGivenFields(testProfile, "createdDate", "lastUpdatedDate");
    }

    @Test
    @Transactional
    public void updateNonExistingProfile() throws Exception {
        int databaseSizeBeforeUpdate = profileRepository.findAll().size();

        // Create the Profile

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProfileMockMvc.perform(put("/api/profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profile)))
            .andExpect(status().isCreated());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProfile() throws Exception {
        // Initialize the database
        profileService.save(profile);

        int databaseSizeBeforeDelete = profileRepository.findAll().size();

        // Get the profile
        restProfileMockMvc.perform(delete("/api/profiles/{id}", profile.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean profileExistsInEs = profileSearchRepository.exists(profile.getId());
        assertThat(profileExistsInEs).isFalse();

        // Validate the database is empty
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchProfile() throws Exception {
        // Initialize the database
        profileService.save(profile);

        // Search the profile
        restProfileMockMvc.perform(get("/api/_search/profiles?query=id:" + profile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(profile.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE))))
            .andExpect(jsonPath("$.[*].published").value(hasItem(DEFAULT_PUBLISHED.booleanValue())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
            .andExpect(jsonPath("$.[*].branch").value(hasItem(DEFAULT_BRANCH.toString())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE.intValue())))
            .andExpect(jsonPath("$.[*].proPackage").value(hasItem(DEFAULT_PRO_PACKAGE.toString())))
            .andExpect(jsonPath("$.[*].lng").value(hasItem(DEFAULT_LNG.intValue())))
            .andExpect(jsonPath("$.[*].lat").value(hasItem(DEFAULT_LAT.intValue())))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
            .andExpect(jsonPath("$.[*].street").value(hasItem(DEFAULT_STREET.toString())))
            .andExpect(jsonPath("$.[*].postCode").value(hasItem(DEFAULT_POST_CODE.toString())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].agbCheck").value(hasItem(DEFAULT_AGB_CHECK.booleanValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].locType").value(hasItem(DEFAULT_LOC_TYPE.toString())))
            .andExpect(jsonPath("$.[*].locGeo").value(hasItem(DEFAULT_LOC_GEO.toString())))
            .andExpect(jsonPath("$.[*].locCapacity").value(hasItem(DEFAULT_LOC_CAPACITY.toString())))
            .andExpect(jsonPath("$.[*].spAvailableRegion").value(hasItem(DEFAULT_SP_AVAILABLE_REGION.toString())))
            .andExpect(jsonPath("$.[*].featureStr").value(hasItem(DEFAULT_FEATURE_STR.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Profile.class);
        Profile profile1 = new Profile();
        profile1.setId(1L);
        Profile profile2 = new Profile();
        profile2.setId(profile1.getId());
        assertThat(profile1).isEqualTo(profile2);
        profile2.setId(2L);
        assertThat(profile1).isNotEqualTo(profile2);
        profile1.setId(null);
        assertThat(profile1).isNotEqualTo(profile2);
    }
}
