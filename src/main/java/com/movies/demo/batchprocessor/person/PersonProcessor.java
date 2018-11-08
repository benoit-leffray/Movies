package com.movies.demo.batchprocessor.person;

import com.movies.demo.DTOs.PersonDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
public class PersonProcessor implements ItemProcessor<PersonSource, PersonDto> {

    @Override
    public PersonDto process(final PersonSource source) throws Exception {

        PersonDto dto = new PersonDto();

        try {
            dto.setId(Long.parseLong(source.getId().substring(2)));

            dto.setPrimaryName(source.getPrimaryName());

            if (!source.getBirthYear().equals("\\N"))
                dto.setBirthYear(Integer.parseInt(source.getBirthYear()));

            if (!source.getDeathYear().equals("\\N"))
                dto.setDeathYear(Integer.parseInt(source.getDeathYear()));

        } catch (Exception e) {
            log.info("Invalid Person item: " + source.toString() + ", EX: " + e.getMessage());
            return null;
        }

        return dto;
    }
}