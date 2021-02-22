package com.spring.services.movieService;

import com.spring.exceptions.BlockbusterDoesntExistsException;
import com.spring.model.Employee;
import com.spring.model.Movie;
import com.spring.repository.MovieRepository;
import com.spring.service.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;
public class MovieServiceGetAllTest {

    @Mock
    MovieRepository movieRepository;

    @InjectMocks
    MovieService movieService;

    List<Movie> movieList;

    @BeforeEach
    public void setUp(){ openMocks(this);}

    //camino feliz sin parametros devolviendo Peliculas

    @Test
    void getAllOkTest() throws BlockbusterDoesntExistsException {
        movieList = Collections.emptyList();
        when(movieRepository.findAll()).thenReturn(movieList);
        List<Movie> movieListResult = movieService.getAll(null);
        verify(movieRepository, times(1)).findAll();
    }

    //Camino feliz poniendo titulo y encontrado peliculas
    @Test
    void getMovieByFirstNameOkTest()throws BlockbusterDoesntExistsException{
        movieList = new ArrayList<>();
        movieList.add(mock(Movie.class));
        when(movieRepository.findByTitle("ET")).thenReturn(movieList);
        List<Movie> movieListResult = movieService.getAll("ET");
        verify(movieRepository, times(1)).findByTitle("ET");
        assertEquals(movieList, movieListResult);
    }

    //Camino infeliz poniendo titulo pero sin encontrar peliculas
    @Test
    void getMovieByFirstNameThrowBlockBusterDoesntExistsExceptionTest() throws BlockbusterDoesntExistsException{
        movieList = Collections.emptyList();
        when(movieRepository.findByTitle("ET")).thenReturn(movieList);
        assertThrows(BlockbusterDoesntExistsException.class,() -> {
            movieService.getAll("ET");
        });
    }
}
