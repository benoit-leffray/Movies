package com.movies.demo.DTOs;

import com.movies.demo.domain.Person;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class PrincipalDto {
    private Long id;
    private Integer ordering;
    private Person person;
    private String category;
    private String job;
    private String characters;
}