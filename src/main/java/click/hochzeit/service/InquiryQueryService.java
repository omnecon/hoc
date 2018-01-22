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

import click.hochzeit.domain.Inquiry;
import click.hochzeit.domain.*; // for static metamodels
import click.hochzeit.repository.InquiryRepository;
import click.hochzeit.repository.search.InquirySearchRepository;
import click.hochzeit.service.dto.InquiryCriteria;


/**
 * Service for executing complex queries for Inquiry entities in the database.
 * The main input is a {@link InquiryCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Inquiry} or a {@link Page} of {@link Inquiry} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class InquiryQueryService extends QueryService<Inquiry> {

    private final Logger log = LoggerFactory.getLogger(InquiryQueryService.class);


    private final InquiryRepository inquiryRepository;

    private final InquirySearchRepository inquirySearchRepository;

    public InquiryQueryService(InquiryRepository inquiryRepository, InquirySearchRepository inquirySearchRepository) {
        this.inquiryRepository = inquiryRepository;
        this.inquirySearchRepository = inquirySearchRepository;
    }

    /**
     * Return a {@link List} of {@link Inquiry} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Inquiry> findByCriteria(InquiryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Inquiry> specification = createSpecification(criteria);
        return inquiryRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Inquiry} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Inquiry> findByCriteria(InquiryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Inquiry> specification = createSpecification(criteria);
        return inquiryRepository.findAll(specification, page);
    }

    /**
     * Function to convert InquiryCriteria to a {@link Specifications}
     */
    private Specifications<Inquiry> createSpecification(InquiryCriteria criteria) {
        Specifications<Inquiry> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Inquiry_.id));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), Inquiry_.createdDate));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), Inquiry_.email));
            }
            if (criteria.getFirstName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFirstName(), Inquiry_.firstName));
            }
            if (criteria.getLastName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastName(), Inquiry_.lastName));
            }
            if (criteria.getPhoneNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhoneNumber(), Inquiry_.phoneNumber));
            }
            if (criteria.getCountry() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCountry(), Inquiry_.country));
            }
            if (criteria.getRegion() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRegion(), Inquiry_.region));
            }
            if (criteria.getProvice() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProvice(), Inquiry_.provice));
            }
            if (criteria.getCity() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCity(), Inquiry_.city));
            }
            if (criteria.getPostalCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPostalCode(), Inquiry_.postalCode));
            }
        }
        return specification;
    }

}
