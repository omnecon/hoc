package click.hochzeit.web.rest;

import click.hochzeit.Hochzeitclick11App;

import click.hochzeit.domain.Feature;
import click.hochzeit.repository.FeatureRepository;
import click.hochzeit.service.FeatureService;
import click.hochzeit.repository.search.FeatureSearchRepository;
import click.hochzeit.web.rest.errors.ExceptionTranslator;

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

/**
 * Test class for the FeatureResource REST controller.
 *
 * @see FeatureResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Hochzeitclick11App.class)
public class FeatureResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private FeatureRepository featureRepository;

    @Autowired
    private FeatureService featureService;

    @Autowired
    private FeatureSearchRepository featureSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFeatureMockMvc;

    private Feature feature;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FeatureResource featureResource = new FeatureResource(featureService);
        this.restFeatureMockMvc = MockMvcBuilders.standaloneSetup(featureResource)
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
    public static Feature createEntity(EntityManager em) {
        Feature feature = new Feature()
            .name(DEFAULT_NAME);
        return feature;
    }

    @Before
    public void initTest() {
        featureSearchRepository.deleteAll();
        feature = createEntity(em);
    }

    @Test
    @Transactional
    public void createFeature() throws Exception {
        int databaseSizeBeforeCreate = featureRepository.findAll().size();

        // Create the Feature
        restFeatureMockMvc.perform(post("/api/features")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(feature)))
            .andExpect(status().isCreated());

        // Validate the Feature in the database
        List<Feature> featureList = featureRepository.findAll();
        assertThat(featureList).hasSize(databaseSizeBeforeCreate + 1);
        Feature testFeature = featureList.get(featureList.size() - 1);
        assertThat(testFeature.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the Feature in Elasticsearch
        Feature featureEs = featureSearchRepository.findOne(testFeature.getId());
        assertThat(featureEs).isEqualToIgnoringGivenFields(testFeature);
    }

    @Test
    @Transactional
    public void createFeatureWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = featureRepository.findAll().size();

        // Create the Feature with an existing ID
        feature.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFeatureMockMvc.perform(post("/api/features")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(feature)))
            .andExpect(status().isBadRequest());

        // Validate the Feature in the database
        List<Feature> featureList = featureRepository.findAll();
        assertThat(featureList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllFeatures() throws Exception {
        // Initialize the database
        featureRepository.saveAndFlush(feature);

        // Get all the featureList
        restFeatureMockMvc.perform(get("/api/features?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(feature.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getFeature() throws Exception {
        // Initialize the database
        featureRepository.saveAndFlush(feature);

        // Get the feature
        restFeatureMockMvc.perform(get("/api/features/{id}", feature.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(feature.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFeature() throws Exception {
        // Get the feature
        restFeatureMockMvc.perform(get("/api/features/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFeature() throws Exception {
        // Initialize the database
        featureService.save(feature);

        int databaseSizeBeforeUpdate = featureRepository.findAll().size();

        // Update the feature
        Feature updatedFeature = featureRepository.findOne(feature.getId());
        // Disconnect from session so that the updates on updatedFeature are not directly saved in db
        em.detach(updatedFeature);
        updatedFeature
            .name(UPDATED_NAME);

        restFeatureMockMvc.perform(put("/api/features")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFeature)))
            .andExpect(status().isOk());

        // Validate the Feature in the database
        List<Feature> featureList = featureRepository.findAll();
        assertThat(featureList).hasSize(databaseSizeBeforeUpdate);
        Feature testFeature = featureList.get(featureList.size() - 1);
        assertThat(testFeature.getName()).isEqualTo(UPDATED_NAME);

        // Validate the Feature in Elasticsearch
        Feature featureEs = featureSearchRepository.findOne(testFeature.getId());
        assertThat(featureEs).isEqualToIgnoringGivenFields(testFeature);
    }

    @Test
    @Transactional
    public void updateNonExistingFeature() throws Exception {
        int databaseSizeBeforeUpdate = featureRepository.findAll().size();

        // Create the Feature

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFeatureMockMvc.perform(put("/api/features")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(feature)))
            .andExpect(status().isCreated());

        // Validate the Feature in the database
        List<Feature> featureList = featureRepository.findAll();
        assertThat(featureList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFeature() throws Exception {
        // Initialize the database
        featureService.save(feature);

        int databaseSizeBeforeDelete = featureRepository.findAll().size();

        // Get the feature
        restFeatureMockMvc.perform(delete("/api/features/{id}", feature.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean featureExistsInEs = featureSearchRepository.exists(feature.getId());
        assertThat(featureExistsInEs).isFalse();

        // Validate the database is empty
        List<Feature> featureList = featureRepository.findAll();
        assertThat(featureList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchFeature() throws Exception {
        // Initialize the database
        featureService.save(feature);

        // Search the feature
        restFeatureMockMvc.perform(get("/api/_search/features?query=id:" + feature.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(feature.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Feature.class);
        Feature feature1 = new Feature();
        feature1.setId(1L);
        Feature feature2 = new Feature();
        feature2.setId(feature1.getId());
        assertThat(feature1).isEqualTo(feature2);
        feature2.setId(2L);
        assertThat(feature1).isNotEqualTo(feature2);
        feature1.setId(null);
        assertThat(feature1).isNotEqualTo(feature2);
    }
}
