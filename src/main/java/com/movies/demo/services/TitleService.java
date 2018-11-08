package com.movies.demo.services;

import com.movies.demo.DTOs.TitleDto;

public interface TitleService {

    TitleDto findByName(String title);

    Iterable<TitleDto> findAllRatedByGenre(String genre);

    Iterable<TitleDto> findAllByPersonCoincidence(String personName1, String personName2);
}