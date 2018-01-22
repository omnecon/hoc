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

import click.hochzeit.domain.Image;
import click.hochzeit.domain.*; // for static metamodels
import click.hochzeit.repository.ImageRepository;
import click.hochzeit.repository.search.ImageSearchRepository;
import click.hochzeit.service.dto.ImageCriteria;


/**
 * Service for executing complex queries for Image entities in the database.
 * The main input is a {@link ImageCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Image} or a {@link Page} of {@link Image} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ImageQueryService extends QueryService<Image> {

    private final Logger log = LoggerFactory.getLogger(ImageQueryService.class);


    private final ImageRepository imageRepository;

    private final ImageSearchRepository imageSearchRepository;

    public ImageQueryService(ImageRepository imageRepository, ImageSearchRepository imageSearchRepository) {
        this.imageRepository = imageRepository;
        this.imageSearchRepository = imageSearchRepository;
    }

    /**
     * Return a {@link List} of {@link Image} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Image> findByCriteria(ImageCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Image> specification = createSpecification(criteria);
        return imageRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Image} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Image> findByCriteria(ImageCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Image> specification = createSpecification(criteria);
        return imageRepository.findAll(specification, page);
    }

    /**
     * Function to convert ImageCriteria to a {@link Specifications}
     */
    private Specifications<Image> createSpecification(ImageCriteria criteria) {
        Specifications<Image> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Image_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), Image_.title));
            }
            if (criteria.getAlt() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAlt(), Image_.alt));
            }
            if (criteria.getCaption() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCaption(), Image_.caption));
            }
            if (criteria.getImgOriginal() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImgOriginal(), Image_.imgOriginal));
            }
            if (criteria.getImgLarge() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImgLarge(), Image_.imgLarge));
            }
            if (criteria.getImgThumbnail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImgThumbnail(), Image_.imgThumbnail));
            }
        }
        return specification;
    }

}
