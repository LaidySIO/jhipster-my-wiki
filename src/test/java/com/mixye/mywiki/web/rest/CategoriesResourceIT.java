package com.mixye.mywiki.web.rest;

import com.mixye.mywiki.JhipsterMyWikiApp;
import com.mixye.mywiki.domain.Categories;
import com.mixye.mywiki.repository.CategoriesRepository;
import com.mixye.mywiki.service.CategoriesService;
import com.mixye.mywiki.service.dto.CategoriesDTO;
import com.mixye.mywiki.service.mapper.CategoriesMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CategoriesResource} REST controller.
 */
@SpringBootTest(classes = JhipsterMyWikiApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CategoriesResourceIT {

    private static final String DEFAULT_CATEGORY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_CATEGORY_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_CATEGORY_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_CATEGORY_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_CATEGORY_IMAGE_CONTENT_TYPE = "image/png";

    private static final Boolean DEFAULT_IS_PUBLIC = false;
    private static final Boolean UPDATED_IS_PUBLIC = true;

    private static final String DEFAULT_USER = "AAAAAAAAAA";
    private static final String UPDATED_USER = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATION_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private CategoriesRepository categoriesRepository;

    @Autowired
    private CategoriesMapper categoriesMapper;

    @Autowired
    private CategoriesService categoriesService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCategoriesMockMvc;

    private Categories categories;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Categories createEntity(EntityManager em) {
        Categories categories = new Categories()
            .categoryName(DEFAULT_CATEGORY_NAME)
            .description(DEFAULT_DESCRIPTION)
            .categoryImage(DEFAULT_CATEGORY_IMAGE)
            .categoryImageContentType(DEFAULT_CATEGORY_IMAGE_CONTENT_TYPE)
            .isPublic(DEFAULT_IS_PUBLIC)
            .user(DEFAULT_USER)
            .creationDate(DEFAULT_CREATION_DATE);
        return categories;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Categories createUpdatedEntity(EntityManager em) {
        Categories categories = new Categories()
            .categoryName(UPDATED_CATEGORY_NAME)
            .description(UPDATED_DESCRIPTION)
            .categoryImage(UPDATED_CATEGORY_IMAGE)
            .categoryImageContentType(UPDATED_CATEGORY_IMAGE_CONTENT_TYPE)
            .isPublic(UPDATED_IS_PUBLIC)
            .user(UPDATED_USER)
            .creationDate(UPDATED_CREATION_DATE);
        return categories;
    }

    @BeforeEach
    public void initTest() {
        categories = createEntity(em);
    }

    @Test
    @Transactional
    public void createCategories() throws Exception {
        int databaseSizeBeforeCreate = categoriesRepository.findAll().size();
        // Create the Categories
        CategoriesDTO categoriesDTO = categoriesMapper.toDto(categories);
        restCategoriesMockMvc.perform(post("/api/categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoriesDTO)))
            .andExpect(status().isCreated());

        // Validate the Categories in the database
        List<Categories> categoriesList = categoriesRepository.findAll();
        assertThat(categoriesList).hasSize(databaseSizeBeforeCreate + 1);
        Categories testCategories = categoriesList.get(categoriesList.size() - 1);
        assertThat(testCategories.getCategoryName()).isEqualTo(DEFAULT_CATEGORY_NAME);
        assertThat(testCategories.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCategories.getCategoryImage()).isEqualTo(DEFAULT_CATEGORY_IMAGE);
        assertThat(testCategories.getCategoryImageContentType()).isEqualTo(DEFAULT_CATEGORY_IMAGE_CONTENT_TYPE);
        assertThat(testCategories.isIsPublic()).isEqualTo(DEFAULT_IS_PUBLIC);
        assertThat(testCategories.getUser()).isEqualTo(DEFAULT_USER);
        assertThat(testCategories.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
    }

    @Test
    @Transactional
    public void createCategoriesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = categoriesRepository.findAll().size();

        // Create the Categories with an existing ID
        categories.setId(1L);
        CategoriesDTO categoriesDTO = categoriesMapper.toDto(categories);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCategoriesMockMvc.perform(post("/api/categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoriesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Categories in the database
        List<Categories> categoriesList = categoriesRepository.findAll();
        assertThat(categoriesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCategoryNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoriesRepository.findAll().size();
        // set the field null
        categories.setCategoryName(null);

        // Create the Categories, which fails.
        CategoriesDTO categoriesDTO = categoriesMapper.toDto(categories);


        restCategoriesMockMvc.perform(post("/api/categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoriesDTO)))
            .andExpect(status().isBadRequest());

        List<Categories> categoriesList = categoriesRepository.findAll();
        assertThat(categoriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoriesRepository.findAll().size();
        // set the field null
        categories.setDescription(null);

        // Create the Categories, which fails.
        CategoriesDTO categoriesDTO = categoriesMapper.toDto(categories);


        restCategoriesMockMvc.perform(post("/api/categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoriesDTO)))
            .andExpect(status().isBadRequest());

        List<Categories> categoriesList = categoriesRepository.findAll();
        assertThat(categoriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsPublicIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoriesRepository.findAll().size();
        // set the field null
        categories.setIsPublic(null);

        // Create the Categories, which fails.
        CategoriesDTO categoriesDTO = categoriesMapper.toDto(categories);


        restCategoriesMockMvc.perform(post("/api/categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoriesDTO)))
            .andExpect(status().isBadRequest());

        List<Categories> categoriesList = categoriesRepository.findAll();
        assertThat(categoriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUserIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoriesRepository.findAll().size();
        // set the field null
        categories.setUser(null);

        // Create the Categories, which fails.
        CategoriesDTO categoriesDTO = categoriesMapper.toDto(categories);


        restCategoriesMockMvc.perform(post("/api/categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoriesDTO)))
            .andExpect(status().isBadRequest());

        List<Categories> categoriesList = categoriesRepository.findAll();
        assertThat(categoriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoriesRepository.findAll().size();
        // set the field null
        categories.setCreationDate(null);

        // Create the Categories, which fails.
        CategoriesDTO categoriesDTO = categoriesMapper.toDto(categories);


        restCategoriesMockMvc.perform(post("/api/categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoriesDTO)))
            .andExpect(status().isBadRequest());

        List<Categories> categoriesList = categoriesRepository.findAll();
        assertThat(categoriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCategories() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList
        restCategoriesMockMvc.perform(get("/api/categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categories.getId().intValue())))
            .andExpect(jsonPath("$.[*].categoryName").value(hasItem(DEFAULT_CATEGORY_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].categoryImageContentType").value(hasItem(DEFAULT_CATEGORY_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].categoryImage").value(hasItem(Base64Utils.encodeToString(DEFAULT_CATEGORY_IMAGE))))
            .andExpect(jsonPath("$.[*].isPublic").value(hasItem(DEFAULT_IS_PUBLIC.booleanValue())))
            .andExpect(jsonPath("$.[*].user").value(hasItem(DEFAULT_USER)))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getCategories() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get the categories
        restCategoriesMockMvc.perform(get("/api/categories/{id}", categories.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(categories.getId().intValue()))
            .andExpect(jsonPath("$.categoryName").value(DEFAULT_CATEGORY_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.categoryImageContentType").value(DEFAULT_CATEGORY_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.categoryImage").value(Base64Utils.encodeToString(DEFAULT_CATEGORY_IMAGE)))
            .andExpect(jsonPath("$.isPublic").value(DEFAULT_IS_PUBLIC.booleanValue()))
            .andExpect(jsonPath("$.user").value(DEFAULT_USER))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingCategories() throws Exception {
        // Get the categories
        restCategoriesMockMvc.perform(get("/api/categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCategories() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        int databaseSizeBeforeUpdate = categoriesRepository.findAll().size();

        // Update the categories
        Categories updatedCategories = categoriesRepository.findById(categories.getId()).get();
        // Disconnect from session so that the updates on updatedCategories are not directly saved in db
        em.detach(updatedCategories);
        updatedCategories
            .categoryName(UPDATED_CATEGORY_NAME)
            .description(UPDATED_DESCRIPTION)
            .categoryImage(UPDATED_CATEGORY_IMAGE)
            .categoryImageContentType(UPDATED_CATEGORY_IMAGE_CONTENT_TYPE)
            .isPublic(UPDATED_IS_PUBLIC)
            .user(UPDATED_USER)
            .creationDate(UPDATED_CREATION_DATE);
        CategoriesDTO categoriesDTO = categoriesMapper.toDto(updatedCategories);

        restCategoriesMockMvc.perform(put("/api/categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoriesDTO)))
            .andExpect(status().isOk());

        // Validate the Categories in the database
        List<Categories> categoriesList = categoriesRepository.findAll();
        assertThat(categoriesList).hasSize(databaseSizeBeforeUpdate);
        Categories testCategories = categoriesList.get(categoriesList.size() - 1);
        assertThat(testCategories.getCategoryName()).isEqualTo(UPDATED_CATEGORY_NAME);
        assertThat(testCategories.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCategories.getCategoryImage()).isEqualTo(UPDATED_CATEGORY_IMAGE);
        assertThat(testCategories.getCategoryImageContentType()).isEqualTo(UPDATED_CATEGORY_IMAGE_CONTENT_TYPE);
        assertThat(testCategories.isIsPublic()).isEqualTo(UPDATED_IS_PUBLIC);
        assertThat(testCategories.getUser()).isEqualTo(UPDATED_USER);
        assertThat(testCategories.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingCategories() throws Exception {
        int databaseSizeBeforeUpdate = categoriesRepository.findAll().size();

        // Create the Categories
        CategoriesDTO categoriesDTO = categoriesMapper.toDto(categories);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategoriesMockMvc.perform(put("/api/categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoriesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Categories in the database
        List<Categories> categoriesList = categoriesRepository.findAll();
        assertThat(categoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCategories() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        int databaseSizeBeforeDelete = categoriesRepository.findAll().size();

        // Delete the categories
        restCategoriesMockMvc.perform(delete("/api/categories/{id}", categories.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Categories> categoriesList = categoriesRepository.findAll();
        assertThat(categoriesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
