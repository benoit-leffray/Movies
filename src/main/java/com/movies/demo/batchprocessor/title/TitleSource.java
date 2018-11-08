package com.movies.demo.batchprocessor.title;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class TitleSource {
    private String id;
    private String type;
    private String primaryTitle;
    private String originalTitle;
    private String isAdult;
    private String startYear;
    private String endYear;
    private String runtimeMinutes;
    private String genres;
}