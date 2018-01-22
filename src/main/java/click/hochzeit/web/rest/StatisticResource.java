package click.hochzeit.web.rest;

import com.codahale.metrics.annotation.Timed;
import click.hochzeit.domain.Statistic;
import click.hochzeit.service.StatisticService;
import click.hochzeit.web.rest.errors.BadRequestAlertException;
import click.hochzeit.web.rest.util.HeaderUtil;
import click.hochzeit.web.rest.util.PaginationUtil;
import click.hochzeit.service.dto.StatisticCriteria;
import click.hochzeit.service.StatisticQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Statistic.
 */
@RestController
@RequestMapping("/api")
public class StatisticResource {

    private final Logger log = LoggerFactory.getLogger(StatisticResource.class);

    private static final String ENTITY_NAME = "statistic";

    private final StatisticService statisticService;

    private final StatisticQueryService statisticQueryService;

    public StatisticResource(StatisticService statisticService, StatisticQueryService statisticQueryService) {
        this.statisticService = statisticService;
        this.statisticQueryService = statisticQueryService;
    }

    /**
     * POST  /statistics : Create a new statistic.
     *
     * @param statistic the statistic to create
     * @return the ResponseEntity with status 201 (Created) and with body the new statistic, or with status 400 (Bad Request) if the statistic has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/statistics")
    @Timed
    public ResponseEntity<Statistic> createStatistic(@RequestBody Statistic statistic) throws URISyntaxException {
        log.debug("REST request to save Statistic : {}", statistic);
        if (statistic.getId() != null) {
            throw new BadRequestAlertException("A new statistic cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Statistic result = statisticService.save(statistic);
        return ResponseEntity.created(new URI("/api/statistics/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /statistics : Updates an existing statistic.
     *
     * @param statistic the statistic to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated statistic,
     * or with status 400 (Bad Request) if the statistic is not valid,
     * or with status 500 (Internal Server Error) if the statistic couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/statistics")
    @Timed
    public ResponseEntity<Statistic> updateStatistic(@RequestBody Statistic statistic) throws URISyntaxException {
        log.debug("REST request to update Statistic : {}", statistic);
        if (statistic.getId() == null) {
            return createStatistic(statistic);
        }
        Statistic result = statisticService.save(statistic);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, statistic.getId().toString()))
            .body(result);
    }

    /**
     * GET  /statistics : get all the statistics.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of statistics in body
     */
    @GetMapping("/statistics")
    @Timed
    public ResponseEntity<List<Statistic>> getAllStatistics(StatisticCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Statistics by criteria: {}", criteria);
        Page<Statistic> page = statisticQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/statistics");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /statistics/:id : get the "id" statistic.
     *
     * @param id the id of the statistic to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the statistic, or with status 404 (Not Found)
     */
    @GetMapping("/statistics/{id}")
    @Timed
    public ResponseEntity<Statistic> getStatistic(@PathVariable Long id) {
        log.debug("REST request to get Statistic : {}", id);
        Statistic statistic = statisticService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(statistic));
    }

    /**
     * DELETE  /statistics/:id : delete the "id" statistic.
     *
     * @param id the id of the statistic to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/statistics/{id}")
    @Timed
    public ResponseEntity<Void> deleteStatistic(@PathVariable Long id) {
        log.debug("REST request to delete Statistic : {}", id);
        statisticService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/statistics?query=:query : search for the statistic corresponding
     * to the query.
     *
     * @param query the query of the statistic search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/statistics")
    @Timed
    public ResponseEntity<List<Statistic>> searchStatistics(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Statistics for query {}", query);
        Page<Statistic> page = statisticService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/statistics");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
