package com.movies.demo.converters;

import com.movies.demo.DTOs.PersonDto;
import com.movies.demo.domain.Person;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class PersonToPersonDto implements Converter<Person, PersonDto> {
    @Synchronized
    @Nullable
    @Override
    public PersonDto convert(Person source) {
        if (source == null) {
            return null;
        }

        final PersonDto dto = new PersonDto();
        dto.setId(source.getId());
        dto.setPrimaryName(source.getPrimaryName());
        dto.setBirthYear(source.getBirthYear());
        dto.setDeathYear(source.getDeathYear());

        return dto;
    }
}