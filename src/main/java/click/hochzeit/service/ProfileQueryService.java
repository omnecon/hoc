package click.hochzeit.service;


import java.time.ZonedDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import click.hochzeit.domain.Profile;
import click.hochzeit.domain.*; // for static metamodels
import click.hochzeit.repository.ProfileRepository;
import click.hochzeit.repository.search.ProfileSearchRepository;
import click.hochzeit.service.dto.ProfileCriteria;

import click.hochzeit.domain.enumeration.ProPackage;
import click.hochzeit.domain.enumeration.Gender;

/**
 * Service for executing complex queries for Profile entities in the database.
 * The main input is a {@link ProfileCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Profile} or a {@link Page} of {@link Profile} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProfileQueryService extends QueryService<Profile> {

    private final Logger log = LoggerFactory.getLogger(ProfileQueryService.class);


    private final ProfileRepository profileRepository;

    private final ProfileSearchRepository profileSearchRepository;

    public ProfileQueryService(ProfileRepository profileRepository, ProfileSearchRepository profileSearchRepository) {
        this.profileRepository = profileRepository;
        this.profileSearchRepository = profileSearchRepository;
    }

    /**
     * Return a {@link List} of {@link Profile} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Profile> findByCriteria(ProfileCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Profile> specification = createSpecification(criteria);
        return profileRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Profile} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Profile> findByCriteria(ProfileCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Profile> specification = createSpecification(criteria);
        return profileRepository.findAll(specification, page);
    }

    /**
     * Function to convert ProfileCriteria to a {@link Specifications}
     */
    private Specifications<Profile> createSpecification(ProfileCriteria criteria) {
        Specifications<Profile> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Profile_.id));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), Profile_.createdDate));
            }
            if (criteria.getLastUpdatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastUpdatedDate(), Profile_.lastUpdatedDate));
            }
            if (criteria.getPublished() != null) {
                specification = specification.and(buildSpecification(criteria.getPublished(), Profile_.published));
            }
            if (criteria.getUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUrl(), Profile_.url));
            }
            if (criteria.getBranch() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBranch(), Profile_.branch));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), Profile_.title));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), Profile_.email));
            }
            if (criteria.getFirstName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFirstName(), Profile_.firstName));
            }
            if (criteria.getLastName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastName(), Profile_.lastName));
            }
            if (criteria.getPhoneNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhoneNumber(), Profile_.phoneNumber));
            }
            if (criteria.getScore() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getScore(), Profile_.score));
            }
            if (criteria.getProPackage() != null) {
                specification = specification.and(buildSpecification(criteria.getProPackage(), Profile_.proPackage));
            }
            if (criteria.getLng() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLng(), Profile_.lng));
            }
            if (criteria.getLat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLat(), Profile_.lat));
            }
            if (criteria.getCountry() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCountry(), Profile_.country));
            }
            if (criteria.getCity() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCity(), Profile_.city));
            }
            if (criteria.getState() != null) {
                specification = specification.and(buildStringSpecification(criteria.getState(), Profile_.state));
            }
            if (criteria.getStreet() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStreet(), Profile_.street));
            }
            if (criteria.getPostCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPostCode(), Profile_.postCode));
            }
            if (criteria.getGender() != null) {
                specification = specification.and(buildSpecification(criteria.getGender(), Profile_.gender));
            }
            if (criteria.getAgbCheck() != null) {
                specification = specification.and(buildSpecification(criteria.getAgbCheck(), Profile_.agbCheck));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), Profile_.status));
            }
            if (criteria.getLocType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLocType(), Profile_.locType));
            }
            if (criteria.getLocGeo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLocGeo(), Profile_.locGeo));
            }
            if (criteria.getLocCapacity() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLocCapacity(), Profile_.locCapacity));
            }
            if (criteria.getSpAvailableRegion() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSpAvailableRegion(), Profile_.spAvailableRegion));
            }
            if (criteria.getFeatureStr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFeatureStr(), Profile_.featureStr));
            }
            if (criteria.getFeaturesId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getFeaturesId(), Profile_.features, Feature_.id));
            }
        }
        return specification;
    }

}
