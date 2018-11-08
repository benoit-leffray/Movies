package com.movies.demo.batchprocessor.ratings;

import com.movies.demo.DTOs.RatingsDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
public class RatingsProcessor implements ItemProcessor<RatingsSource, RatingsDto> {

    @Override
    public RatingsDto process(final RatingsSource source) throws Exception {

        RatingsDto dto = new RatingsDto();

        try {
            dto.setId(Long.parseLong(source.getId().substring(2)));

            dto.setAverageRating(Double.parseDouble(source.getAverageRating()));
            dto.setNumVotes(Integer.parseInt(source.getNumVotes()));

        } catch (Exception e) {
            log.info("Invalid Ratings item: " + source.toString() + ", EX: " + e.getMessage());
            return null;
        }

        return dto;
    }
}