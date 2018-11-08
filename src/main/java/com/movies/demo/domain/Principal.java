package com.movies.demo.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Setter
@Getter
@EqualsAndHashCode(exclude = "title")
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "title")
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"title_id", "ordering"}))
public class Principal implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer ordering;

    @ManyToOne
    @JoinColumn(name = "title_id")
    private Title title;
    
    @OneToOne
    @JoinColumn(name = "person_id")
    private Person person;

    @Column(name = "category", nullable = false, length = 32)
    private String category;

    @Column(name = "job", nullable = false, length = 32)
    private String job;

    @Column(name = "characters", nullable = false, length = 32)
    private String characters;

    public Principal(String category, String characters, String job, Person person) {
        this.category = category;
        this.characters = characters;
        this.job = job;
        this.person = person;
    }
}
