package com.movies.demo.services;

import com.movies.demo.repositories.PrincipalRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class PrincipalServiceImplTest {

    PrincipalServiceImpl principalService;

    @Mock
    PrincipalRepository principalRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        principalService = new PrincipalServiceImpl(principalRepository);
    }

    @Test
    public void getTypeCasted() throws Exception {

        when(principalRepository.findTypeCasted(anyString())).thenReturn(true);

        boolean typeCasted = principalService.findTypeCasted("who ever");

        assertTrue("Typecasted expected", typeCasted);
    }

    @Test
    public void getNotTypeCasted() throws Exception {

        when(principalRepository.findTypeCasted(anyString())).thenReturn(false);

        boolean typeCasted = principalService.findTypeCasted("who ever");

        assertFalse("Typecasted expected", typeCasted);
    }
}