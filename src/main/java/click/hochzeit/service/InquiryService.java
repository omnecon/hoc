package click.hochzeit.service;

import click.hochzeit.domain.Inquiry;
import click.hochzeit.repository.InquiryRepository;
import click.hochzeit.repository.search.InquirySearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Inquiry.
 */
@Service
@Transactional
public class InquiryService {

    private final Logger log = LoggerFactory.getLogger(InquiryService.class);

    private final InquiryRepository inquiryRepository;

    private final InquirySearchRepository inquirySearchRepository;

    public InquiryService(InquiryRepository inquiryRepository, InquirySearchRepository inquirySearchRepository) {
        this.inquiryRepository = inquiryRepository;
        this.inquirySearchRepository = inquirySearchRepository;
    }

    /**
     * Save a inquiry.
     *
     * @param inquiry the entity to save
     * @return the persisted entity
     */
    public Inquiry save(Inquiry inquiry) {
        log.debug("Request to save Inquiry : {}", inquiry);
        Inquiry result = inquiryRepository.save(inquiry);
        inquirySearchRepository.save(result);
        return result;
    }

    /**
     * Get all the inquiries.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Inquiry> findAll(Pageable pageable) {
        log.debug("Request to get all Inquiries");
        return inquiryRepository.findAll(pageable);
    }

    /**
     * Get one inquiry by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Inquiry findOne(Long id) {
        log.debug("Request to get Inquiry : {}", id);
        return inquiryRepository.findOne(id);
    }

    /**
     * Delete the inquiry by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Inquiry : {}", id);
        inquiryRepository.delete(id);
        inquirySearchRepository.delete(id);
    }

    /**
     * Search for the inquiry corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Inquiry> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Inquiries for query {}", query);
        Page<Inquiry> result = inquirySearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
