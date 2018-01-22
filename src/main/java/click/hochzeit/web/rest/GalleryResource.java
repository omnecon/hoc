package click.hochzeit.web.rest;

import com.codahale.metrics.annotation.Timed;
import click.hochzeit.domain.Gallery;
import click.hochzeit.service.GalleryService;
import click.hochzeit.web.rest.errors.BadRequestAlertException;
import click.hochzeit.web.rest.util.HeaderUtil;
import click.hochzeit.web.rest.util.PaginationUtil;
import click.hochzeit.service.dto.GalleryCriteria;
import click.hochzeit.service.GalleryQueryService;
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
 * REST controller for managing Gallery.
 */
@RestController
@RequestMapping("/api")
public class GalleryResource {

    private final Logger log = LoggerFactory.getLogger(GalleryResource.class);

    private static final String ENTITY_NAME = "gallery";

    private final GalleryService galleryService;

    private final GalleryQueryService galleryQueryService;

    public GalleryResource(GalleryService galleryService, GalleryQueryService galleryQueryService) {
        this.galleryService = galleryService;
        this.galleryQueryService = galleryQueryService;
    }

    /**
     * POST  /galleries : Create a new gallery.
     *
     * @param gallery the gallery to create
     * @return the ResponseEntity with status 201 (Created) and with body the new gallery, or with status 400 (Bad Request) if the gallery has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/galleries")
    @Timed
    public ResponseEntity<Gallery> createGallery(@RequestBody Gallery gallery) throws URISyntaxException {
        log.debug("REST request to save Gallery : {}", gallery);
        if (gallery.getId() != null) {
            throw new BadRequestAlertException("A new gallery cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Gallery result = galleryService.save(gallery);
        return ResponseEntity.created(new URI("/api/galleries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /galleries : Updates an existing gallery.
     *
     * @param gallery the gallery to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated gallery,
     * or with status 400 (Bad Request) if the gallery is not valid,
     * or with status 500 (Internal Server Error) if the gallery couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/galleries")
    @Timed
    public ResponseEntity<Gallery> updateGallery(@RequestBody Gallery gallery) throws URISyntaxException {
        log.debug("REST request to update Gallery : {}", gallery);
        if (gallery.getId() == null) {
            return createGallery(gallery);
        }
        Gallery result = galleryService.save(gallery);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, gallery.getId().toString()))
            .body(result);
    }

    /**
     * GET  /galleries : get all the galleries.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of galleries in body
     */
    @GetMapping("/galleries")
    @Timed
    public ResponseEntity<List<Gallery>> getAllGalleries(GalleryCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Galleries by criteria: {}", criteria);
        Page<Gallery> page = galleryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/galleries");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /galleries/:id : get the "id" gallery.
     *
     * @param id the id of the gallery to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the gallery, or with status 404 (Not Found)
     */
    @GetMapping("/galleries/{id}")
    @Timed
    public ResponseEntity<Gallery> getGallery(@PathVariable Long id) {
        log.debug("REST request to get Gallery : {}", id);
        Gallery gallery = galleryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(gallery));
    }

    /**
     * DELETE  /galleries/:id : delete the "id" gallery.
     *
     * @param id the id of the gallery to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/galleries/{id}")
    @Timed
    public ResponseEntity<Void> deleteGallery(@PathVariable Long id) {
        log.debug("REST request to delete Gallery : {}", id);
        galleryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/galleries?query=:query : search for the gallery corresponding
     * to the query.
     *
     * @param query the query of the gallery search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/galleries")
    @Timed
    public ResponseEntity<List<Gallery>> searchGalleries(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Galleries for query {}", query);
        Page<Gallery> page = galleryService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/galleries");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
