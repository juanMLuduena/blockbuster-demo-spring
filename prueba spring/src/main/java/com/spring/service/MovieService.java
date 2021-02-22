package com.spring.service;

import com.spring.exceptions.BlockbusterAlreadyExistsException;
import com.spring.exceptions.BlockbusterDoesntExistsException;
import com.spring.model.Movie;
import com.spring.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

@Service
public class MovieService {
    private final MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }


    public Movie addMovie(Movie newMovie) {
        if (newMovie.getTitle() == null) throw new IllegalArgumentException("La pelicula que quiere agregar no cuenta con titulo");
        return movieRepository.save(newMovie);
    }

    public List<Movie> getAll(String title) throws BlockbusterDoesntExistsException {
        if (isNull(title)) {
            return movieRepository.findAll();
        }
        List<Movie> moviesByTitle = movieRepository.findByTitle(title);
        if (moviesByTitle.isEmpty())
            throw new BlockbusterDoesntExistsException("No se encontró ninguna película llamada " + title + ".");
        return moviesByTitle;
    }

    public Movie findByTitleAndRented(String title, boolean b) throws BlockbusterDoesntExistsException {
        if (title == null) throw new IllegalArgumentException("No se proporciono un titulo para la búsqueda.");
        List<Movie> movies = null;
        movies = movieRepository.findByTitleAndRented(title, b);
        if (movies.isEmpty()) throw new BlockbusterDoesntExistsException("La película que busca no esta disponible.");
        return movies.get(0);
    }


    //este metodo no hace ningun control xq se asume q el dato recibido ya fue checkeado con anterioridad
    public void rentMovie(Movie movie) throws BlockbusterAlreadyExistsException {
        if (movie.getRented())
            throw new BlockbusterAlreadyExistsException("La película que quiere rentar ya fue rentada");
        movieRepository.rentMovie(movie.getId());
    }

    public void unRentMovie(Movie movie) throws BlockbusterAlreadyExistsException {
        if (!movie.getRented())
            throw new BlockbusterAlreadyExistsException("La película no cuenta con una renta activa");
        movieRepository.unRentMovie(movie.getId());
    }

    public Movie findById(Integer idMovie) throws BlockbusterDoesntExistsException {
        if (idMovie == null) throw new IllegalArgumentException("El ID no puede ser nulo.");
        Optional<Movie> movieById = movieRepository.findById(idMovie);
        if (movieById.isEmpty()) throw new BlockbusterDoesntExistsException("No se pudo encontrar la película.");
        return movieById.get();
    }

    public Movie getByTitleSingle(String title) throws BlockbusterDoesntExistsException {
        if (title == null) throw new IllegalArgumentException("No se proporciono un titulo para la búsqueda.");
        List<Movie> movies = movieRepository.findByTitle(title);
        if(movies.isEmpty()) throw new BlockbusterDoesntExistsException("No se encontró ninguna película llamada " + title + ".");
        return  movies.get(0);
    }

    public List<String> getMovieTitlesByClientDni(String dni) {
        if (dni == null) throw new IllegalArgumentException("El dni no puede ser nulo.");
        return movieRepository.findMovieTitlesByClientDni(dni);
    }
}
