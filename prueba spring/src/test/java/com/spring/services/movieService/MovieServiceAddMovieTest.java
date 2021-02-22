package com.spring.services.movieService;

import com.spring.exceptions.BlockbusterAlreadyExistsException;
import com.spring.model.Employee;
import com.spring.model.Movie;
import com.spring.repository.MovieRepository;
import com.spring.service.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;
public class MovieServiceAddMovieTest {

    @Mock
    MovieRepository movieRepository;

    @InjectMocks
    MovieService movieService;

    @BeforeEach
    public void setUp(){ openMocks(this);}

    //Camino feliz se guarda la nueva pelicula
    @Test
    void addMovieOkTest()  {
        Movie movie = new Movie(5,"ET",true, new ArrayList<>());
        when(movieRepository.save(movie)).thenReturn(movie);
        Movie movieResult = movieService.addMovie(movie);
        verify(movieRepository, times(1)).save(movie);
        assertEquals(movie, movieResult);
    }

    //Camino infeliz el titulo es nulo tira excepcion
    @Test
    void addEmployeeNullDniThrowIllegalArgumentExceptionTest() {
        Movie movie = mock(Movie.class);
        assertThrows(IllegalArgumentException.class, () -> {
            movieService.addMovie(movie);
        });
    }

}
