package click.hochzeit.service;

import click.hochzeit.domain.Banner;
import click.hochzeit.repository.BannerRepository;
import click.hochzeit.repository.search.BannerSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Banner.
 */
@Service
@Transactional
public class BannerService {

    private final Logger log = LoggerFactory.getLogger(BannerService.class);

    private final BannerRepository bannerRepository;

    private final BannerSearchRepository bannerSearchRepository;

    public BannerService(BannerRepository bannerRepository, BannerSearchRepository bannerSearchRepository) {
        this.bannerRepository = bannerRepository;
        this.bannerSearchRepository = bannerSearchRepository;
    }

    /**
     * Save a banner.
     *
     * @param banner the entity to save
     * @return the persisted entity
     */
    public Banner save(Banner banner) {
        log.debug("Request to save Banner : {}", banner);
        Banner result = bannerRepository.save(banner);
        bannerSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the banners.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Banner> findAll(Pageable pageable) {
        log.debug("Request to get all Banners");
        return bannerRepository.findAll(pageable);
    }

    /**
     * Get one banner by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Banner findOne(Long id) {
        log.debug("Request to get Banner : {}", id);
        return bannerRepository.findOne(id);
    }

    /**
     * Delete the banner by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Banner : {}", id);
        bannerRepository.delete(id);
        bannerSearchRepository.delete(id);
    }

    /**
     * Search for the banner corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Banner> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Banners for query {}", query);
        Page<Banner> result = bannerSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
