package com.movies.demo.controllers;

import com.movies.demo.DTOs.TitleDto;
import com.movies.demo.domain.Principal;
import com.movies.demo.services.PrincipalService;
import com.movies.demo.services.TitleService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class PrincipalControllerTest {

    @Mock
    PrincipalService principalService;

    PrincipalController controller;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        controller = new PrincipalController(principalService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new NotFoundExceptionController())
                .build();
    }

    @Test
    public void testGetIsTypeCasted() throws Exception {

        mockMvc.perform(get("/principal/is-typecasted"))
                .andExpect(status().isOk())
                .andExpect(view().name("principal/isTypeCasted"))
                .andExpect(model().attributeExists("typecasted"));
    }

    @Test
    public void testPostIsTypeCasted() throws Exception {

        when(principalService.findTypeCasted(anyString())).thenReturn(true);

        mockMvc.perform(post("/principal/is-typecasted")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", "whatever"))
                .andExpect(status().isOk())
                .andExpect(view().name("principal/typecast"))
                .andExpect(model().attributeExists("typecasted"));
    }
}