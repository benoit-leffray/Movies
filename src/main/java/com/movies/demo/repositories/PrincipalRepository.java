package com.movies.demo.repositories;

import com.movies.demo.domain.Principal;
import com.movies.demo.moviebygenre.NbMovieByGenre;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;

public interface PrincipalRepository extends CrudRepository<Principal, Long> {
    @Query(
            value =
                    "SELECT EXISTS ( " +
                        "SELECT COUNT(*) " +
                        "FROM Principal p " +
                        "JOIN Person pers " +
                        "ON p.Person_ID = pers.ID " +
                        "JOIN Title t " +
                        "ON t.ID = p.Title_ID " +
                        "WHERE pers.Primary_Name = :name " +
                        "GROUP BY t.genres " +
                        "HAVING COUNT(*) > (" +
                                "SELECT " +
                                "COUNT(*) " +
                                "FROM Principal p " +
                                "JOIN Person pers " +
                                "ON p.Person_ID = pers.ID " +
                                "JOIN Title t " +
                                "ON t.ID = p.Title_ID " +
                                "WHERE pers.Primary_Name = :name ) / 2)",
            nativeQuery = true
    )
    Boolean findTypeCasted(@Param("name") String name);
}