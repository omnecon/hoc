package click.hochzeit.service;

import click.hochzeit.domain.Feature;
import click.hochzeit.repository.FeatureRepository;
import click.hochzeit.repository.search.FeatureSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Feature.
 */
@Service
@Transactional
public class FeatureService {

    private final Logger log = LoggerFactory.getLogger(FeatureService.class);

    private final FeatureRepository featureRepository;

    private final FeatureSearchRepository featureSearchRepository;

    public FeatureService(FeatureRepository featureRepository, FeatureSearchRepository featureSearchRepository) {
        this.featureRepository = featureRepository;
        this.featureSearchRepository = featureSearchRepository;
    }

    /**
     * Save a feature.
     *
     * @param feature the entity to save
     * @return the persisted entity
     */
    public Feature save(Feature feature) {
        log.debug("Request to save Feature : {}", feature);
        Feature result = featureRepository.save(feature);
        featureSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the features.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Feature> findAll(Pageable pageable) {
        log.debug("Request to get all Features");
        return featureRepository.findAll(pageable);
    }

    /**
     * Get one feature by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Feature findOne(Long id) {
        log.debug("Request to get Feature : {}", id);
        return featureRepository.findOne(id);
    }

    /**
     * Delete the feature by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Feature : {}", id);
        featureRepository.delete(id);
        featureSearchRepository.delete(id);
    }

    /**
     * Search for the feature corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Feature> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Features for query {}", query);
        Page<Feature> result = featureSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
