package com.movies.demo.converters;

import com.movies.demo.DTOs.TitleDto;
import com.movies.demo.domain.Principal;
import com.movies.demo.domain.Title;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class TitleToTitleDto implements Converter<Title, TitleDto> {

    private final RatingsToRatingsDto ratingsConverter;
    private final PrincipalToPrincipalDto principalConverter;

    public TitleToTitleDto(RatingsToRatingsDto ratingsConverter, PrincipalToPrincipalDto principalConverter) {
        this.ratingsConverter = ratingsConverter;
        this.principalConverter = principalConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public TitleDto convert(Title source) {
        if (source == null) {
            return null;
        }

        final TitleDto dto = new TitleDto();
        dto.setId(source.getId());
        dto.setPrimaryTitle(source.getPrimaryTitle());
        dto.setOriginalTitle(source.getOriginalTitle());
        dto.setIsAdult(source.getIsAdult());
        dto.setStartYear(source.getStartYear());
        dto.setEndYear(source.getEndYear());
        dto.setRuntimeMinutes(source.getRuntimeMinutes());
        dto.setGenres(source.getGenres());

        dto.setRatings(ratingsConverter.convert(source.getRatings()));

        if (source.getPrincipals() != null && source.getPrincipals().size() > 0) {
            source.getPrincipals()
                    .forEach((Principal p) -> dto.getPrincipals().add(principalConverter.convert(p)));
        }

        return dto;
    }
}