package click.hochzeit.web.rest;

import click.hochzeit.Hochzeitclick11App;

import click.hochzeit.domain.Banner;
import click.hochzeit.repository.BannerRepository;
import click.hochzeit.service.BannerService;
import click.hochzeit.repository.search.BannerSearchRepository;
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
 * Test class for the BannerResource REST controller.
 *
 * @see BannerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Hochzeitclick11App.class)
public class BannerResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private BannerRepository bannerRepository;

    @Autowired
    private BannerService bannerService;

    @Autowired
    private BannerSearchRepository bannerSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBannerMockMvc;

    private Banner banner;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BannerResource bannerResource = new BannerResource(bannerService);
        this.restBannerMockMvc = MockMvcBuilders.standaloneSetup(bannerResource)
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
    public static Banner createEntity(EntityManager em) {
        Banner banner = new Banner()
            .name(DEFAULT_NAME);
        return banner;
    }

    @Before
    public void initTest() {
        bannerSearchRepository.deleteAll();
        banner = createEntity(em);
    }

    @Test
    @Transactional
    public void createBanner() throws Exception {
        int databaseSizeBeforeCreate = bannerRepository.findAll().size();

        // Create the Banner
        restBannerMockMvc.perform(post("/api/banners")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(banner)))
            .andExpect(status().isCreated());

        // Validate the Banner in the database
        List<Banner> bannerList = bannerRepository.findAll();
        assertThat(bannerList).hasSize(databaseSizeBeforeCreate + 1);
        Banner testBanner = bannerList.get(bannerList.size() - 1);
        assertThat(testBanner.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the Banner in Elasticsearch
        Banner bannerEs = bannerSearchRepository.findOne(testBanner.getId());
        assertThat(bannerEs).isEqualToIgnoringGivenFields(testBanner);
    }

    @Test
    @Transactional
    public void createBannerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bannerRepository.findAll().size();

        // Create the Banner with an existing ID
        banner.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBannerMockMvc.perform(post("/api/banners")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(banner)))
            .andExpect(status().isBadRequest());

        // Validate the Banner in the database
        List<Banner> bannerList = bannerRepository.findAll();
        assertThat(bannerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBanners() throws Exception {
        // Initialize the database
        bannerRepository.saveAndFlush(banner);

        // Get all the bannerList
        restBannerMockMvc.perform(get("/api/banners?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(banner.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getBanner() throws Exception {
        // Initialize the database
        bannerRepository.saveAndFlush(banner);

        // Get the banner
        restBannerMockMvc.perform(get("/api/banners/{id}", banner.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(banner.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBanner() throws Exception {
        // Get the banner
        restBannerMockMvc.perform(get("/api/banners/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBanner() throws Exception {
        // Initialize the database
        bannerService.save(banner);

        int databaseSizeBeforeUpdate = bannerRepository.findAll().size();

        // Update the banner
        Banner updatedBanner = bannerRepository.findOne(banner.getId());
        // Disconnect from session so that the updates on updatedBanner are not directly saved in db
        em.detach(updatedBanner);
        updatedBanner
            .name(UPDATED_NAME);

        restBannerMockMvc.perform(put("/api/banners")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBanner)))
            .andExpect(status().isOk());

        // Validate the Banner in the database
        List<Banner> bannerList = bannerRepository.findAll();
        assertThat(bannerList).hasSize(databaseSizeBeforeUpdate);
        Banner testBanner = bannerList.get(bannerList.size() - 1);
        assertThat(testBanner.getName()).isEqualTo(UPDATED_NAME);

        // Validate the Banner in Elasticsearch
        Banner bannerEs = bannerSearchRepository.findOne(testBanner.getId());
        assertThat(bannerEs).isEqualToIgnoringGivenFields(testBanner);
    }

    @Test
    @Transactional
    public void updateNonExistingBanner() throws Exception {
        int databaseSizeBeforeUpdate = bannerRepository.findAll().size();

        // Create the Banner

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBannerMockMvc.perform(put("/api/banners")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(banner)))
            .andExpect(status().isCreated());

        // Validate the Banner in the database
        List<Banner> bannerList = bannerRepository.findAll();
        assertThat(bannerList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBanner() throws Exception {
        // Initialize the database
        bannerService.save(banner);

        int databaseSizeBeforeDelete = bannerRepository.findAll().size();

        // Get the banner
        restBannerMockMvc.perform(delete("/api/banners/{id}", banner.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean bannerExistsInEs = bannerSearchRepository.exists(banner.getId());
        assertThat(bannerExistsInEs).isFalse();

        // Validate the database is empty
        List<Banner> bannerList = bannerRepository.findAll();
        assertThat(bannerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchBanner() throws Exception {
        // Initialize the database
        bannerService.save(banner);

        // Search the banner
        restBannerMockMvc.perform(get("/api/_search/banners?query=id:" + banner.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(banner.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Banner.class);
        Banner banner1 = new Banner();
        banner1.setId(1L);
        Banner banner2 = new Banner();
        banner2.setId(banner1.getId());
        assertThat(banner1).isEqualTo(banner2);
        banner2.setId(2L);
        assertThat(banner1).isNotEqualTo(banner2);
        banner1.setId(null);
        assertThat(banner1).isNotEqualTo(banner2);
    }
}
