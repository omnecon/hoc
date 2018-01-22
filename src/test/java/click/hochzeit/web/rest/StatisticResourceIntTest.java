package click.hochzeit.web.rest;

import click.hochzeit.Hochzeitclick11App;

import click.hochzeit.domain.Statistic;
import click.hochzeit.domain.Profile;
import click.hochzeit.repository.StatisticRepository;
import click.hochzeit.service.StatisticService;
import click.hochzeit.repository.search.StatisticSearchRepository;
import click.hochzeit.web.rest.errors.ExceptionTranslator;
import click.hochzeit.service.dto.StatisticCriteria;
import click.hochzeit.service.StatisticQueryService;

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
 * Test class for the StatisticResource REST controller.
 *
 * @see StatisticResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Hochzeitclick11App.class)
public class StatisticResourceIntTest {

    private static final Integer DEFAULT_NO_OF_FAG = 1;
    private static final Integer UPDATED_NO_OF_FAG = 2;

    private static final Integer DEFAULT_NO_OF_GALLERIES = 1;
    private static final Integer UPDATED_NO_OF_GALLERIES = 2;

    private static final Integer DEFAULT_NO_OF_INQUIRIES = 1;
    private static final Integer UPDATED_NO_OF_INQUIRIES = 2;

    private static final Integer DEFAULT_NO_OF_LINKED_PROVIDER = 1;
    private static final Integer UPDATED_NO_OF_LINKED_PROVIDER = 2;

    private static final Integer DEFAULT_NO_OF_PORTFOLIO_IMG = 1;
    private static final Integer UPDATED_NO_OF_PORTFOLIO_IMG = 2;

    private static final Integer DEFAULT_VIEWS_TOTAL_WP = 1;
    private static final Integer UPDATED_VIEWS_TOTAL_WP = 2;

    @Autowired
    private StatisticRepository statisticRepository;

    @Autowired
    private StatisticService statisticService;

    @Autowired
    private StatisticSearchRepository statisticSearchRepository;

    @Autowired
    private StatisticQueryService statisticQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restStatisticMockMvc;

    private Statistic statistic;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StatisticResource statisticResource = new StatisticResource(statisticService, statisticQueryService);
        this.restStatisticMockMvc = MockMvcBuilders.standaloneSetup(statisticResource)
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
    public static Statistic createEntity(EntityManager em) {
        Statistic statistic = new Statistic()
            .noOfFag(DEFAULT_NO_OF_FAG)
            .noOfGalleries(DEFAULT_NO_OF_GALLERIES)
            .noOfInquiries(DEFAULT_NO_OF_INQUIRIES)
            .noOfLinkedProvider(DEFAULT_NO_OF_LINKED_PROVIDER)
            .noOfPortfolioImg(DEFAULT_NO_OF_PORTFOLIO_IMG)
            .viewsTotalWP(DEFAULT_VIEWS_TOTAL_WP);
        return statistic;
    }

    @Before
    public void initTest() {
        statisticSearchRepository.deleteAll();
        statistic = createEntity(em);
    }

