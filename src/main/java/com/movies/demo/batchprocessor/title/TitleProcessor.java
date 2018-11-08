package com.movies.demo.batchprocessor.title;

import com.movies.demo.DTOs.TitleDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
public class TitleProcessor implements ItemProcessor<TitleSource, TitleDto> {

    @Override
    public TitleDto process(final TitleSource source) throws Exception {

        TitleDto dto = new TitleDto();

        try {
            dto.setId(Long.parseLong(source.getId().substring(2)));

            dto.setType(source.getType());
            dto.setPrimaryTitle(source.getPrimaryTitle());
            dto.setOriginalTitle(source.getOriginalTitle());
            dto.setIsAdult(Boolean.parseBoolean(source.getIsAdult()));

            if (!source.getStartYear().equals("\\N"))
                dto.setStartYear(Integer.parseInt(source.getStartYear()));

            if (!source.getEndYear().equals("\\N"))
                dto.setEndYear(Integer.parseInt(source.getEndYear()));

            if (!source.getRuntimeMinutes().equals("\\N"))
                dto.setRuntimeMinutes(Integer.parseInt(source.getRuntimeMinutes()));

            dto.setGenres(source.getGenres());

        } catch (Exception e) {
            log.info("Invalid Title item: " + source.toString() + ", EX: " + e.getMessage());
            return null;
        }

        return dto;
    }
}