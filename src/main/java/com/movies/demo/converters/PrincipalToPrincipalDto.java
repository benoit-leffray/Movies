package com.movies.demo.converters;

import com.movies.demo.DTOs.PrincipalDto;
import com.movies.demo.domain.Principal;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class PrincipalToPrincipalDto implements Converter<Principal, PrincipalDto> {
    @Synchronized
    @Nullable
    @Override
    public PrincipalDto convert(Principal source) {
        if (source == null) {
            return null;
        }

        final PrincipalDto dto = new PrincipalDto();
        dto.setId(source.getId());
        dto.setOrdering(source.getOrdering());
        dto.setCategory(source.getCategory());
        dto.setCharacters(source.getCharacters());
        dto.setJob(source.getJob());
        dto.setPerson(source.getPerson());

        return dto;
    }
}