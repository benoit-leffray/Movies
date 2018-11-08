package com.movies.demo.moviebygenre;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class NbMovieByGenre {
    private long nbMovies;

    public NbMovieByGenre(long nbMovies) {
        this.nbMovies = nbMovies;
    }
}