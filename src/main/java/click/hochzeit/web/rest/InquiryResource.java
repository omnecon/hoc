package click.hochzeit.web.rest;

import com.codahale.metrics.annotation.Timed;
import click.hochzeit.domain.Inquiry;
import click.hochzeit.service.InquiryService;
import click.hochzeit.web.rest.errors.BadRequestAlertException;
import click.hochzeit.web.rest.util.HeaderUtil;
import click.hochzeit.web.rest.util.PaginationUtil;
import click.hochzeit.service.dto.InquiryCriteria;
import click.hochzeit.service.InquiryQueryService;
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
 * REST controller for managing Inquiry.
 */
@RestController
@RequestMapping("/api")
public class InquiryResource {

    private final Logger log = LoggerFactory.getLogger(InquiryResource.class);

    private static final String ENTITY_NAME = "inquiry";

    private final InquiryService inquiryService;

    private final InquiryQueryService inquiryQueryService;

    public InquiryResource(InquiryService inquiryService, InquiryQueryService inquiryQueryService) {
        this.inquiryService = inquiryService;
        this.inquiryQueryService = inquiryQueryService;
    }

    /**
     * POST  /inquiries : Create a new inquiry.
     *
     * @param inquiry the inquiry to create
     * @return the ResponseEntity with status 201 (Created) and with body the new inquiry, or with status 400 (Bad Request) if the inquiry has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/inquiries")
    @Timed
    public ResponseEntity<Inquiry> createInquiry(@RequestBody Inquiry inquiry) throws URISyntaxException {
        log.debug("REST request to save Inquiry : {}", inquiry);
        if (inquiry.getId() != null) {
            throw new BadRequestAlertException("A new inquiry cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Inquiry result = inquiryService.save(inquiry);
        return ResponseEntity.created(new URI("/api/inquiries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /inquiries : Updates an existing inquiry.
     *
     * @param inquiry the inquiry to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated inquiry,
     * or with status 400 (Bad Request) if the inquiry is not valid,
     * or with status 500 (Internal Server Error) if the inquiry couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/inquiries")
    @Timed
    public ResponseEntity<Inquiry> updateInquiry(@RequestBody Inquiry inquiry) throws URISyntaxException {
        log.debug("REST request to update Inquiry : {}", inquiry);
        if (inquiry.getId() == null) {
            return createInquiry(inquiry);
        }
        Inquiry result = inquiryService.save(inquiry);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, inquiry.getId().toString()))
            .body(result);
    }

    /**
     * GET  /inquiries : get all the inquiries.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of inquiries in body
     */
    @GetMapping("/inquiries")
    @Timed
    public ResponseEntity<List<Inquiry>> getAllInquiries(InquiryCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Inquiries by criteria: {}", criteria);
        Page<Inquiry> page = inquiryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/inquiries");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /inquiries/:id : get the "id" inquiry.
     *
     * @param id the id of the inquiry to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the inquiry, or with status 404 (Not Found)
     */
    @GetMapping("/inquiries/{id}")
    @Timed
    public ResponseEntity<Inquiry> getInquiry(@PathVariable Long id) {
        log.debug("REST request to get Inquiry : {}", id);
        Inquiry inquiry = inquiryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(inquiry));
    }

    /**
     * DELETE  /inquiries/:id : delete the "id" inquiry.
     *
     * @param id the id of the inquiry to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/inquiries/{id}")
    @Timed
    public ResponseEntity<Void> deleteInquiry(@PathVariable Long id) {
        log.debug("REST request to delete Inquiry : {}", id);
        inquiryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/inquiries?query=:query : search for the inquiry corresponding
     * to the query.
     *
     * @param query the query of the inquiry search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/inquiries")
    @Timed
    public ResponseEntity<List<Inquiry>> searchInquiries(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Inquiries for query {}", query);
        Page<Inquiry> page = inquiryService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/inquiries");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
