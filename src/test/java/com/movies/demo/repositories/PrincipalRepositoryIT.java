package com.movies.demo.repositories;

import com.movies.demo.domain.Principal;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PrincipalRepositoryIT {

    @Autowired
    PrincipalRepository principalRepository;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void findTypeCasted() throws Exception {

        boolean typecasted = principalRepository.findTypeCasted("Primary Name");

        assertEquals(true, typecasted);

        typecasted = principalRepository.findTypeCasted("doesn't exist");

        assertEquals(false, typecasted);
    }
}