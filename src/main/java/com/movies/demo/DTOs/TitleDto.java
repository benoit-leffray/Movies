package com.movies.demo.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
public class TitleDto {
    private Long id;

    @NotEmpty
    @NotNull
    private String type;

    @NotEmpty
    @NotNull
    private String primaryTitle;

    @NotEmpty
    @NotNull
    private String originalTitle;

    @NotNull
    private Boolean isAdult;

    private Integer startYear;
    private Integer endYear;
    private Integer runtimeMinutes;

    @NotEmpty
    @NotNull
    private String genres;

    private Set<PrincipalDto> principals = new HashSet<>();

    private RatingsDto ratings;
}
