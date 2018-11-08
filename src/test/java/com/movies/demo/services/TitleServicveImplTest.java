package com.movies.demo.services;

import com.movies.demo.DTOs.TitleDto;
import com.movies.demo.exception.NotFoundException;
import com.movies.demo.converters.TitleToTitleDto;
import com.movies.demo.domain.Title;
import com.movies.demo.repositories.TitleRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertTrue;

public class TitleServicveImplTest {

    TitleServiceImpl titleService;

    @Mock
    TitleRepository titleRepository;

    @Mock
    TitleToTitleDto titleToTitleDto;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        titleService = new TitleServiceImpl(titleRepository, titleToTitleDto);
    }

    @Test
    public void getTitleByName() throws Exception {
        final String primaryTitle = "A Title";
        final Long id = 1L;

        Title title = new Title();
        title.setId(id);
        title.setPrimaryTitle(primaryTitle);

        TitleDto dto = new TitleDto();
        dto.setId(id);
        dto.setPrimaryTitle(primaryTitle);

        when(titleRepository.findOneByPrimaryTitleOrOriginalTitle(anyString(), anyString())).thenReturn(title);
        when(titleToTitleDto.convert(title)).thenReturn(dto);

        TitleDto titleReturned = titleService.findByName(primaryTitle);

        assertNotNull("Null Title returned", titleReturned);
    }

    @Test(expected = NotFoundException.class)
    public void getTitleByNameThrow() throws Exception {
        final String primaryTitle = "A Title";
        final Long id = 1L;

        Title title = new Title();
        title.setId(id);
        title.setPrimaryTitle(primaryTitle);

        TitleDto dto = new TitleDto();
        dto.setId(id);
        dto.setPrimaryTitle(primaryTitle);

        when(titleRepository.findOneByPrimaryTitleOrOriginalTitle(anyString(), anyString())).thenReturn(null);
        when(titleToTitleDto.convert(title)).thenReturn(dto);

        titleService.findByName(primaryTitle);

        // Should throw
    }

    @Test
    public void getRatedByGenre() throws Exception {
        final String primaryTitle = "A genre";
        final Long id = 1L;

        Title title = new Title();
        title.setId(id);
        title.setPrimaryTitle(primaryTitle);

        List<Title> titles = new ArrayList<>();
        titles.add(title);

        TitleDto dto = new TitleDto();
        dto.setId(id);
        dto.setPrimaryTitle(primaryTitle);

        when(titleRepository.findAllByGenresOrderByRatings_AverageRatingDesc(anyString())).thenReturn(titles);
        when(titleToTitleDto.convert(title)).thenReturn(dto);

        Iterable<TitleDto> titlesReturned = titleService.findAllRatedByGenre("whatever");

        assertTrue("Empty Title  returned", titlesReturned.iterator().hasNext());
        assertEquals(primaryTitle, titlesReturned.iterator().next().getPrimaryTitle());
    }

    @Test
    public void getNoGenre() throws Exception {

        List<Title> titles = new ArrayList<>();

        when(titleRepository.findAllByGenresOrderByRatings_AverageRatingDesc(anyString())).thenReturn(titles);

        Iterable<TitleDto> titlesReturned = titleService.findAllRatedByGenre("whatever");

        assertFalse("Non Empty Title  returned", titlesReturned.iterator().hasNext());
    }

    @Test
    public void getCoincidences() throws Exception {
        final String primaryTitle = "A coincidence";
        final Long id = 1L;

        Title title = new Title();
        title.setId(id);
        title.setPrimaryTitle(primaryTitle);

        List<Title> titles = new ArrayList<>();
        titles.add(title);

        TitleDto dto = new TitleDto();
        dto.setId(id);
        dto.setPrimaryTitle(primaryTitle);

        when(titleRepository.findAllByPersonCoincidence(anyString(), anyString())).thenReturn(titles);
        when(titleToTitleDto.convert(title)).thenReturn(dto);

        Iterable<TitleDto> titlesReturned = titleService.findAllByPersonCoincidence("whatever", "whatever");

        assertTrue("Empty Title  returned", titlesReturned.iterator().hasNext());
        assertEquals(primaryTitle, titlesReturned.iterator().next().getPrimaryTitle());
    }

    @Test
    public void getNoCoincidences() throws Exception {

        List<Title> titles = new ArrayList<>();

        when(titleRepository.findAllByPersonCoincidence(anyString(), anyString())).thenReturn(titles);

        Iterable<TitleDto> titlesReturned = titleService.findAllByPersonCoincidence("whatever", "whatever");

        assertTrue("Non Empty Title  returned", !titlesReturned.iterator().hasNext());
    }
}