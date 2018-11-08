package com.movies.demo.services;

import com.movies.demo.DTOs.TitleDto;
import com.movies.demo.converters.TitleToTitleDto;
import com.movies.demo.domain.Title;
import com.movies.demo.repositories.TitleRepository;
import com.movies.demo.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TitleServiceImpl implements TitleService {

    private final TitleRepository titleRepository;
    private final TitleToTitleDto titleConverter;

    public TitleServiceImpl(TitleRepository titleRepository, TitleToTitleDto titleConverter) {
        this.titleRepository = titleRepository;
        this.titleConverter = titleConverter;
    }

    @Override
    public TitleDto findByName(String name) {
        Title title = titleRepository.findOneByPrimaryTitleOrOriginalTitle(name, name);

        if (title == null) {
            throw new NotFoundException("Title Not Found. For name: " + name);
        }

        return titleConverter.convert(title);
    }

    @Override
    public Iterable<TitleDto> findAllRatedByGenre(String genre) {
        return convert(titleRepository.findAllByGenresOrderByRatings_AverageRatingDesc(genre));
   }

    @Override
    public Iterable<TitleDto> findAllByPersonCoincidence(String personName1, String personName2) {
        return convert(titleRepository.findAllByPersonCoincidence(personName1, personName2));
    }

    private Iterable<TitleDto> convert(Iterable<Title> titles) {
        List<TitleDto> result = new ArrayList<>();

        if (titles.iterator().hasNext()) {
            titles.forEach((Title t) -> result.add(titleConverter.convert(t)));
        }

        return result;
    }

}