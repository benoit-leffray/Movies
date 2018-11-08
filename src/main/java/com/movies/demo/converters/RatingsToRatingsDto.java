package com.movies.demo.converters;

import com.movies.demo.DTOs.RatingsDto;
import com.movies.demo.domain.Ratings;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RatingsToRatingsDto implements Converter<Ratings, RatingsDto> {
    @Synchronized
    @Nullable
    @Override
    public RatingsDto convert(Ratings source) {
        if (source == null) {
            return null;
        }

        final RatingsDto dto = new RatingsDto();
        dto.setId(source.getId());
        //dto.setTitle(source.getTitle());
        dto.setAverageRating(source.getAverageRating());
        dto.setNumVotes(source.getNumVotes());

        return dto;
    }
}