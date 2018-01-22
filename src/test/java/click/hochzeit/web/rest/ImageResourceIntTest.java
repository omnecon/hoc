package click.hochzeit.web.rest;

import click.hochzeit.Hochzeitclick11App;

import click.hochzeit.domain.Image;
import click.hochzeit.repository.ImageRepository;
import click.hochzeit.service.ImageService;
import click.hochzeit.repository.search.ImageSearchRepository;
import click.hochzeit.web.rest.errors.ExceptionTranslator;
import click.hochzeit.service.dto.ImageCriteria;
import click.hochzeit.service.ImageQueryService;

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
 * Test class for the ImageResource REST controller.
 *
 * @see ImageResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Hochzeitclick11App.class)
public class ImageResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_ALT = "AAAAAAAAAA";
    private static final String UPDATED_ALT = "BBBBBBBBBB";

    private static final String DEFAULT_CAPTION = "AAAAAAAAAA";
    private static final String UPDATED_CAPTION = "BBBBBBBBBB";

    private static final String DEFAULT_IMG_ORIGINAL = "AAAAAAAAAA";
    private static final String UPDATED_IMG_ORIGINAL = "BBBBBBBBBB";

    private static final String DEFAULT_IMG_LARGE = "AAAAAAAAAA";
    private static final String UPDATED_IMG_LARGE = "BBBBBBBBBB";

    private static final String DEFAULT_IMG_THUMBNAIL = "AAAAAAAAAA";
    private static final String UPDATED_IMG_THUMBNAIL = "BBBBBBBBBB";

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ImageService imageService;

    @Autowired
    private ImageSearchRepository imageSearchRepository;

    @Autowired
    private ImageQueryService imageQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restImageMockMvc;

    private Image image;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ImageResource imageResource = new ImageResource(imageService, imageQueryService);
        this.restImageMockMvc = MockMvcBuilders.standaloneSetup(imageResource)
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
    public static Image createEntity(EntityManager em) {
        Image image = new Image()
            .title(DEFAULT_TITLE)
            .alt(DEFAULT_ALT)
            .caption(DEFAULT_CAPTION)
            .imgOriginal(DEFAULT_IMG_ORIGINAL)
            .imgLarge(DEFAULT_IMG_LARGE)
            .imgThumbnail(DEFAULT_IMG_THUMBNAIL);
        return image;
    }

    @Before
    public void initTest() {
        imageSearchRepository.deleteAll();
        image = createEntity(em);
    }

    @Test
    @Transactional
    public void createImage() throws Exception {
        int databaseSizeBeforeCreate = imageRepository.findAll().size();

        // Create the Image
        restImageMockMvc.perform(post("/api/images")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(image)))
            .andExpect(status().isCreated());

        // Validate the Image in the database
        List<Image> imageList = imageRepository.findAll();
        assertThat(imageList).hasSize(databaseSizeBeforeCreate + 1);
        Image testImage = imageList.get(imageList.size() - 1);
        assertThat(testImage.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testImage.getAlt()).isEqualTo(DEFAULT_ALT);
        assertThat(testImage.getCaption()).isEqualTo(DEFAULT_CAPTION);
        assertThat(testImage.getImgOriginal()).isEqualTo(DEFAULT_IMG_ORIGINAL);
        assertThat(testImage.getImgLarge()).isEqualTo(DEFAULT_IMG_LARGE);
        assertThat(testImage.getImgThumbnail()).isEqualTo(DEFAULT_IMG_THUMBNAIL);

        // Validate the Image in Elasticsearch
        Image imageEs = imageSearchRepository.findOne(testImage.getId());
        assertThat(imageEs).isEqualToIgnoringGivenFields(testImage);
    }

    @Test
    @Transactional
    public void createImageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = imageRepository.findAll().size();

        // Create the Image with an existing ID
        image.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restImageMockMvc.perform(post("/api/images")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(image)))
            .andExpect(status().isBadRequest());

        // Validate the Image in the database
        List<Image> imageList = imageRepository.findAll();
        assertThat(imageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllImages() throws Exception {
        // Initialize the database
        imageRepository.saveAndFlush(image);

        // Get all the imageList
        restImageMockMvc.perform(get("/api/images?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(image.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].alt").value(hasItem(DEFAULT_ALT.toString())))
            .andExpect(jsonPath("$.[*].caption").value(hasItem(DEFAULT_CAPTION.toString())))
            .andExpect(jsonPath("$.[*].imgOriginal").value(hasItem(DEFAULT_IMG_ORIGINAL.toString())))
            .andExpect(jsonPath("$.[*].imgLarge").value(hasItem(DEFAULT_IMG_LARGE.toString())))
            .andExpect(jsonPath("$.[*].imgThumbnail").value(hasItem(DEFAULT_IMG_THUMBNAIL.toString())));
    }

    @Test
    @Transactional
    public void getImage() throws Exception {
        // Initialize the database
        imageRepository.saveAndFlush(image);

        // Get the image
        restImageMockMvc.perform(get("/api/images/{id}", image.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(image.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.alt").value(DEFAULT_ALT.toString()))
            .andExpect(jsonPath("$.caption").value(DEFAULT_CAPTION.toString()))
            .andExpect(jsonPath("$.imgOriginal").value(DEFAULT_IMG_ORIGINAL.toString()))
            .andExpect(jsonPath("$.imgLarge").value(DEFAULT_IMG_LARGE.toString()))
            .andExpect(jsonPath("$.imgThumbnail").value(DEFAULT_IMG_THUMBNAIL.toString()));
    }

    @Test
    @Transactional
    public void getAllImagesByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        imageRepository.saveAndFlush(image);

        // Get all the imageList where title equals to DEFAULT_TITLE
        defaultImageShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the imageList where title equals to UPDATED_TITLE
        defaultImageShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllImagesByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        imageRepository.saveAndFlush(image);

        // Get all the imageList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultImageShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the imageList where title equals to UPDATED_TITLE
        defaultImageShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllImagesByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        imageRepository.saveAndFlush(image);

        // Get all the imageList where title is not null
        defaultImageShouldBeFound("title.specified=true");

        // Get all the imageList where title is null
        defaultImageShouldNotBeFound("title.specified=false");
    }

    @Test
    @Transactional
    public void getAllImagesByAltIsEqualToSomething() throws Exception {
        // Initialize the database
        imageRepository.saveAndFlush(image);

        // Get all the imageList where alt equals to DEFAULT_ALT
        defaultImageShouldBeFound("alt.equals=" + DEFAULT_ALT);

        // Get all the imageList where alt equals to UPDATED_ALT
        defaultImageShouldNotBeFound("alt.equals=" + UPDATED_ALT);
    }

    @Test
    @Transactional
    public void getAllImagesByAltIsInShouldWork() throws Exception {
        // Initialize the database
        imageRepository.saveAndFlush(image);

        // Get all the imageList where alt in DEFAULT_ALT or UPDATED_ALT
        defaultImageShouldBeFound("alt.in=" + DEFAULT_ALT + "," + UPDATED_ALT);

        // Get all the imageList where alt equals to UPDATED_ALT
        defaultImageShouldNotBeFound("alt.in=" + UPDATED_ALT);
    }

    @Test
    @Transactional
    public void getAllImagesByAltIsNullOrNotNull() throws Exception {
        // Initialize the database
        imageRepository.saveAndFlush(image);

        // Get all the imageList where alt is not null
        defaultImageShouldBeFound("alt.specified=true");

        // Get all the imageList where alt is null
        defaultImageShouldNotBeFound("alt.specified=false");
    }

    @Test
    @Transactional
    public void getAllImagesByCaptionIsEqualToSomething() throws Exception {
        // Initialize the database
        imageRepository.saveAndFlush(image);

        // Get all the imageList where caption equals to DEFAULT_CAPTION
        defaultImageShouldBeFound("caption.equals=" + DEFAULT_CAPTION);

        // Get all the imageList where caption equals to UPDATED_CAPTION
        defaultImageShouldNotBeFound("caption.equals=" + UPDATED_CAPTION);
    }

    @Test
    @Transactional
    public void getAllImagesByCaptionIsInShouldWork() throws Exception {
        // Initialize the database
        imageRepository.saveAndFlush(image);

        // Get all the imageList where caption in DEFAULT_CAPTION or UPDATED_CAPTION
        defaultImageShouldBeFound("caption.in=" + DEFAULT_CAPTION + "," + UPDATED_CAPTION);

        // Get all the imageList where caption equals to UPDATED_CAPTION
        defaultImageShouldNotBeFound("caption.in=" + UPDATED_CAPTION);
    }

    @Test
    @Transactional
    public void getAllImagesByCaptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        imageRepository.saveAndFlush(image);

        // Get all the imageList where caption is not null
        defaultImageShouldBeFound("caption.specified=true");

        // Get all the imageList where caption is null
        defaultImageShouldNotBeFound("caption.specified=false");
    }

    @Test
    @Transactional
    public void getAllImagesByImgOriginalIsEqualToSomething() throws Exception {
        // Initialize the database
        imageRepository.saveAndFlush(image);

        // Get all the imageList where imgOriginal equals to DEFAULT_IMG_ORIGINAL
        defaultImageShouldBeFound("imgOriginal.equals=" + DEFAULT_IMG_ORIGINAL);

        // Get all the imageList where imgOriginal equals to UPDATED_IMG_ORIGINAL
        defaultImageShouldNotBeFound("imgOriginal.equals=" + UPDATED_IMG_ORIGINAL);
    }

    @Test
    @Transactional
    public void getAllImagesByImgOriginalIsInShouldWork() throws Exception {
        // Initialize the database
        imageRepository.saveAndFlush(image);

        // Get all the imageList where imgOriginal in DEFAULT_IMG_ORIGINAL or UPDATED_IMG_ORIGINAL
        defaultImageShouldBeFound("imgOriginal.in=" + DEFAULT_IMG_ORIGINAL + "," + UPDATED_IMG_ORIGINAL);

        // Get all the imageList where imgOriginal equals to UPDATED_IMG_ORIGINAL
        defaultImageShouldNotBeFound("imgOriginal.in=" + UPDATED_IMG_ORIGINAL);
    }

    @Test
    @Transactional
    public void getAllImagesByImgOriginalIsNullOrNotNull() throws Exception {
        // Initialize the database
        imageRepository.saveAndFlush(image);

        // Get all the imageList where imgOriginal is not null
        defaultImageShouldBeFound("imgOriginal.specified=true");

        // Get all the imageList where imgOriginal is null
        defaultImageShouldNotBeFound("imgOriginal.specified=false");
    }

    @Test
    @Transactional
    public void getAllImagesByImgLargeIsEqualToSomething() throws Exception {
        // Initialize the database
        imageRepository.saveAndFlush(image);

        // Get all the imageList where imgLarge equals to DEFAULT_IMG_LARGE
        defaultImageShouldBeFound("imgLarge.equals=" + DEFAULT_IMG_LARGE);

        // Get all the imageList where imgLarge equals to UPDATED_IMG_LARGE
        defaultImageShouldNotBeFound("imgLarge.equals=" + UPDATED_IMG_LARGE);
    }

    @Test
    @Transactional
    public void getAllImagesByImgLargeIsInShouldWork() throws Exception {
        // Initialize the database
        imageRepository.saveAndFlush(image);

        // Get all the imageList where imgLarge in DEFAULT_IMG_LARGE or UPDATED_IMG_LARGE
        defaultImageShouldBeFound("imgLarge.in=" + DEFAULT_IMG_LARGE + "," + UPDATED_IMG_LARGE);

        // Get all the imageList where imgLarge equals to UPDATED_IMG_LARGE
        defaultImageShouldNotBeFound("imgLarge.in=" + UPDATED_IMG_LARGE);
    }

    @Test
    @Transactional
    public void getAllImagesByImgLargeIsNullOrNotNull() throws Exception {
        // Initialize the database
        imageRepository.saveAndFlush(image);

        // Get all the imageList where imgLarge is not null
        defaultImageShouldBeFound("imgLarge.specified=true");

        // Get all the imageList where imgLarge is null
        defaultImageShouldNotBeFound("imgLarge.specified=false");
    }

    @Test
    @Transactional
    public void getAllImagesByImgThumbnailIsEqualToSomething() throws Exception {
        // Initialize the database
        imageRepository.saveAndFlush(image);

        // Get all the imageList where imgThumbnail equals to DEFAULT_IMG_THUMBNAIL
        defaultImageShouldBeFound("imgThumbnail.equals=" + DEFAULT_IMG_THUMBNAIL);

        // Get all the imageList where imgThumbnail equals to UPDATED_IMG_THUMBNAIL
        defaultImageShouldNotBeFound("imgThumbnail.equals=" + UPDATED_IMG_THUMBNAIL);
    }

    @Test
    @Transactional
    public void getAllImagesByImgThumbnailIsInShouldWork() throws Exception {
        // Initialize the database
        imageRepository.saveAndFlush(image);

        // Get all the imageList where imgThumbnail in DEFAULT_IMG_THUMBNAIL or UPDATED_IMG_THUMBNAIL
        defaultImageShouldBeFound("imgThumbnail.in=" + DEFAULT_IMG_THUMBNAIL + "," + UPDATED_IMG_THUMBNAIL);

        // Get all the imageList where imgThumbnail equals to UPDATED_IMG_THUMBNAIL
        defaultImageShouldNotBeFound("imgThumbnail.in=" + UPDATED_IMG_THUMBNAIL);
    }

    @Test
    @Transactional
    public void getAllImagesByImgThumbnailIsNullOrNotNull() throws Exception {
        // Initialize the database
        imageRepository.saveAndFlush(image);

        // Get all the imageList where imgThumbnail is not null
        defaultImageShouldBeFound("imgThumbnail.specified=true");

        // Get all the imageList where imgThumbnail is null
        defaultImageShouldNotBeFound("imgThumbnail.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultImageShouldBeFound(String filter) throws Exception {
        restImageMockMvc.perform(get("/api/images?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(image.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].alt").value(hasItem(DEFAULT_ALT.toString())))
            .andExpect(jsonPath("$.[*].caption").value(hasItem(DEFAULT_CAPTION.toString())))
            .andExpect(jsonPath("$.[*].imgOriginal").value(hasItem(DEFAULT_IMG_ORIGINAL.toString())))
            .andExpect(jsonPath("$.[*].imgLarge").value(hasItem(DEFAULT_IMG_LARGE.toString())))
            .andExpect(jsonPath("$.[*].imgThumbnail").value(hasItem(DEFAULT_IMG_THUMBNAIL.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultImageShouldNotBeFound(String filter) throws Exception {
        restImageMockMvc.perform(get("/api/images?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingImage() throws Exception {
        // Get the image
        restImageMockMvc.perform(get("/api/images/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateImage() throws Exception {
        // Initialize the database
        imageService.save(image);

        int databaseSizeBeforeUpdate = imageRepository.findAll().size();

        // Update the image
        Image updatedImage = imageRepository.findOne(image.getId());
        // Disconnect from session so that the updates on updatedImage are not directly saved in db
        em.detach(updatedImage);
        updatedImage
            .title(UPDATED_TITLE)
            .alt(UPDATED_ALT)
            .caption(UPDATED_CAPTION)
            .imgOriginal(UPDATED_IMG_ORIGINAL)
            .imgLarge(UPDATED_IMG_LARGE)
            .imgThumbnail(UPDATED_IMG_THUMBNAIL);

        restImageMockMvc.perform(put("/api/images")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedImage)))
            .andExpect(status().isOk());

        // Validate the Image in the database
        List<Image> imageList = imageRepository.findAll();
        assertThat(imageList).hasSize(databaseSizeBeforeUpdate);
        Image testImage = imageList.get(imageList.size() - 1);
        assertThat(testImage.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testImage.getAlt()).isEqualTo(UPDATED_ALT);
        assertThat(testImage.getCaption()).isEqualTo(UPDATED_CAPTION);
        assertThat(testImage.getImgOriginal()).isEqualTo(UPDATED_IMG_ORIGINAL);
        assertThat(testImage.getImgLarge()).isEqualTo(UPDATED_IMG_LARGE);
        assertThat(testImage.getImgThumbnail()).isEqualTo(UPDATED_IMG_THUMBNAIL);

        // Validate the Image in Elasticsearch
        Image imageEs = imageSearchRepository.findOne(testImage.getId());
        assertThat(imageEs).isEqualToIgnoringGivenFields(testImage);
    }

    @Test
    @Transactional
    public void updateNonExistingImage() throws Exception {
        int databaseSizeBeforeUpdate = imageRepository.findAll().size();

        // Create the Image

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restImageMockMvc.perform(put("/api/images")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(image)))
            .andExpect(status().isCreated());

        // Validate the Image in the database
        List<Image> imageList = imageRepository.findAll();
        assertThat(imageList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteImage() throws Exception {
        // Initialize the database
        imageService.save(image);

        int databaseSizeBeforeDelete = imageRepository.findAll().size();

        // Get the image
        restImageMockMvc.perform(delete("/api/images/{id}", image.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean imageExistsInEs = imageSearchRepository.exists(image.getId());
        assertThat(imageExistsInEs).isFalse();

        // Validate the database is empty
        List<Image> imageList = imageRepository.findAll();
        assertThat(imageList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchImage() throws Exception {
        // Initialize the database
        imageService.save(image);

        // Search the image
        restImageMockMvc.perform(get("/api/_search/images?query=id:" + image.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(image.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].alt").value(hasItem(DEFAULT_ALT.toString())))
            .andExpect(jsonPath("$.[*].caption").value(hasItem(DEFAULT_CAPTION.toString())))
            .andExpect(jsonPath("$.[*].imgOriginal").value(hasItem(DEFAULT_IMG_ORIGINAL.toString())))
            .andExpect(jsonPath("$.[*].imgLarge").value(hasItem(DEFAULT_IMG_LARGE.toString())))
            .andExpect(jsonPath("$.[*].imgThumbnail").value(hasItem(DEFAULT_IMG_THUMBNAIL.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Image.class);
        Image image1 = new Image();
        image1.setId(1L);
        Image image2 = new Image();
        image2.setId(image1.getId());
        assertThat(image1).isEqualTo(image2);
        image2.setId(2L);
        assertThat(image1).isNotEqualTo(image2);
        image1.setId(null);
        assertThat(image1).isNotEqualTo(image2);
    }
}
