package com.movies.demo.converters;

import com.movies.demo.DTOs.PersonDto;
import com.movies.demo.domain.Person;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class PersonDtoToPerson implements Converter<PersonDto, Person> {
    @Synchronized
    @Nullable
    @Override
    public Person convert(PersonDto source) {
        if (source == null) {
            return null;
        }

        final Person person = new Person();
        person.setId(source.getId());
        person.setPrimaryName(source.getPrimaryName());
        person.setBirthYear(source.getBirthYear());
        person.setDeathYear(source.getDeathYear());

        return person;
    }
}