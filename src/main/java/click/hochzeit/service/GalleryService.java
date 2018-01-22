package click.hochzeit.service;

import click.hochzeit.domain.Gallery;
import click.hochzeit.repository.GalleryRepository;
import click.hochzeit.repository.search.GallerySearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Gallery.
 */
@Service
@Transactional
public class GalleryService {

    private final Logger log = LoggerFactory.getLogger(GalleryService.class);

    private final GalleryRepository galleryRepository;

    private final GallerySearchRepository gallerySearchRepository;

    public GalleryService(GalleryRepository galleryRepository, GallerySearchRepository gallerySearchRepository) {
        this.galleryRepository = galleryRepository;
        this.gallerySearchRepository = gallerySearchRepository;
    }

    /**
     * Save a gallery.
     *
     * @param gallery the entity to save
     * @return the persisted entity
     */
    public Gallery save(Gallery gallery) {
        log.debug("Request to save Gallery : {}", gallery);
        Gallery result = galleryRepository.save(gallery);
        gallerySearchRepository.save(result);
        return result;
    }

    /**
     * Get all the galleries.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Gallery> findAll(Pageable pageable) {
        log.debug("Request to get all Galleries");
        return galleryRepository.findAll(pageable);
    }

    /**
     * Get one gallery by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Gallery findOne(Long id) {
        log.debug("Request to get Gallery : {}", id);
        return galleryRepository.findOne(id);
    }

    /**
     * Delete the gallery by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Gallery : {}", id);
        galleryRepository.delete(id);
        gallerySearchRepository.delete(id);
    }

    /**
     * Search for the gallery corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Gallery> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Galleries for query {}", query);
        Page<Gallery> result = gallerySearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
