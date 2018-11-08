package com.movies.demo.repositories;

import com.movies.demo.domain.Title;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface TitleRepository extends CrudRepository<Title, Long> {
    Title findOneByPrimaryTitleOrOriginalTitle(String primaryTitle, String originalTitle);

    Iterable<Title> findAllByGenresOrderByRatings_AverageRatingDesc(String genre);

    @Query(
            value =
                "SELECT * FROM title t " +
                "INNER JOIN person_title pt1 ON pt1.title_id = t.id " +
                "INNER JOIN person_title pt2 ON pt1.person_id <> pt2.person_id AND pt1.title_id = pt2.title_id " +

                "INNER JOIN person p1 ON p1.id = pt1.person_id " +
                "INNER JOIN person p2 ON p2.id = pt2.person_id " +

                "WHERE p1.primary_name = :personName1 AND p2.primary_name = :personName2",

            nativeQuery = true
    )
    Iterable<Title> findAllByPersonCoincidence(@Param("personName1") String personName1,
                                               @Param("personName2") String personName2);
}