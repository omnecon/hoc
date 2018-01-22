package click.hochzeit.web.rest;

import com.codahale.metrics.annotation.Timed;
import click.hochzeit.domain.Feature;
import click.hochzeit.service.FeatureService;
import click.hochzeit.web.rest.errors.BadRequestAlertException;
import click.hochzeit.web.rest.util.HeaderUtil;
import click.hochzeit.web.rest.util.PaginationUtil;
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
 * REST controller for managing Feature.
 */
@RestController
@RequestMapping("/api")
public class FeatureResource {

    private final Logger log = LoggerFactory.getLogger(FeatureResource.class);

    private static final String ENTITY_NAME = "feature";

    private final FeatureService featureService;

    public FeatureResource(FeatureService featureService) {
        this.featureService = featureService;
    }

    /**
     * POST  /features : Create a new feature.
     *
     * @param feature the feature to create
     * @return the ResponseEntity with status 201 (Created) and with body the new feature, or with status 400 (Bad Request) if the feature has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/features")
    @Timed
    public ResponseEntity<Feature> createFeature(@RequestBody Feature feature) throws URISyntaxException {
        log.debug("REST request to save Feature : {}", feature);
        if (feature.getId() != null) {
            throw new BadRequestAlertException("A new feature cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Feature result = featureService.save(feature);
        return ResponseEntity.created(new URI("/api/features/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /features : Updates an existing feature.
     *
     * @param feature the feature to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated feature,
     * or with status 400 (Bad Request) if the feature is not valid,
     * or with status 500 (Internal Server Error) if the feature couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/features")
    @Timed
    public ResponseEntity<Feature> updateFeature(@RequestBody Feature feature) throws URISyntaxException {
        log.debug("REST request to update Feature : {}", feature);
        if (feature.getId() == null) {
            return createFeature(feature);
        }
        Feature result = featureService.save(feature);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, feature.getId().toString()))
            .body(result);
    }

    /**
     * GET  /features : get all the features.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of features in body
     */
    @GetMapping("/features")
    @Timed
    public ResponseEntity<List<Feature>> getAllFeatures(Pageable pageable) {
        log.debug("REST request to get a page of Features");
        Page<Feature> page = featureService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/features");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /features/:id : get the "id" feature.
     *
     * @param id the id of the feature to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the feature, or with status 404 (Not Found)
     */
    @GetMapping("/features/{id}")
    @Timed
    public ResponseEntity<Feature> getFeature(@PathVariable Long id) {
        log.debug("REST request to get Feature : {}", id);
        Feature feature = featureService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(feature));
    }

    /**
     * DELETE  /features/:id : delete the "id" feature.
     *
     * @param id the id of the feature to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/features/{id}")
    @Timed
    public ResponseEntity<Void> deleteFeature(@PathVariable Long id) {
        log.debug("REST request to delete Feature : {}", id);
        featureService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/features?query=:query : search for the feature corresponding
     * to the query.
     *
     * @param query the query of the feature search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/features")
    @Timed
    public ResponseEntity<List<Feature>> searchFeatures(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Features for query {}", query);
        Page<Feature> page = featureService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/features");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
