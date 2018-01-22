package click.hochzeit.service;

import click.hochzeit.domain.Statistic;
import click.hochzeit.repository.StatisticRepository;
import click.hochzeit.repository.search.StatisticSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Statistic.
 */
@Service
@Transactional
public class StatisticService {

    private final Logger log = LoggerFactory.getLogger(StatisticService.class);

    private final StatisticRepository statisticRepository;

    private final StatisticSearchRepository statisticSearchRepository;

    public StatisticService(StatisticRepository statisticRepository, StatisticSearchRepository statisticSearchRepository) {
        this.statisticRepository = statisticRepository;
        this.statisticSearchRepository = statisticSearchRepository;
    }

    /**
     * Save a statistic.
     *
     * @param statistic the entity to save
     * @return the persisted entity
     */
    public Statistic save(Statistic statistic) {
        log.debug("Request to save Statistic : {}", statistic);
        Statistic result = statisticRepository.save(statistic);
        statisticSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the statistics.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Statistic> findAll(Pageable pageable) {
        log.debug("Request to get all Statistics");
        return statisticRepository.findAll(pageable);
    }

    /**
     * Get one statistic by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Statistic findOne(Long id) {
        log.debug("Request to get Statistic : {}", id);
        return statisticRepository.findOne(id);
    }

    /**
     * Delete the statistic by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Statistic : {}", id);
        statisticRepository.delete(id);
        statisticSearchRepository.delete(id);
    }

    /**
     * Search for the statistic corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Statistic> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Statistics for query {}", query);
        Page<Statistic> result = statisticSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
