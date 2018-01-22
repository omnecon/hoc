package click.hochzeit.service;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import click.hochzeit.domain.Gallery;
import click.hochzeit.domain.*; // for static metamodels
import click.hochzeit.repository.GalleryRepository;
import click.hochzeit.repository.search.GallerySearchRepository;
import click.hochzeit.service.dto.GalleryCriteria;

import click.hochzeit.domain.enumeration.GalleryType;

/**
 * Service for executing complex queries for Gallery entities in the database.
 * The main input is a {@link GalleryCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Gallery} or a {@link Page} of {@link Gallery} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class GalleryQueryService extends QueryService<Gallery> {

    private final Logger log = LoggerFactory.getLogger(GalleryQueryService.class);


    private final GalleryRepository galleryRepository;

    private final GallerySearchRepository gallerySearchRepository;

    public GalleryQueryService(GalleryRepository galleryRepository, GallerySearchRepository gallerySearchRepository) {
        this.galleryRepository = galleryRepository;
        this.gallerySearchRepository = gallerySearchRepository;
    }

    /**
     * Return a {@link List} of {@link Gallery} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Gallery> findByCriteria(GalleryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Gallery> specification = createSpecification(criteria);
        return galleryRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Gallery} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Gallery> findByCriteria(GalleryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Gallery> specification = createSpecification(criteria);
        return galleryRepository.findAll(specification, page);
    }

    /**
     * Function to convert GalleryCriteria to a {@link Specifications}
     */
    private Specifications<Gallery> createSpecification(GalleryCriteria criteria) {
        Specifications<Gallery> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Gallery_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Gallery_.name));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildSpecification(criteria.getType(), Gallery_.type));
            }
        }
        return specification;
    }

}
