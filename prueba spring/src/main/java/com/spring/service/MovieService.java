package com.spring.service;

import com.spring.exceptions.MovieAlreadyRented;
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

    public Movie findByTitleAndRented(String title, boolean b) {
        List<Movie> movies = null;
        movies = movieRepository.findByTitleAndRented(title,b);
        return (movies.isEmpty()) ? null : movies.get(0);
    }


    //este metodo no hace ningun control xq se asume q el dato recibido ya fue checkeado con anterioridad
    public void rentMovie(Movie movie) throws MovieAlreadyRented {
        if(movie.getRented()) throw new MovieAlreadyRented("La pelicula que quiere rentar ya fue rentada");
        movieRepository.rentMovie(movie.getId());
    }

    public void unRentMovie(Movie movie) throws MovieAlreadyRented {
        if(!movie.getRented()) throw new MovieAlreadyRented("La pelicula no cuenta con una renta activa");
        movieRepository.unRentMovie(movie.getId());
    }

    public Movie findById(Integer idMovie) {
        return movieRepository.findById(idMovie).orElseThrow();
    }

    public Movie getByTitleSingle(String title) {
        List<Movie> movies = movieRepository.findByTitle(title);
        return (movies.isEmpty()) ? null : movies.get(0);
    }
}
