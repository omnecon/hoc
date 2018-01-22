package click.hochzeit.web.rest;

import click.hochzeit.Hochzeitclick11App;

import click.hochzeit.domain.Inquiry;
import click.hochzeit.repository.InquiryRepository;
import click.hochzeit.service.InquiryService;
import click.hochzeit.repository.search.InquirySearchRepository;
import click.hochzeit.web.rest.errors.ExceptionTranslator;
import click.hochzeit.service.dto.InquiryCriteria;
import click.hochzeit.service.InquiryQueryService;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static click.hochzeit.web.rest.TestUtil.sameInstant;
import static click.hochzeit.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the InquiryResource REST controller.
 *
 * @see InquiryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Hochzeitclick11App.class)
public class InquiryResourceIntTest {

    private static final ZonedDateTime DEFAULT_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_REGION = "AAAAAAAAAA";
    private static final String UPDATED_REGION = "BBBBBBBBBB";

    private static final String DEFAULT_PROVICE = "AAAAAAAAAA";
    private static final String UPDATED_PROVICE = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_POSTAL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_POSTAL_CODE = "BBBBBBBBBB";

    @Autowired
    private InquiryRepository inquiryRepository;

    @Autowired
    private InquiryService inquiryService;

    @Autowired
    private InquirySearchRepository inquirySearchRepository;

    @Autowired
    private InquiryQueryService inquiryQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restInquiryMockMvc;

    private Inquiry inquiry;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InquiryResource inquiryResource = new InquiryResource(inquiryService, inquiryQueryService);
        this.restInquiryMockMvc = MockMvcBuilders.standaloneSetup(inquiryResource)
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
    public static Inquiry createEntity(EntityManager em) {
        Inquiry inquiry = new Inquiry()
            .createdDate(DEFAULT_CREATED_DATE)
            .email(DEFAULT_EMAIL)
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .country(DEFAULT_COUNTRY)
            .region(DEFAULT_REGION)
            .provice(DEFAULT_PROVICE)
            .city(DEFAULT_CITY)
            .postalCode(DEFAULT_POSTAL_CODE);
        return inquiry;
    }

    @Before
    public void initTest() {
        inquirySearchRepository.deleteAll();
        inquiry = createEntity(em);
    }

