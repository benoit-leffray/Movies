package com.movies.demo.domain;

import lombok.*;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@EqualsAndHashCode(exclude = {"knownForTitles"})
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "knownForTitles")
public class Person implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "primary_name", nullable = false, length = 128)
    private String primaryName;

    @Column(name = "birth_year", nullable = true)
    private Integer birthYear;

    @Column(name = "death_year", nullable = true)
    private Integer deathYear;

    @ManyToMany
    @JoinTable(name = "person_title",
               joinColumns = @JoinColumn(name = "person_id"),
               inverseJoinColumns = @JoinColumn(name = "title_id"))
    private Set<Title> knownForTitles = new HashSet<>();

    public Person(String primaryName, Integer birthYear, Integer deathYear) {
        this.primaryName = primaryName;
        this.birthYear = birthYear;
        this.deathYear = deathYear;
    }
}