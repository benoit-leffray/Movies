package com.movies.demo.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
public class PersonDto {
    private Long id;

    @NotEmpty
    @NotNull
    private String primaryName;

    private Integer birthYear;

    private Integer deathYear;
}