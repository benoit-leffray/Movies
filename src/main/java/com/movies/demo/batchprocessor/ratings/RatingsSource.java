package com.movies.demo.batchprocessor.ratings;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class RatingsSource {
    private String id;
    private String averageRating;
    private String numVotes;
}