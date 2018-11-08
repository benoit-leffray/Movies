package com.movies.demo.services;

import com.movies.demo.repositories.PrincipalRepository;
import org.springframework.stereotype.Service;

@Service
public class PrincipalServiceImpl implements PrincipalService {

    private final PrincipalRepository principalRepository;

    public PrincipalServiceImpl(PrincipalRepository principalRepository) {
        this.principalRepository = principalRepository;
    }

    @Override
    public boolean findTypeCasted(String name) {
        return principalRepository.findTypeCasted(name);
    }
}