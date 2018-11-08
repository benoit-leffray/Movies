package com.movies.demo.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@EqualsAndHashCode(exclude = {"principals", "ratings", "knownForTitles"})
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"principals", "ratings", "knownForTitles"})
public class Title implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type", nullable = false, length = 16)
    private String type;

    @Column(name = "primary_title", nullable = false, length = 450)
    private String primaryTitle;

    @Column(name = "original_title", nullable = false, length = 450)
    private String originalTitle;

    private Boolean isAdult;
    private Integer startYear;
    private Integer endYear;
    private Integer runtimeMinutes;

    @Column(name = "genres", nullable = false, length = 64)
    private String genres;

    @OneToMany(mappedBy = "title", cascade = CascadeType.ALL, fetch= FetchType.EAGER)
    private Set<Principal> principals = new HashSet<>();

    @ManyToMany(mappedBy = "knownForTitles")
    private Set<Person> knownForTitles = new HashSet<>();

    @OneToOne(mappedBy = "title", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Ratings ratings;

    public Title(String type, String primaryTitle, String originalTitle, Boolean isAdult, Integer startYear, Integer endYear, Integer runtimeMinutes, String genres) {
        this.type = type;
        this.primaryTitle = primaryTitle;
        this.originalTitle = originalTitle;
        this.isAdult = isAdult;
        this.startYear = startYear;
        this.endYear = endYear;
        this.runtimeMinutes = runtimeMinutes;
        this.genres = genres;
    }

    public void addPrincipal(Principal principal) {
        if (principal != null) {
            principals.add(principal);
            principal.setTitle(this);
        }
    }
/*
    public void setRatings(Ratings ratings) {
        if (ratings != null) {
            ratings.setTitle(this);
            this.ratings = ratings;
        }
    }*/
}
