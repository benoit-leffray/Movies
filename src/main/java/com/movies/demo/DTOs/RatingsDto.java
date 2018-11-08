package com.movies.demo.DTOs;

import com.movies.demo.domain.Title;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

@Setter
@Getter
@NoArgsConstructor
public class RatingsDto {
    private Long id;
    //private Title title;
    private Double averageRating;
    private Integer numVotes;
}