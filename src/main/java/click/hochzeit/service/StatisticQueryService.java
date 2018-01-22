package click.hochzeit.service;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import click.hochzeit.domain.Statistic;
import click.hochzeit.domain.*; // for static metamodels
import click.hochzeit.repository.StatisticRepository;
import click.hochzeit.repository.search.StatisticSearchRepository;
import click.hochzeit.service.dto.StatisticCriteria;


/**
 * Service for executing complex queries for Statistic entities in the database.
 * The main input is a {@link StatisticCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Statistic} or a {@link Page} of {@link Statistic} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class StatisticQueryService extends QueryService<Statistic> {

    private final Logger log = LoggerFactory.getLogger(StatisticQueryService.class);


    private final StatisticRepository statisticRepository;

    private final StatisticSearchRepository statisticSearchRepository;

    public StatisticQueryService(StatisticRepository statisticRepository, StatisticSearchRepository statisticSearchRepository) {
        this.statisticRepository = statisticRepository;
        this.statisticSearchRepository = statisticSearchRepository;
    }

    /**
     * Return a {@link List} of {@link Statistic} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Statistic> findByCriteria(StatisticCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Statistic> specification = createSpecification(criteria);
        return statisticRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Statistic} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Statistic> findByCriteria(StatisticCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Statistic> specification = createSpecification(criteria);
        return statisticRepository.findAll(specification, page);
    }

    /**
     * Function to convert StatisticCriteria to a {@link Specifications}
     */
    private Specifications<Statistic> createSpecification(StatisticCriteria criteria) {
        Specifications<Statistic> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Statistic_.id));
            }
            if (criteria.getNoOfFag() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNoOfFag(), Statistic_.noOfFag));
            }
            if (criteria.getNoOfGalleries() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNoOfGalleries(), Statistic_.noOfGalleries));
            }
            if (criteria.getNoOfInquiries() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNoOfInquiries(), Statistic_.noOfInquiries));
            }
            if (criteria.getNoOfLinkedProvider() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNoOfLinkedProvider(), Statistic_.noOfLinkedProvider));
            }
            if (criteria.getNoOfPortfolioImg() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNoOfPortfolioImg(), Statistic_.noOfPortfolioImg));
            }
            if (criteria.getViewsTotalWP() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getViewsTotalWP(), Statistic_.viewsTotalWP));
            }
            if (criteria.getProfileId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getProfileId(), Statistic_.profile, Profile_.id));
            }
        }
        return specification;
    }

}
