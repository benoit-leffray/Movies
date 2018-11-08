package com.movies.demo.repositories;

import com.movies.demo.domain.Ratings;
import org.springframework.data.repository.CrudRepository;

public interface RatingsRepository extends CrudRepository<Ratings, Long> {
}