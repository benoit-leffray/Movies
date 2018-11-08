package com.movies.demo.domain;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@ToString(exclude = "title")
@EqualsAndHashCode(exclude = "title")
@NoArgsConstructor
@AllArgsConstructor
public class Ratings implements Serializable {
    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Title title;

    private Double averageRating;
    private Integer numVotes;

    public Ratings(Title title, Double averageRating, Integer numVotes) {
        if (title != null) {
            this.id = title.getId();
            this.title = title;
            title.setRatings(this);
        }

        this.averageRating = averageRating;
        this.numVotes = numVotes;
    }
}
