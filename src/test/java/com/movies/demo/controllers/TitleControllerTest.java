package com.movies.demo.controllers;

import com.movies.demo.DTOs.TitleDto;
import com.movies.demo.commands.FindTitleByName;
import com.movies.demo.exception.NotFoundException;
import com.movies.demo.services.TitleService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class TitleControllerTest {

    @Mock
    TitleService titleService;

    TitleController controller;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        controller = new TitleController(titleService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new NotFoundExceptionController())
                .build();
    }

    @Test
    public void testGetTitle() throws Exception {

        mockMvc.perform(get("/title/find-title"))
                .andExpect(status().isOk())
                .andExpect(view().name("title/findTitle"))
                .andExpect(model().attributeExists("title"));
    }

    @Test
    public void testPostTitle() throws Exception {

        TitleDto dto = new TitleDto();

        when(titleService.findByName(anyString())).thenReturn(dto);

        mockMvc.perform(post("/title/find-title")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", "whatever"))
                .andExpect(status().isOk())
                .andExpect(view().name("title/show"))
                .andExpect(model().attributeExists("title"));
    }


    @Test
    public void testPostTitleNotFound() throws Exception {

        when(titleService.findByName(anyString())).thenThrow(NotFoundException.class);

        mockMvc.perform(post("/title/find-title")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", "whatever"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("error/notFound"));
    }

    @Test
    public void testGetGenre() throws Exception {

        mockMvc.perform(get("/title/find-genre"))
                .andExpect(status().isOk())
                .andExpect(view().name("title/findGenre"))
                .andExpect(model().attributeExists("title"));
    }

    @Test
    public void testPostGenre() throws Exception {

        TitleDto dto = new TitleDto();
        List<TitleDto> dtos = new ArrayList<>();
        dtos.add(dto);

        when(titleService.findAllRatedByGenre(anyString())).thenReturn(dtos);

        mockMvc.perform(post("/title/find-genre")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("genre", "whatever"))
                .andExpect(status().isOk())
                .andExpect(view().name("title/ratedByGenre"))
                .andExpect(model().attributeExists("titles"));
    }

    @Test
    public void testGetCoincidence() throws Exception {

        mockMvc.perform(get("/title/find-coincidence"))
                .andExpect(status().isOk())
                .andExpect(view().name("title/findCoincidence"))
                .andExpect(model().attributeExists("coincidence"));
    }

    @Test
    public void testPostcoincidence() throws Exception {

        TitleDto dto = new TitleDto();
        List<TitleDto> dtos = new ArrayList<>();
        dtos.add(dto);

        when(titleService.findAllRatedByGenre(anyString())).thenReturn(dtos);

        mockMvc.perform(post("/title/find-coincidence")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("personName1", "whatever")
                .param("personName2", "whatever"))
                .andExpect(status().isOk())
                .andExpect(view().name("title/coincidence"))
                .andExpect(model().attributeExists("titles"));
    }
}