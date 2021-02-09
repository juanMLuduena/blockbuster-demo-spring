package com.spring.service;

import com.spring.model.Movie;
import com.spring.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.isNull;

@Service
public class MovieService {
    private final MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }


    public void addMovie(Movie newMovie) {
        movieRepository.save(newMovie);
    }

    public List<Movie> getAll(String title) {
        if (isNull(title)) {
            return movieRepository.findAll();
        } else {
            return movieRepository.findByTitle(title);

        }
    }

    public List<Movie> getByTitle(String title) {
        return movieRepository.findByTitle(title);
    }
}
