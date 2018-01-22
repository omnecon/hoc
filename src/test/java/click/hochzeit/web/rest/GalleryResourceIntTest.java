package click.hochzeit.web.rest;

import click.hochzeit.Hochzeitclick11App;

import click.hochzeit.domain.Gallery;
import click.hochzeit.repository.GalleryRepository;
import click.hochzeit.service.GalleryService;
import click.hochzeit.repository.search.GallerySearchRepository;
import click.hochzeit.web.rest.errors.ExceptionTranslator;
import click.hochzeit.service.dto.GalleryCriteria;
import click.hochzeit.service.GalleryQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static click.hochzeit.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import click.hochzeit.domain.enumeration.GalleryType;
/**
 * Test class for the GalleryResource REST controller.
 *
 * @see GalleryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Hochzeitclick11App.class)
public class GalleryResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final GalleryType DEFAULT_TYPE = GalleryType.PORTFOLIO;
    private static final GalleryType UPDATED_TYPE = GalleryType.LOCATION;

    @Autowired
    private GalleryRepository galleryRepository;

    @Autowired
    private GalleryService galleryService;

    @Autowired
    private GallerySearchRepository gallerySearchRepository;

    @Autowired
    private GalleryQueryService galleryQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGalleryMockMvc;

    private Gallery gallery;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GalleryResource galleryResource = new GalleryResource(galleryService, galleryQueryService);
        this.restGalleryMockMvc = MockMvcBuilders.standaloneSetup(galleryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Gallery createEntity(EntityManager em) {
        Gallery gallery = new Gallery()
            .name(DEFAULT_NAME)
            .type(DEFAULT_TYPE);
        return gallery;
    }

    @Before
    public void initTest() {
        gallerySearchRepository.deleteAll();
        gallery = createEntity(em);
    }

    @Test
    @Transactional
    public void createGallery() throws Exception {
        int databaseSizeBeforeCreate = galleryRepository.findAll().size();

        // Create the Gallery
        restGalleryMockMvc.perform(post("/api/galleries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gallery)))
            .andExpect(status().isCreated());

        // Validate the Gallery in the database
        List<Gallery> galleryList = galleryRepository.findAll();
        assertThat(galleryList).hasSize(databaseSizeBeforeCreate + 1);
        Gallery testGallery = galleryList.get(galleryList.size() - 1);
        assertThat(testGallery.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testGallery.getType()).isEqualTo(DEFAULT_TYPE);

        // Validate the Gallery in Elasticsearch
        Gallery galleryEs = gallerySearchRepository.findOne(testGallery.getId());
        assertThat(galleryEs).isEqualToIgnoringGivenFields(testGallery);
    }

    @Test
    @Transactional
    public void createGalleryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = galleryRepository.findAll().size();

        // Create the Gallery with an existing ID
        gallery.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGalleryMockMvc.perform(post("/api/galleries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gallery)))
            .andExpect(status().isBadRequest());

        // Validate the Gallery in the database
        List<Gallery> galleryList = galleryRepository.findAll();
        assertThat(galleryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllGalleries() throws Exception {
        // Initialize the database
        galleryRepository.saveAndFlush(gallery);

        // Get all the galleryList
        restGalleryMockMvc.perform(get("/api/galleries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gallery.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getGallery() throws Exception {
        // Initialize the database
        galleryRepository.saveAndFlush(gallery);

        // Get the gallery
        restGalleryMockMvc.perform(get("/api/galleries/{id}", gallery.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(gallery.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getAllGalleriesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        galleryRepository.saveAndFlush(gallery);

        // Get all the galleryList where name equals to DEFAULT_NAME
        defaultGalleryShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the galleryList where name equals to UPDATED_NAME
        defaultGalleryShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllGalleriesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        galleryRepository.saveAndFlush(gallery);

        // Get all the galleryList where name in DEFAULT_NAME or UPDATED_NAME
        defaultGalleryShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the galleryList where name equals to UPDATED_NAME
        defaultGalleryShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllGalleriesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        galleryRepository.saveAndFlush(gallery);

        // Get all the galleryList where name is not null
        defaultGalleryShouldBeFound("name.specified=true");

        // Get all the galleryList where name is null
        defaultGalleryShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllGalleriesByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        galleryRepository.saveAndFlush(gallery);

        // Get all the galleryList where type equals to DEFAULT_TYPE
        defaultGalleryShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the galleryList where type equals to UPDATED_TYPE
        defaultGalleryShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllGalleriesByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        galleryRepository.saveAndFlush(gallery);

        // Get all the galleryList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultGalleryShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the galleryList where type equals to UPDATED_TYPE
        defaultGalleryShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllGalleriesByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        galleryRepository.saveAndFlush(gallery);

        // Get all the galleryList where type is not null
        defaultGalleryShouldBeFound("type.specified=true");

        // Get all the galleryList where type is null
        defaultGalleryShouldNotBeFound("type.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultGalleryShouldBeFound(String filter) throws Exception {
        restGalleryMockMvc.perform(get("/api/galleries?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gallery.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultGalleryShouldNotBeFound(String filter) throws Exception {
        restGalleryMockMvc.perform(get("/api/galleries?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingGallery() throws Exception {
        // Get the gallery
        restGalleryMockMvc.perform(get("/api/galleries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGallery() throws Exception {
        // Initialize the database
        galleryService.save(gallery);

        int databaseSizeBeforeUpdate = galleryRepository.findAll().size();

        // Update the gallery
        Gallery updatedGallery = galleryRepository.findOne(gallery.getId());
        // Disconnect from session so that the updates on updatedGallery are not directly saved in db
        em.detach(updatedGallery);
        updatedGallery
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE);

        restGalleryMockMvc.perform(put("/api/galleries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGallery)))
            .andExpect(status().isOk());

        // Validate the Gallery in the database
        List<Gallery> galleryList = galleryRepository.findAll();
        assertThat(galleryList).hasSize(databaseSizeBeforeUpdate);
        Gallery testGallery = galleryList.get(galleryList.size() - 1);
        assertThat(testGallery.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGallery.getType()).isEqualTo(UPDATED_TYPE);

        // Validate the Gallery in Elasticsearch
        Gallery galleryEs = gallerySearchRepository.findOne(testGallery.getId());
        assertThat(galleryEs).isEqualToIgnoringGivenFields(testGallery);
    }

    @Test
    @Transactional
    public void updateNonExistingGallery() throws Exception {
        int databaseSizeBeforeUpdate = galleryRepository.findAll().size();

        // Create the Gallery

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restGalleryMockMvc.perform(put("/api/galleries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gallery)))
            .andExpect(status().isCreated());

        // Validate the Gallery in the database
        List<Gallery> galleryList = galleryRepository.findAll();
        assertThat(galleryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteGallery() throws Exception {
        // Initialize the database
        galleryService.save(gallery);

        int databaseSizeBeforeDelete = galleryRepository.findAll().size();

        // Get the gallery
        restGalleryMockMvc.perform(delete("/api/galleries/{id}", gallery.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean galleryExistsInEs = gallerySearchRepository.exists(gallery.getId());
        assertThat(galleryExistsInEs).isFalse();

        // Validate the database is empty
        List<Gallery> galleryList = galleryRepository.findAll();
        assertThat(galleryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchGallery() throws Exception {
        // Initialize the database
        galleryService.save(gallery);

        // Search the gallery
        restGalleryMockMvc.perform(get("/api/_search/galleries?query=id:" + gallery.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gallery.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Gallery.class);
        Gallery gallery1 = new Gallery();
        gallery1.setId(1L);
        Gallery gallery2 = new Gallery();
        gallery2.setId(gallery1.getId());
        assertThat(gallery1).isEqualTo(gallery2);
        gallery2.setId(2L);
        assertThat(gallery1).isNotEqualTo(gallery2);
        gallery1.setId(null);
        assertThat(gallery1).isNotEqualTo(gallery2);
    }
}