    @Test
    @Transactional
    public void createStatistic() throws Exception {
        int databaseSizeBeforeCreate = statisticRepository.findAll().size();

        // Create the Statistic
        restStatisticMockMvc.perform(post("/api/statistics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(statistic)))
            .andExpect(status().isCreated());

        // Validate the Statistic in the database
        List<Statistic> statisticList = statisticRepository.findAll();
        assertThat(statisticList).hasSize(databaseSizeBeforeCreate + 1);
        Statistic testStatistic = statisticList.get(statisticList.size() - 1);
        assertThat(testStatistic.getNoOfFag()).isEqualTo(DEFAULT_NO_OF_FAG);
        assertThat(testStatistic.getNoOfGalleries()).isEqualTo(DEFAULT_NO_OF_GALLERIES);
        assertThat(testStatistic.getNoOfInquiries()).isEqualTo(DEFAULT_NO_OF_INQUIRIES);
        assertThat(testStatistic.getNoOfLinkedProvider()).isEqualTo(DEFAULT_NO_OF_LINKED_PROVIDER);
        assertThat(testStatistic.getNoOfPortfolioImg()).isEqualTo(DEFAULT_NO_OF_PORTFOLIO_IMG);
        assertThat(testStatistic.getViewsTotalWP()).isEqualTo(DEFAULT_VIEWS_TOTAL_WP);

        // Validate the Statistic in Elasticsearch
        Statistic statisticEs = statisticSearchRepository.findOne(testStatistic.getId());
        assertThat(statisticEs).isEqualToIgnoringGivenFields(testStatistic);
    }

    @Test
    @Transactional
    public void createStatisticWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = statisticRepository.findAll().size();

        // Create the Statistic with an existing ID
        statistic.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStatisticMockMvc.perform(post("/api/statistics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(statistic)))
            .andExpect(status().isBadRequest());

        // Validate the Statistic in the database
        List<Statistic> statisticList = statisticRepository.findAll();
        assertThat(statisticList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllStatistics() throws Exception {
        // Initialize the database
        statisticRepository.saveAndFlush(statistic);

        // Get all the statisticList
        restStatisticMockMvc.perform(get("/api/statistics?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(statistic.getId().intValue())))
            .andExpect(jsonPath("$.[*].noOfFag").value(hasItem(DEFAULT_NO_OF_FAG)))
            .andExpect(jsonPath("$.[*].noOfGalleries").value(hasItem(DEFAULT_NO_OF_GALLERIES)))
            .andExpect(jsonPath("$.[*].noOfInquiries").value(hasItem(DEFAULT_NO_OF_INQUIRIES)))
            .andExpect(jsonPath("$.[*].noOfLinkedProvider").value(hasItem(DEFAULT_NO_OF_LINKED_PROVIDER)))
            .andExpect(jsonPath("$.[*].noOfPortfolioImg").value(hasItem(DEFAULT_NO_OF_PORTFOLIO_IMG)))
            .andExpect(jsonPath("$.[*].viewsTotalWP").value(hasItem(DEFAULT_VIEWS_TOTAL_WP)));
    }

    @Test
    @Transactional
    public void getStatistic() throws Exception {
        // Initialize the database
        statisticRepository.saveAndFlush(statistic);

        // Get the statistic
        restStatisticMockMvc.perform(get("/api/statistics/{id}", statistic.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(statistic.getId().intValue()))
            .andExpect(jsonPath("$.noOfFag").value(DEFAULT_NO_OF_FAG))
            .andExpect(jsonPath("$.noOfGalleries").value(DEFAULT_NO_OF_GALLERIES))
            .andExpect(jsonPath("$.noOfInquiries").value(DEFAULT_NO_OF_INQUIRIES))
            .andExpect(jsonPath("$.noOfLinkedProvider").value(DEFAULT_NO_OF_LINKED_PROVIDER))
            .andExpect(jsonPath("$.noOfPortfolioImg").value(DEFAULT_NO_OF_PORTFOLIO_IMG))
            .andExpect(jsonPath("$.viewsTotalWP").value(DEFAULT_VIEWS_TOTAL_WP));
    }

    @Test
    @Transactional
    public void getAllStatisticsByNoOfFagIsEqualToSomething() throws Exception {
        // Initialize the database
        statisticRepository.saveAndFlush(statistic);

        // Get all the statisticList where noOfFag equals to DEFAULT_NO_OF_FAG
        defaultStatisticShouldBeFound("noOfFag.equals=" + DEFAULT_NO_OF_FAG);

        // Get all the statisticList where noOfFag equals to UPDATED_NO_OF_FAG
        defaultStatisticShouldNotBeFound("noOfFag.equals=" + UPDATED_NO_OF_FAG);
    }

    @Test
    @Transactional
    public void getAllStatisticsByNoOfFagIsInShouldWork() throws Exception {
        // Initialize the database
        statisticRepository.saveAndFlush(statistic);

        // Get all the statisticList where noOfFag in DEFAULT_NO_OF_FAG or UPDATED_NO_OF_FAG
        defaultStatisticShouldBeFound("noOfFag.in=" + DEFAULT_NO_OF_FAG + "," + UPDATED_NO_OF_FAG);

        // Get all the statisticList where noOfFag equals to UPDATED_NO_OF_FAG
        defaultStatisticShouldNotBeFound("noOfFag.in=" + UPDATED_NO_OF_FAG);
    }

    @Test
    @Transactional
    public void getAllStatisticsByNoOfFagIsNullOrNotNull() throws Exception {
        // Initialize the database
        statisticRepository.saveAndFlush(statistic);

        // Get all the statisticList where noOfFag is not null
        defaultStatisticShouldBeFound("noOfFag.specified=true");

        // Get all the statisticList where noOfFag is null
        defaultStatisticShouldNotBeFound("noOfFag.specified=false");
    }

    @Test
    @Transactional
    public void getAllStatisticsByNoOfFagIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        statisticRepository.saveAndFlush(statistic);

        // Get all the statisticList where noOfFag greater than or equals to DEFAULT_NO_OF_FAG
        defaultStatisticShouldBeFound("noOfFag.greaterOrEqualThan=" + DEFAULT_NO_OF_FAG);

        // Get all the statisticList where noOfFag greater than or equals to UPDATED_NO_OF_FAG
        defaultStatisticShouldNotBeFound("noOfFag.greaterOrEqualThan=" + UPDATED_NO_OF_FAG);
    }

    @Test
    @Transactional
    public void getAllStatisticsByNoOfFagIsLessThanSomething() throws Exception {
        // Initialize the database
        statisticRepository.saveAndFlush(statistic);

        // Get all the statisticList where noOfFag less than or equals to DEFAULT_NO_OF_FAG
        defaultStatisticShouldNotBeFound("noOfFag.lessThan=" + DEFAULT_NO_OF_FAG);

        // Get all the statisticList where noOfFag less than or equals to UPDATED_NO_OF_FAG
        defaultStatisticShouldBeFound("noOfFag.lessThan=" + UPDATED_NO_OF_FAG);
    }


    @Test
    @Transactional
    public void getAllStatisticsByNoOfGalleriesIsEqualToSomething() throws Exception {
        // Initialize the database
        statisticRepository.saveAndFlush(statistic);

        // Get all the statisticList where noOfGalleries equals to DEFAULT_NO_OF_GALLERIES
        defaultStatisticShouldBeFound("noOfGalleries.equals=" + DEFAULT_NO_OF_GALLERIES);

        // Get all the statisticList where noOfGalleries equals to UPDATED_NO_OF_GALLERIES
        defaultStatisticShouldNotBeFound("noOfGalleries.equals=" + UPDATED_NO_OF_GALLERIES);
    }

    @Test
    @Transactional
    public void getAllStatisticsByNoOfGalleriesIsInShouldWork() throws Exception {
        // Initialize the database
        statisticRepository.saveAndFlush(statistic);

        // Get all the statisticList where noOfGalleries in DEFAULT_NO_OF_GALLERIES or UPDATED_NO_OF_GALLERIES
        defaultStatisticShouldBeFound("noOfGalleries.in=" + DEFAULT_NO_OF_GALLERIES + "," + UPDATED_NO_OF_GALLERIES);

        // Get all the statisticList where noOfGalleries equals to UPDATED_NO_OF_GALLERIES
        defaultStatisticShouldNotBeFound("noOfGalleries.in=" + UPDATED_NO_OF_GALLERIES);
    }

    @Test
    @Transactional
    public void getAllStatisticsByNoOfGalleriesIsNullOrNotNull() throws Exception {
        // Initialize the database
        statisticRepository.saveAndFlush(statistic);

        // Get all the statisticList where noOfGalleries is not null
        defaultStatisticShouldBeFound("noOfGalleries.specified=true");

        // Get all the statisticList where noOfGalleries is null
        defaultStatisticShouldNotBeFound("noOfGalleries.specified=false");
    }

    @Test
    @Transactional
    public void getAllStatisticsByNoOfGalleriesIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        statisticRepository.saveAndFlush(statistic);

        // Get all the statisticList where noOfGalleries greater than or equals to DEFAULT_NO_OF_GALLERIES
        defaultStatisticShouldBeFound("noOfGalleries.greaterOrEqualThan=" + DEFAULT_NO_OF_GALLERIES);

        // Get all the statisticList where noOfGalleries greater than or equals to UPDATED_NO_OF_GALLERIES
        defaultStatisticShouldNotBeFound("noOfGalleries.greaterOrEqualThan=" + UPDATED_NO_OF_GALLERIES);
    }

    @Test
    @Transactional
    public void getAllStatisticsByNoOfGalleriesIsLessThanSomething() throws Exception {
        // Initialize the database
        statisticRepository.saveAndFlush(statistic);

        // Get all the statisticList where noOfGalleries less than or equals to DEFAULT_NO_OF_GALLERIES
        defaultStatisticShouldNotBeFound("noOfGalleries.lessThan=" + DEFAULT_NO_OF_GALLERIES);

        // Get all the statisticList where noOfGalleries less than or equals to UPDATED_NO_OF_GALLERIES
        defaultStatisticShouldBeFound("noOfGalleries.lessThan=" + UPDATED_NO_OF_GALLERIES);
    }


    @Test
    @Transactional
    public void getAllStatisticsByNoOfInquiriesIsEqualToSomething() throws Exception {
        // Initialize the database
        statisticRepository.saveAndFlush(statistic);

        // Get all the statisticList where noOfInquiries equals to DEFAULT_NO_OF_INQUIRIES
        defaultStatisticShouldBeFound("noOfInquiries.equals=" + DEFAULT_NO_OF_INQUIRIES);

        // Get all the statisticList where noOfInquiries equals to UPDATED_NO_OF_INQUIRIES
        defaultStatisticShouldNotBeFound("noOfInquiries.equals=" + UPDATED_NO_OF_INQUIRIES);
    }

    @Test
    @Transactional
    public void getAllStatisticsByNoOfInquiriesIsInShouldWork() throws Exception {
        // Initialize the database
        statisticRepository.saveAndFlush(statistic);

        // Get all the statisticList where noOfInquiries in DEFAULT_NO_OF_INQUIRIES or UPDATED_NO_OF_INQUIRIES
        defaultStatisticShouldBeFound("noOfInquiries.in=" + DEFAULT_NO_OF_INQUIRIES + "," + UPDATED_NO_OF_INQUIRIES);

        // Get all the statisticList where noOfInquiries equals to UPDATED_NO_OF_INQUIRIES
        defaultStatisticShouldNotBeFound("noOfInquiries.in=" + UPDATED_NO_OF_INQUIRIES);
    }

    @Test
    @Transactional
    public void getAllStatisticsByNoOfInquiriesIsNullOrNotNull() throws Exception {
        // Initialize the database
        statisticRepository.saveAndFlush(statistic);

        // Get all the statisticList where noOfInquiries is not null
        defaultStatisticShouldBeFound("noOfInquiries.specified=true");

        // Get all the statisticList where noOfInquiries is null
        defaultStatisticShouldNotBeFound("noOfInquiries.specified=false");
    }

    @Test
    @Transactional
    public void getAllStatisticsByNoOfInquiriesIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        statisticRepository.saveAndFlush(statistic);

        // Get all the statisticList where noOfInquiries greater than or equals to DEFAULT_NO_OF_INQUIRIES
        defaultStatisticShouldBeFound("noOfInquiries.greaterOrEqualThan=" + DEFAULT_NO_OF_INQUIRIES);

        // Get all the statisticList where noOfInquiries greater than or equals to UPDATED_NO_OF_INQUIRIES
        defaultStatisticShouldNotBeFound("noOfInquiries.greaterOrEqualThan=" + UPDATED_NO_OF_INQUIRIES);
    }

    @Test
    @Transactional
    public void getAllStatisticsByNoOfInquiriesIsLessThanSomething() throws Exception {
        // Initialize the database
        statisticRepository.saveAndFlush(statistic);

        // Get all the statisticList where noOfInquiries less than or equals to DEFAULT_NO_OF_INQUIRIES
        defaultStatisticShouldNotBeFound("noOfInquiries.lessThan=" + DEFAULT_NO_OF_INQUIRIES);

        // Get all the statisticList where noOfInquiries less than or equals to UPDATED_NO_OF_INQUIRIES
        defaultStatisticShouldBeFound("noOfInquiries.lessThan=" + UPDATED_NO_OF_INQUIRIES);
    }


    @Test
    @Transactional
    public void getAllStatisticsByNoOfLinkedProviderIsEqualToSomething() throws Exception {
        // Initialize the database
        statisticRepository.saveAndFlush(statistic);

        // Get all the statisticList where noOfLinkedProvider equals to DEFAULT_NO_OF_LINKED_PROVIDER
        defaultStatisticShouldBeFound("noOfLinkedProvider.equals=" + DEFAULT_NO_OF_LINKED_PROVIDER);

        // Get all the statisticList where noOfLinkedProvider equals to UPDATED_NO_OF_LINKED_PROVIDER
        defaultStatisticShouldNotBeFound("noOfLinkedProvider.equals=" + UPDATED_NO_OF_LINKED_PROVIDER);
    }

    @Test
    @Transactional
    public void getAllStatisticsByNoOfLinkedProviderIsInShouldWork() throws Exception {
        // Initialize the database
        statisticRepository.saveAndFlush(statistic);

        // Get all the statisticList where noOfLinkedProvider in DEFAULT_NO_OF_LINKED_PROVIDER or UPDATED_NO_OF_LINKED_PROVIDER
        defaultStatisticShouldBeFound("noOfLinkedProvider.in=" + DEFAULT_NO_OF_LINKED_PROVIDER + "," + UPDATED_NO_OF_LINKED_PROVIDER);

        // Get all the statisticList where noOfLinkedProvider equals to UPDATED_NO_OF_LINKED_PROVIDER
        defaultStatisticShouldNotBeFound("noOfLinkedProvider.in=" + UPDATED_NO_OF_LINKED_PROVIDER);
    }

    @Test
    @Transactional
    public void getAllStatisticsByNoOfLinkedProviderIsNullOrNotNull() throws Exception {
        // Initialize the database
        statisticRepository.saveAndFlush(statistic);

        // Get all the statisticList where noOfLinkedProvider is not null
        defaultStatisticShouldBeFound("noOfLinkedProvider.specified=true");

        // Get all the statisticList where noOfLinkedProvider is null
        defaultStatisticShouldNotBeFound("noOfLinkedProvider.specified=false");
    }

    @Test
    @Transactional
    public void getAllStatisticsByNoOfLinkedProviderIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        statisticRepository.saveAndFlush(statistic);

        // Get all the statisticList where noOfLinkedProvider greater than or equals to DEFAULT_NO_OF_LINKED_PROVIDER
        defaultStatisticShouldBeFound("noOfLinkedProvider.greaterOrEqualThan=" + DEFAULT_NO_OF_LINKED_PROVIDER);

        // Get all the statisticList where noOfLinkedProvider greater than or equals to UPDATED_NO_OF_LINKED_PROVIDER
        defaultStatisticShouldNotBeFound("noOfLinkedProvider.greaterOrEqualThan=" + UPDATED_NO_OF_LINKED_PROVIDER);
    }

    @Test
    @Transactional
    public void getAllStatisticsByNoOfLinkedProviderIsLessThanSomething() throws Exception {
        // Initialize the database
        statisticRepository.saveAndFlush(statistic);

        // Get all the statisticList where noOfLinkedProvider less than or equals to DEFAULT_NO_OF_LINKED_PROVIDER
        defaultStatisticShouldNotBeFound("noOfLinkedProvider.lessThan=" + DEFAULT_NO_OF_LINKED_PROVIDER);

        // Get all the statisticList where noOfLinkedProvider less than or equals to UPDATED_NO_OF_LINKED_PROVIDER
        defaultStatisticShouldBeFound("noOfLinkedProvider.lessThan=" + UPDATED_NO_OF_LINKED_PROVIDER);
    }


    @Test
    @Transactional
    public void getAllStatisticsByNoOfPortfolioImgIsEqualToSomething() throws Exception {
        // Initialize the database
        statisticRepository.saveAndFlush(statistic);

        // Get all the statisticList where noOfPortfolioImg equals to DEFAULT_NO_OF_PORTFOLIO_IMG
        defaultStatisticShouldBeFound("noOfPortfolioImg.equals=" + DEFAULT_NO_OF_PORTFOLIO_IMG);

        // Get all the statisticList where noOfPortfolioImg equals to UPDATED_NO_OF_PORTFOLIO_IMG
        defaultStatisticShouldNotBeFound("noOfPortfolioImg.equals=" + UPDATED_NO_OF_PORTFOLIO_IMG);
    }

    @Test
    @Transactional
    public void getAllStatisticsByNoOfPortfolioImgIsInShouldWork() throws Exception {
        // Initialize the database
        statisticRepository.saveAndFlush(statistic);

        // Get all the statisticList where noOfPortfolioImg in DEFAULT_NO_OF_PORTFOLIO_IMG or UPDATED_NO_OF_PORTFOLIO_IMG
        defaultStatisticShouldBeFound("noOfPortfolioImg.in=" + DEFAULT_NO_OF_PORTFOLIO_IMG + "," + UPDATED_NO_OF_PORTFOLIO_IMG);

        // Get all the statisticList where noOfPortfolioImg equals to UPDATED_NO_OF_PORTFOLIO_IMG
        defaultStatisticShouldNotBeFound("noOfPortfolioImg.in=" + UPDATED_NO_OF_PORTFOLIO_IMG);
    }

    @Test
    @Transactional
    public void getAllStatisticsByNoOfPortfolioImgIsNullOrNotNull() throws Exception {
        // Initialize the database
        statisticRepository.saveAndFlush(statistic);

        // Get all the statisticList where noOfPortfolioImg is not null
        defaultStatisticShouldBeFound("noOfPortfolioImg.specified=true");

        // Get all the statisticList where noOfPortfolioImg is null
        defaultStatisticShouldNotBeFound("noOfPortfolioImg.specified=false");
    }

    @Test
    @Transactional
    public void getAllStatisticsByNoOfPortfolioImgIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        statisticRepository.saveAndFlush(statistic);

        // Get all the statisticList where noOfPortfolioImg greater than or equals to DEFAULT_NO_OF_PORTFOLIO_IMG
        defaultStatisticShouldBeFound("noOfPortfolioImg.greaterOrEqualThan=" + DEFAULT_NO_OF_PORTFOLIO_IMG);

        // Get all the statisticList where noOfPortfolioImg greater than or equals to UPDATED_NO_OF_PORTFOLIO_IMG
        defaultStatisticShouldNotBeFound("noOfPortfolioImg.greaterOrEqualThan=" + UPDATED_NO_OF_PORTFOLIO_IMG);
    }

    @Test
    @Transactional
    public void getAllStatisticsByNoOfPortfolioImgIsLessThanSomething() throws Exception {
        // Initialize the database
        statisticRepository.saveAndFlush(statistic);

        // Get all the statisticList where noOfPortfolioImg less than or equals to DEFAULT_NO_OF_PORTFOLIO_IMG
        defaultStatisticShouldNotBeFound("noOfPortfolioImg.lessThan=" + DEFAULT_NO_OF_PORTFOLIO_IMG);

        // Get all the statisticList where noOfPortfolioImg less than or equals to UPDATED_NO_OF_PORTFOLIO_IMG
        defaultStatisticShouldBeFound("noOfPortfolioImg.lessThan=" + UPDATED_NO_OF_PORTFOLIO_IMG);
    }


    @Test
    @Transactional
    public void getAllStatisticsByViewsTotalWPIsEqualToSomething() throws Exception {
        // Initialize the database
        statisticRepository.saveAndFlush(statistic);

        // Get all the statisticList where viewsTotalWP equals to DEFAULT_VIEWS_TOTAL_WP
        defaultStatisticShouldBeFound("viewsTotalWP.equals=" + DEFAULT_VIEWS_TOTAL_WP);

        // Get all the statisticList where viewsTotalWP equals to UPDATED_VIEWS_TOTAL_WP
        defaultStatisticShouldNotBeFound("viewsTotalWP.equals=" + UPDATED_VIEWS_TOTAL_WP);
    }

    @Test
    @Transactional
    public void getAllStatisticsByViewsTotalWPIsInShouldWork() throws Exception {
        // Initialize the database
        statisticRepository.saveAndFlush(statistic);

        // Get all the statisticList where viewsTotalWP in DEFAULT_VIEWS_TOTAL_WP or UPDATED_VIEWS_TOTAL_WP
        defaultStatisticShouldBeFound("viewsTotalWP.in=" + DEFAULT_VIEWS_TOTAL_WP + "," + UPDATED_VIEWS_TOTAL_WP);

        // Get all the statisticList where viewsTotalWP equals to UPDATED_VIEWS_TOTAL_WP
        defaultStatisticShouldNotBeFound("viewsTotalWP.in=" + UPDATED_VIEWS_TOTAL_WP);
    }

    @Test
    @Transactional
    public void getAllStatisticsByViewsTotalWPIsNullOrNotNull() throws Exception {
        // Initialize the database
        statisticRepository.saveAndFlush(statistic);

        // Get all the statisticList where viewsTotalWP is not null
        defaultStatisticShouldBeFound("viewsTotalWP.specified=true");

        // Get all the statisticList where viewsTotalWP is null
        defaultStatisticShouldNotBeFound("viewsTotalWP.specified=false");
    }

    @Test
    @Transactional
    public void getAllStatisticsByViewsTotalWPIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        statisticRepository.saveAndFlush(statistic);

        // Get all the statisticList where viewsTotalWP greater than or equals to DEFAULT_VIEWS_TOTAL_WP
        defaultStatisticShouldBeFound("viewsTotalWP.greaterOrEqualThan=" + DEFAULT_VIEWS_TOTAL_WP);

        // Get all the statisticList where viewsTotalWP greater than or equals to UPDATED_VIEWS_TOTAL_WP
        defaultStatisticShouldNotBeFound("viewsTotalWP.greaterOrEqualThan=" + UPDATED_VIEWS_TOTAL_WP);
    }

    @Test
    @Transactional
    public void getAllStatisticsByViewsTotalWPIsLessThanSomething() throws Exception {
        // Initialize the database
        statisticRepository.saveAndFlush(statistic);

        // Get all the statisticList where viewsTotalWP less than or equals to DEFAULT_VIEWS_TOTAL_WP
        defaultStatisticShouldNotBeFound("viewsTotalWP.lessThan=" + DEFAULT_VIEWS_TOTAL_WP);

        // Get all the statisticList where viewsTotalWP less than or equals to UPDATED_VIEWS_TOTAL_WP
        defaultStatisticShouldBeFound("viewsTotalWP.lessThan=" + UPDATED_VIEWS_TOTAL_WP);
    }


    @Test
    @Transactional
    public void getAllStatisticsByProfileIsEqualToSomething() throws Exception {
        // Initialize the database
        Profile profile = ProfileResourceIntTest.createEntity(em);
        em.persist(profile);
        em.flush();
        statistic.setProfile(profile);
        statisticRepository.saveAndFlush(statistic);
        Long profileId = profile.getId();

        // Get all the statisticList where profile equals to profileId
        defaultStatisticShouldBeFound("profileId.equals=" + profileId);

        // Get all the statisticList where profile equals to profileId + 1
        defaultStatisticShouldNotBeFound("profileId.equals=" + (profileId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultStatisticShouldBeFound(String filter) throws Exception {
        restStatisticMockMvc.perform(get("/api/statistics?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(statistic.getId().intValue())))
            .andExpect(jsonPath("$.[*].noOfFag").value(hasItem(DEFAULT_NO_OF_FAG)))
            .andExpect(jsonPath("$.[*].noOfGalleries").value(hasItem(DEFAULT_NO_OF_GALLERIES)))
            .andExpect(jsonPath("$.[*].noOfInquiries").value(hasItem(DEFAULT_NO_OF_INQUIRIES)))
            .andExpect(jsonPath("$.[*].noOfLinkedProvider").value(hasItem(DEFAULT_NO_OF_LINKED_PROVIDER)))
            .andExpect(jsonPath("$.[*].noOfPortfolioImg").value(hasItem(DEFAULT_NO_OF_PORTFOLIO_IMG)))
            .andExpect(jsonPath("$.[*].viewsTotalWP").value(hasItem(DEFAULT_VIEWS_TOTAL_WP)));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultStatisticShouldNotBeFound(String filter) throws Exception {
        restStatisticMockMvc.perform(get("/api/statistics?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingStatistic() throws Exception {
        // Get the statistic
        restStatisticMockMvc.perform(get("/api/statistics/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStatistic() throws Exception {
        // Initialize the database
        statisticService.save(statistic);

        int databaseSizeBeforeUpdate = statisticRepository.findAll().size();

        // Update the statistic
        Statistic updatedStatistic = statisticRepository.findOne(statistic.getId());
        // Disconnect from session so that the updates on updatedStatistic are not directly saved in db
        em.detach(updatedStatistic);
        updatedStatistic
            .noOfFag(UPDATED_NO_OF_FAG)
            .noOfGalleries(UPDATED_NO_OF_GALLERIES)
            .noOfInquiries(UPDATED_NO_OF_INQUIRIES)
            .noOfLinkedProvider(UPDATED_NO_OF_LINKED_PROVIDER)
            .noOfPortfolioImg(UPDATED_NO_OF_PORTFOLIO_IMG)
            .viewsTotalWP(UPDATED_VIEWS_TOTAL_WP);

        restStatisticMockMvc.perform(put("/api/statistics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedStatistic)))
            .andExpect(status().isOk());

        // Validate the Statistic in the database
        List<Statistic> statisticList = statisticRepository.findAll();
        assertThat(statisticList).hasSize(databaseSizeBeforeUpdate);
        Statistic testStatistic = statisticList.get(statisticList.size() - 1);
        assertThat(testStatistic.getNoOfFag()).isEqualTo(UPDATED_NO_OF_FAG);
        assertThat(testStatistic.getNoOfGalleries()).isEqualTo(UPDATED_NO_OF_GALLERIES);
        assertThat(testStatistic.getNoOfInquiries()).isEqualTo(UPDATED_NO_OF_INQUIRIES);
        assertThat(testStatistic.getNoOfLinkedProvider()).isEqualTo(UPDATED_NO_OF_LINKED_PROVIDER);
        assertThat(testStatistic.getNoOfPortfolioImg()).isEqualTo(UPDATED_NO_OF_PORTFOLIO_IMG);
        assertThat(testStatistic.getViewsTotalWP()).isEqualTo(UPDATED_VIEWS_TOTAL_WP);

        // Validate the Statistic in Elasticsearch
        Statistic statisticEs = statisticSearchRepository.findOne(testStatistic.getId());
        assertThat(statisticEs).isEqualToIgnoringGivenFields(testStatistic);
    }

    @Test
    @Transactional
    public void updateNonExistingStatistic() throws Exception {
        int databaseSizeBeforeUpdate = statisticRepository.findAll().size();

        // Create the Statistic

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restStatisticMockMvc.perform(put("/api/statistics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(statistic)))
            .andExpect(status().isCreated());

        // Validate the Statistic in the database
        List<Statistic> statisticList = statisticRepository.findAll();
        assertThat(statisticList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteStatistic() throws Exception {
        // Initialize the database
        statisticService.save(statistic);

        int databaseSizeBeforeDelete = statisticRepository.findAll().size();

        // Get the statistic
        restStatisticMockMvc.perform(delete("/api/statistics/{id}", statistic.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean statisticExistsInEs = statisticSearchRepository.exists(statistic.getId());
        assertThat(statisticExistsInEs).isFalse();

        // Validate the database is empty
        List<Statistic> statisticList = statisticRepository.findAll();
        assertThat(statisticList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchStatistic() throws Exception {
        // Initialize the database
        statisticService.save(statistic);

        // Search the statistic
        restStatisticMockMvc.perform(get("/api/_search/statistics?query=id:" + statistic.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(statistic.getId().intValue())))
            .andExpect(jsonPath("$.[*].noOfFag").value(hasItem(DEFAULT_NO_OF_FAG)))
            .andExpect(jsonPath("$.[*].noOfGalleries").value(hasItem(DEFAULT_NO_OF_GALLERIES)))
            .andExpect(jsonPath("$.[*].noOfInquiries").value(hasItem(DEFAULT_NO_OF_INQUIRIES)))
            .andExpect(jsonPath("$.[*].noOfLinkedProvider").value(hasItem(DEFAULT_NO_OF_LINKED_PROVIDER)))
            .andExpect(jsonPath("$.[*].noOfPortfolioImg").value(hasItem(DEFAULT_NO_OF_PORTFOLIO_IMG)))
            .andExpect(jsonPath("$.[*].viewsTotalWP").value(hasItem(DEFAULT_VIEWS_TOTAL_WP)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Statistic.class);
        Statistic statistic1 = new Statistic();
        statistic1.setId(1L);
        Statistic statistic2 = new Statistic();
        statistic2.setId(statistic1.getId());
        assertThat(statistic1).isEqualTo(statistic2);
        statistic2.setId(2L);
        assertThat(statistic1).isNotEqualTo(statistic2);
        statistic1.setId(null);
        assertThat(statistic1).isNotEqualTo(statistic2);
    }
}
