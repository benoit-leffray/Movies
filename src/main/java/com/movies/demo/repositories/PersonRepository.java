package com.movies.demo.repositories;

import com.movies.demo.domain.Person;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;


public interface PersonRepository extends CrudRepository<Person, Long> {
}