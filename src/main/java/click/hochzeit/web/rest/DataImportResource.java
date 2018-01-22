package click.hochzeit.web.rest;

import com.codahale.metrics.annotation.Timed;
import click.hochzeit.domain.DataImport;
import click.hochzeit.service.DataImportService;
import click.hochzeit.service.WordpressImporter;
import click.hochzeit.web.rest.errors.BadRequestAlertException;
import click.hochzeit.web.rest.util.HeaderUtil;
import click.hochzeit.web.rest.util.PaginationUtil;
import click.hochzeit.service.dto.DataImportCriteria;
import click.hochzeit.service.DataImportQueryService;
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
 * REST controller for managing DataImport.
 */
@RestController
@RequestMapping("/api")
public class DataImportResource {

    private final Logger log = LoggerFactory.getLogger(DataImportResource.class);

    private static final String ENTITY_NAME = "dataImport";

    private final DataImportService dataImportService;

    private final DataImportQueryService dataImportQueryService;
    
    private final WordpressImporter wordpressImporter;

    public DataImportResource(DataImportService dataImportService, DataImportQueryService dataImportQueryService, WordpressImporter wordpressImporter) {
        this.dataImportService = dataImportService;
        this.dataImportQueryService = dataImportQueryService;
        this.wordpressImporter = wordpressImporter;
    }

    /**
     * POST  /data-imports : Create a new dataImport.
     *
     * @param dataImport the dataImport to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dataImport, or with status 400 (Bad Request) if the dataImport has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/data-imports")
    @Timed
    public ResponseEntity<DataImport> createDataImport(@RequestBody DataImport dataImport) throws URISyntaxException {
        log.debug("REST request to save DataImport : {}", dataImport);
        if (dataImport.getId() != null) {
            throw new BadRequestAlertException("A new dataImport cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DataImport result = dataImportService.save(dataImport);
        return ResponseEntity.created(new URI("/api/data-imports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /data-imports : Updates an existing dataImport.
     *
     * @param dataImport the dataImport to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dataImport,
     * or with status 400 (Bad Request) if the dataImport is not valid,
     * or with status 500 (Internal Server Error) if the dataImport couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/data-imports")
    @Timed
    public ResponseEntity<DataImport> updateDataImport(@RequestBody DataImport dataImport) throws URISyntaxException {
        log.debug("REST request to update DataImport : {}", dataImport);
        if (dataImport.getId() == null) {
            return createDataImport(dataImport);
        }
        DataImport result = dataImportService.save(dataImport);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dataImport.getId().toString()))
            .body(result);
    }

    /**
     * GET  /data-imports : get all the dataImports.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of dataImports in body
     */
    @GetMapping("/data-imports")
    @Timed
    public ResponseEntity<List<DataImport>> getAllDataImports(DataImportCriteria criteria, Pageable pageable) {
        log.debug("REST request to get DataImports by criteria: {}", criteria);
        Page<DataImport> page = dataImportQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/data-imports");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /data-imports/:id : get the "id" dataImport.
     *
     * @param id the id of the dataImport to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dataImport, or with status 404 (Not Found)
     */
    @GetMapping("/data-imports/{id}")
    @Timed
    public ResponseEntity<DataImport> getDataImport(@PathVariable Long id) {
        log.debug("REST request to get DataImport : {}", id);
        
        if (id == (long)1111) {
        	wordpressImporter.storeFeatures();
        } else if (id == 2222) {
        	wordpressImporter.importWordpressDB(15);
        }
        
        DataImport dataImport = dataImportService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(dataImport));
    }

    /**
     * DELETE  /data-imports/:id : delete the "id" dataImport.
     *
     * @param id the id of the dataImport to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/data-imports/{id}")
    @Timed
    public ResponseEntity<Void> deleteDataImport(@PathVariable Long id) {
        log.debug("REST request to delete DataImport : {}", id);
        dataImportService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/data-imports?query=:query : search for the dataImport corresponding
     * to the query.
     *
     * @param query the query of the dataImport search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/data-imports")
    @Timed
    public ResponseEntity<List<DataImport>> searchDataImports(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of DataImports for query {}", query);
        Page<DataImport> page = dataImportService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/data-imports");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
