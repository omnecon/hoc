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

import click.hochzeit.domain.DataImport;
import click.hochzeit.domain.*; // for static metamodels
import click.hochzeit.repository.DataImportRepository;
import click.hochzeit.repository.search.DataImportSearchRepository;
import click.hochzeit.service.dto.DataImportCriteria;


/**
 * Service for executing complex queries for DataImport entities in the database.
 * The main input is a {@link DataImportCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DataImport} or a {@link Page} of {@link DataImport} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DataImportQueryService extends QueryService<DataImport> {

    private final Logger log = LoggerFactory.getLogger(DataImportQueryService.class);


    private final DataImportRepository dataImportRepository;

    private final DataImportSearchRepository dataImportSearchRepository;

    public DataImportQueryService(DataImportRepository dataImportRepository, DataImportSearchRepository dataImportSearchRepository) {
        this.dataImportRepository = dataImportRepository;
        this.dataImportSearchRepository = dataImportSearchRepository;
    }

    /**
     * Return a {@link List} of {@link DataImport} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DataImport> findByCriteria(DataImportCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<DataImport> specification = createSpecification(criteria);
        return dataImportRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link DataImport} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DataImport> findByCriteria(DataImportCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<DataImport> specification = createSpecification(criteria);
        return dataImportRepository.findAll(specification, page);
    }

    /**
     * Function to convert DataImportCriteria to a {@link Specifications}
     */
    private Specifications<DataImport> createSpecification(DataImportCriteria criteria) {
        Specifications<DataImport> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), DataImport_.id));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), DataImport_.type));
            }
        }
        return specification;
    }

}
