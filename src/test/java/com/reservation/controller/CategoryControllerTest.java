package com.reservation.controller;

import com.reservation.model.Category;
import com.reservation.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class CategoryControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
    }

    @Test
    void testCreateCategory_Success() throws Exception {
        Category category = new Category("Concert");

        when(categoryService.createCategory(any(Category.class))).thenReturn(category);

        mockMvc.perform(post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"Concert\" }"))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteCategory_NotFound() throws Exception {
        doThrow(new RuntimeException("Category not found")).when(categoryService).deleteCategory(1L);

        mockMvc.perform(delete("/categories/1"))
                .andExpect(status().isBadRequest());
    }
}