    @Test
    @Transactional
    public void createInquiry() throws Exception {
        int databaseSizeBeforeCreate = inquiryRepository.findAll().size();

        // Create the Inquiry
        restInquiryMockMvc.perform(post("/api/inquiries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inquiry)))
            .andExpect(status().isCreated());

        // Validate the Inquiry in the database
        List<Inquiry> inquiryList = inquiryRepository.findAll();
        assertThat(inquiryList).hasSize(databaseSizeBeforeCreate + 1);
        Inquiry testInquiry = inquiryList.get(inquiryList.size() - 1);
        assertThat(testInquiry.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testInquiry.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testInquiry.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testInquiry.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testInquiry.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testInquiry.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testInquiry.getRegion()).isEqualTo(DEFAULT_REGION);
        assertThat(testInquiry.getProvice()).isEqualTo(DEFAULT_PROVICE);
        assertThat(testInquiry.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testInquiry.getPostalCode()).isEqualTo(DEFAULT_POSTAL_CODE);

        // Validate the Inquiry in Elasticsearch
        Inquiry inquiryEs = inquirySearchRepository.findOne(testInquiry.getId());
        assertThat(testInquiry.getCreatedDate()).isEqualTo(testInquiry.getCreatedDate());
        assertThat(inquiryEs).isEqualToIgnoringGivenFields(testInquiry, "createdDate");
    }

    @Test
    @Transactional
    public void createInquiryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = inquiryRepository.findAll().size();

        // Create the Inquiry with an existing ID
        inquiry.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInquiryMockMvc.perform(post("/api/inquiries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inquiry)))
            .andExpect(status().isBadRequest());

        // Validate the Inquiry in the database
        List<Inquiry> inquiryList = inquiryRepository.findAll();
        assertThat(inquiryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllInquiries() throws Exception {
        // Initialize the database
        inquiryRepository.saveAndFlush(inquiry);

        // Get all the inquiryList
        restInquiryMockMvc.perform(get("/api/inquiries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inquiry.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].region").value(hasItem(DEFAULT_REGION.toString())))
            .andExpect(jsonPath("$.[*].provice").value(hasItem(DEFAULT_PROVICE.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE.toString())));
    }

    @Test
    @Transactional
    public void getInquiry() throws Exception {
        // Initialize the database
        inquiryRepository.saveAndFlush(inquiry);

        // Get the inquiry
        restInquiryMockMvc.perform(get("/api/inquiries/{id}", inquiry.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(inquiry.getId().intValue()))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER.toString()))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY.toString()))
            .andExpect(jsonPath("$.region").value(DEFAULT_REGION.toString()))
            .andExpect(jsonPath("$.provice").value(DEFAULT_PROVICE.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.postalCode").value(DEFAULT_POSTAL_CODE.toString()));
    }

    @Test
    @Transactional
    public void getAllInquiriesByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        inquiryRepository.saveAndFlush(inquiry);

        // Get all the inquiryList where createdDate equals to DEFAULT_CREATED_DATE
        defaultInquiryShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the inquiryList where createdDate equals to UPDATED_CREATED_DATE
        defaultInquiryShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllInquiriesByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        inquiryRepository.saveAndFlush(inquiry);

        // Get all the inquiryList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultInquiryShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the inquiryList where createdDate equals to UPDATED_CREATED_DATE
        defaultInquiryShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllInquiriesByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        inquiryRepository.saveAndFlush(inquiry);

        // Get all the inquiryList where createdDate is not null
        defaultInquiryShouldBeFound("createdDate.specified=true");

        // Get all the inquiryList where createdDate is null
        defaultInquiryShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllInquiriesByCreatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inquiryRepository.saveAndFlush(inquiry);

        // Get all the inquiryList where createdDate greater than or equals to DEFAULT_CREATED_DATE
        defaultInquiryShouldBeFound("createdDate.greaterOrEqualThan=" + DEFAULT_CREATED_DATE);

        // Get all the inquiryList where createdDate greater than or equals to UPDATED_CREATED_DATE
        defaultInquiryShouldNotBeFound("createdDate.greaterOrEqualThan=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllInquiriesByCreatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        inquiryRepository.saveAndFlush(inquiry);

        // Get all the inquiryList where createdDate less than or equals to DEFAULT_CREATED_DATE
        defaultInquiryShouldNotBeFound("createdDate.lessThan=" + DEFAULT_CREATED_DATE);

        // Get all the inquiryList where createdDate less than or equals to UPDATED_CREATED_DATE
        defaultInquiryShouldBeFound("createdDate.lessThan=" + UPDATED_CREATED_DATE);
    }


    @Test
    @Transactional
    public void getAllInquiriesByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        inquiryRepository.saveAndFlush(inquiry);

        // Get all the inquiryList where email equals to DEFAULT_EMAIL
        defaultInquiryShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the inquiryList where email equals to UPDATED_EMAIL
        defaultInquiryShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllInquiriesByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        inquiryRepository.saveAndFlush(inquiry);

        // Get all the inquiryList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultInquiryShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the inquiryList where email equals to UPDATED_EMAIL
        defaultInquiryShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllInquiriesByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        inquiryRepository.saveAndFlush(inquiry);

        // Get all the inquiryList where email is not null
        defaultInquiryShouldBeFound("email.specified=true");

        // Get all the inquiryList where email is null
        defaultInquiryShouldNotBeFound("email.specified=false");
    }

    @Test
    @Transactional
    public void getAllInquiriesByFirstNameIsEqualToSomething() throws Exception {
        // Initialize the database
        inquiryRepository.saveAndFlush(inquiry);

        // Get all the inquiryList where firstName equals to DEFAULT_FIRST_NAME
        defaultInquiryShouldBeFound("firstName.equals=" + DEFAULT_FIRST_NAME);

        // Get all the inquiryList where firstName equals to UPDATED_FIRST_NAME
        defaultInquiryShouldNotBeFound("firstName.equals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllInquiriesByFirstNameIsInShouldWork() throws Exception {
        // Initialize the database
        inquiryRepository.saveAndFlush(inquiry);

        // Get all the inquiryList where firstName in DEFAULT_FIRST_NAME or UPDATED_FIRST_NAME
        defaultInquiryShouldBeFound("firstName.in=" + DEFAULT_FIRST_NAME + "," + UPDATED_FIRST_NAME);

        // Get all the inquiryList where firstName equals to UPDATED_FIRST_NAME
        defaultInquiryShouldNotBeFound("firstName.in=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllInquiriesByFirstNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        inquiryRepository.saveAndFlush(inquiry);

        // Get all the inquiryList where firstName is not null
        defaultInquiryShouldBeFound("firstName.specified=true");

        // Get all the inquiryList where firstName is null
        defaultInquiryShouldNotBeFound("firstName.specified=false");
    }

    @Test
    @Transactional
    public void getAllInquiriesByLastNameIsEqualToSomething() throws Exception {
        // Initialize the database
        inquiryRepository.saveAndFlush(inquiry);

        // Get all the inquiryList where lastName equals to DEFAULT_LAST_NAME
        defaultInquiryShouldBeFound("lastName.equals=" + DEFAULT_LAST_NAME);

        // Get all the inquiryList where lastName equals to UPDATED_LAST_NAME
        defaultInquiryShouldNotBeFound("lastName.equals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllInquiriesByLastNameIsInShouldWork() throws Exception {
        // Initialize the database
        inquiryRepository.saveAndFlush(inquiry);

        // Get all the inquiryList where lastName in DEFAULT_LAST_NAME or UPDATED_LAST_NAME
        defaultInquiryShouldBeFound("lastName.in=" + DEFAULT_LAST_NAME + "," + UPDATED_LAST_NAME);

        // Get all the inquiryList where lastName equals to UPDATED_LAST_NAME
        defaultInquiryShouldNotBeFound("lastName.in=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllInquiriesByLastNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        inquiryRepository.saveAndFlush(inquiry);

        // Get all the inquiryList where lastName is not null
        defaultInquiryShouldBeFound("lastName.specified=true");

        // Get all the inquiryList where lastName is null
        defaultInquiryShouldNotBeFound("lastName.specified=false");
    }

    @Test
    @Transactional
    public void getAllInquiriesByPhoneNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        inquiryRepository.saveAndFlush(inquiry);

        // Get all the inquiryList where phoneNumber equals to DEFAULT_PHONE_NUMBER
        defaultInquiryShouldBeFound("phoneNumber.equals=" + DEFAULT_PHONE_NUMBER);

        // Get all the inquiryList where phoneNumber equals to UPDATED_PHONE_NUMBER
        defaultInquiryShouldNotBeFound("phoneNumber.equals=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllInquiriesByPhoneNumberIsInShouldWork() throws Exception {
        // Initialize the database
        inquiryRepository.saveAndFlush(inquiry);

        // Get all the inquiryList where phoneNumber in DEFAULT_PHONE_NUMBER or UPDATED_PHONE_NUMBER
        defaultInquiryShouldBeFound("phoneNumber.in=" + DEFAULT_PHONE_NUMBER + "," + UPDATED_PHONE_NUMBER);

        // Get all the inquiryList where phoneNumber equals to UPDATED_PHONE_NUMBER
        defaultInquiryShouldNotBeFound("phoneNumber.in=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllInquiriesByPhoneNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        inquiryRepository.saveAndFlush(inquiry);

        // Get all the inquiryList where phoneNumber is not null
        defaultInquiryShouldBeFound("phoneNumber.specified=true");

        // Get all the inquiryList where phoneNumber is null
        defaultInquiryShouldNotBeFound("phoneNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllInquiriesByCountryIsEqualToSomething() throws Exception {
        // Initialize the database
        inquiryRepository.saveAndFlush(inquiry);

        // Get all the inquiryList where country equals to DEFAULT_COUNTRY
        defaultInquiryShouldBeFound("country.equals=" + DEFAULT_COUNTRY);

        // Get all the inquiryList where country equals to UPDATED_COUNTRY
        defaultInquiryShouldNotBeFound("country.equals=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    public void getAllInquiriesByCountryIsInShouldWork() throws Exception {
        // Initialize the database
        inquiryRepository.saveAndFlush(inquiry);

        // Get all the inquiryList where country in DEFAULT_COUNTRY or UPDATED_COUNTRY
        defaultInquiryShouldBeFound("country.in=" + DEFAULT_COUNTRY + "," + UPDATED_COUNTRY);

        // Get all the inquiryList where country equals to UPDATED_COUNTRY
        defaultInquiryShouldNotBeFound("country.in=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    public void getAllInquiriesByCountryIsNullOrNotNull() throws Exception {
        // Initialize the database
        inquiryRepository.saveAndFlush(inquiry);

        // Get all the inquiryList where country is not null
        defaultInquiryShouldBeFound("country.specified=true");

        // Get all the inquiryList where country is null
        defaultInquiryShouldNotBeFound("country.specified=false");
    }

    @Test
    @Transactional
    public void getAllInquiriesByRegionIsEqualToSomething() throws Exception {
        // Initialize the database
        inquiryRepository.saveAndFlush(inquiry);

        // Get all the inquiryList where region equals to DEFAULT_REGION
        defaultInquiryShouldBeFound("region.equals=" + DEFAULT_REGION);

        // Get all the inquiryList where region equals to UPDATED_REGION
        defaultInquiryShouldNotBeFound("region.equals=" + UPDATED_REGION);
    }

    @Test
    @Transactional
    public void getAllInquiriesByRegionIsInShouldWork() throws Exception {
        // Initialize the database
        inquiryRepository.saveAndFlush(inquiry);

        // Get all the inquiryList where region in DEFAULT_REGION or UPDATED_REGION
        defaultInquiryShouldBeFound("region.in=" + DEFAULT_REGION + "," + UPDATED_REGION);

        // Get all the inquiryList where region equals to UPDATED_REGION
        defaultInquiryShouldNotBeFound("region.in=" + UPDATED_REGION);
    }

    @Test
    @Transactional
    public void getAllInquiriesByRegionIsNullOrNotNull() throws Exception {
        // Initialize the database
        inquiryRepository.saveAndFlush(inquiry);

        // Get all the inquiryList where region is not null
        defaultInquiryShouldBeFound("region.specified=true");

        // Get all the inquiryList where region is null
        defaultInquiryShouldNotBeFound("region.specified=false");
    }

    @Test
    @Transactional
    public void getAllInquiriesByProviceIsEqualToSomething() throws Exception {
        // Initialize the database
        inquiryRepository.saveAndFlush(inquiry);

        // Get all the inquiryList where provice equals to DEFAULT_PROVICE
        defaultInquiryShouldBeFound("provice.equals=" + DEFAULT_PROVICE);

        // Get all the inquiryList where provice equals to UPDATED_PROVICE
        defaultInquiryShouldNotBeFound("provice.equals=" + UPDATED_PROVICE);
    }

    @Test
    @Transactional
    public void getAllInquiriesByProviceIsInShouldWork() throws Exception {
        // Initialize the database
        inquiryRepository.saveAndFlush(inquiry);

        // Get all the inquiryList where provice in DEFAULT_PROVICE or UPDATED_PROVICE
        defaultInquiryShouldBeFound("provice.in=" + DEFAULT_PROVICE + "," + UPDATED_PROVICE);

        // Get all the inquiryList where provice equals to UPDATED_PROVICE
        defaultInquiryShouldNotBeFound("provice.in=" + UPDATED_PROVICE);
    }

    @Test
    @Transactional
    public void getAllInquiriesByProviceIsNullOrNotNull() throws Exception {
        // Initialize the database
        inquiryRepository.saveAndFlush(inquiry);

        // Get all the inquiryList where provice is not null
        defaultInquiryShouldBeFound("provice.specified=true");

        // Get all the inquiryList where provice is null
        defaultInquiryShouldNotBeFound("provice.specified=false");
    }

    @Test
    @Transactional
    public void getAllInquiriesByCityIsEqualToSomething() throws Exception {
        // Initialize the database
        inquiryRepository.saveAndFlush(inquiry);

        // Get all the inquiryList where city equals to DEFAULT_CITY
        defaultInquiryShouldBeFound("city.equals=" + DEFAULT_CITY);

        // Get all the inquiryList where city equals to UPDATED_CITY
        defaultInquiryShouldNotBeFound("city.equals=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    public void getAllInquiriesByCityIsInShouldWork() throws Exception {
        // Initialize the database
        inquiryRepository.saveAndFlush(inquiry);

        // Get all the inquiryList where city in DEFAULT_CITY or UPDATED_CITY
        defaultInquiryShouldBeFound("city.in=" + DEFAULT_CITY + "," + UPDATED_CITY);

        // Get all the inquiryList where city equals to UPDATED_CITY
        defaultInquiryShouldNotBeFound("city.in=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    public void getAllInquiriesByCityIsNullOrNotNull() throws Exception {
        // Initialize the database
        inquiryRepository.saveAndFlush(inquiry);

        // Get all the inquiryList where city is not null
        defaultInquiryShouldBeFound("city.specified=true");

        // Get all the inquiryList where city is null
        defaultInquiryShouldNotBeFound("city.specified=false");
    }

    @Test
    @Transactional
    public void getAllInquiriesByPostalCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        inquiryRepository.saveAndFlush(inquiry);

        // Get all the inquiryList where postalCode equals to DEFAULT_POSTAL_CODE
        defaultInquiryShouldBeFound("postalCode.equals=" + DEFAULT_POSTAL_CODE);

        // Get all the inquiryList where postalCode equals to UPDATED_POSTAL_CODE
        defaultInquiryShouldNotBeFound("postalCode.equals=" + UPDATED_POSTAL_CODE);
    }

    @Test
    @Transactional
    public void getAllInquiriesByPostalCodeIsInShouldWork() throws Exception {
        // Initialize the database
        inquiryRepository.saveAndFlush(inquiry);

        // Get all the inquiryList where postalCode in DEFAULT_POSTAL_CODE or UPDATED_POSTAL_CODE
        defaultInquiryShouldBeFound("postalCode.in=" + DEFAULT_POSTAL_CODE + "," + UPDATED_POSTAL_CODE);

        // Get all the inquiryList where postalCode equals to UPDATED_POSTAL_CODE
        defaultInquiryShouldNotBeFound("postalCode.in=" + UPDATED_POSTAL_CODE);
    }

    @Test
    @Transactional
    public void getAllInquiriesByPostalCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        inquiryRepository.saveAndFlush(inquiry);

        // Get all the inquiryList where postalCode is not null
        defaultInquiryShouldBeFound("postalCode.specified=true");

        // Get all the inquiryList where postalCode is null
        defaultInquiryShouldNotBeFound("postalCode.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultInquiryShouldBeFound(String filter) throws Exception {
        restInquiryMockMvc.perform(get("/api/inquiries?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inquiry.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].region").value(hasItem(DEFAULT_REGION.toString())))
            .andExpect(jsonPath("$.[*].provice").value(hasItem(DEFAULT_PROVICE.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultInquiryShouldNotBeFound(String filter) throws Exception {
        restInquiryMockMvc.perform(get("/api/inquiries?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingInquiry() throws Exception {
        // Get the inquiry
        restInquiryMockMvc.perform(get("/api/inquiries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInquiry() throws Exception {
        // Initialize the database
        inquiryService.save(inquiry);

        int databaseSizeBeforeUpdate = inquiryRepository.findAll().size();

        // Update the inquiry
        Inquiry updatedInquiry = inquiryRepository.findOne(inquiry.getId());
        // Disconnect from session so that the updates on updatedInquiry are not directly saved in db
        em.detach(updatedInquiry);
        updatedInquiry
            .createdDate(UPDATED_CREATED_DATE)
            .email(UPDATED_EMAIL)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .country(UPDATED_COUNTRY)
            .region(UPDATED_REGION)
            .provice(UPDATED_PROVICE)
            .city(UPDATED_CITY)
            .postalCode(UPDATED_POSTAL_CODE);

        restInquiryMockMvc.perform(put("/api/inquiries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedInquiry)))
            .andExpect(status().isOk());

        // Validate the Inquiry in the database
        List<Inquiry> inquiryList = inquiryRepository.findAll();
        assertThat(inquiryList).hasSize(databaseSizeBeforeUpdate);
        Inquiry testInquiry = inquiryList.get(inquiryList.size() - 1);
        assertThat(testInquiry.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testInquiry.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testInquiry.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testInquiry.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testInquiry.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testInquiry.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testInquiry.getRegion()).isEqualTo(UPDATED_REGION);
        assertThat(testInquiry.getProvice()).isEqualTo(UPDATED_PROVICE);
        assertThat(testInquiry.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testInquiry.getPostalCode()).isEqualTo(UPDATED_POSTAL_CODE);

        // Validate the Inquiry in Elasticsearch
        Inquiry inquiryEs = inquirySearchRepository.findOne(testInquiry.getId());
        assertThat(testInquiry.getCreatedDate()).isEqualTo(testInquiry.getCreatedDate());
        assertThat(inquiryEs).isEqualToIgnoringGivenFields(testInquiry, "createdDate");
    }

    @Test
    @Transactional
    public void updateNonExistingInquiry() throws Exception {
        int databaseSizeBeforeUpdate = inquiryRepository.findAll().size();

        // Create the Inquiry

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restInquiryMockMvc.perform(put("/api/inquiries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inquiry)))
            .andExpect(status().isCreated());

        // Validate the Inquiry in the database
        List<Inquiry> inquiryList = inquiryRepository.findAll();
        assertThat(inquiryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteInquiry() throws Exception {
        // Initialize the database
        inquiryService.save(inquiry);

        int databaseSizeBeforeDelete = inquiryRepository.findAll().size();

        // Get the inquiry
        restInquiryMockMvc.perform(delete("/api/inquiries/{id}", inquiry.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean inquiryExistsInEs = inquirySearchRepository.exists(inquiry.getId());
        assertThat(inquiryExistsInEs).isFalse();

        // Validate the database is empty
        List<Inquiry> inquiryList = inquiryRepository.findAll();
        assertThat(inquiryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchInquiry() throws Exception {
        // Initialize the database
        inquiryService.save(inquiry);

        // Search the inquiry
        restInquiryMockMvc.perform(get("/api/_search/inquiries?query=id:" + inquiry.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inquiry.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].region").value(hasItem(DEFAULT_REGION.toString())))
            .andExpect(jsonPath("$.[*].provice").value(hasItem(DEFAULT_PROVICE.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Inquiry.class);
        Inquiry inquiry1 = new Inquiry();
        inquiry1.setId(1L);
        Inquiry inquiry2 = new Inquiry();
        inquiry2.setId(inquiry1.getId());
        assertThat(inquiry1).isEqualTo(inquiry2);
        inquiry2.setId(2L);
        assertThat(inquiry1).isNotEqualTo(inquiry2);
        inquiry1.setId(null);
        assertThat(inquiry1).isNotEqualTo(inquiry2);
    }
}
