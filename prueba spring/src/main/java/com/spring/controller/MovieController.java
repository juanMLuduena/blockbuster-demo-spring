package com.spring.controller;

import com.spring.exceptions.BlockbusterAlreadyExistsException;
import com.spring.exceptions.BlockbusterDoesntExistsException;
import com.spring.model.Movie;
import com.spring.service.MovieService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movie")
public class MovieController {
    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping("/")
    public void addMovie(@RequestBody Movie newMovie) {
        movieService.addMovie(newMovie);
    }

    @GetMapping("/")
    public ResponseEntity<Object> getAll(@RequestParam(required = false) String title) throws BlockbusterDoesntExistsException {
        return ResponseEntity.ok(movieService.getAll(title));
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<Object> getByTitle(@PathVariable String title) throws BlockbusterDoesntExistsException {
        return ResponseEntity.ok(movieService.getAll(title));
    }

    @GetMapping("/title/single/{title}")
    public ResponseEntity<Object> getByTitleOneMovie(@PathVariable String title) throws BlockbusterDoesntExistsException {
        return ResponseEntity.ok(movieService.getByTitleSingle(title));
    }

    @PutMapping("/rented")
    public ResponseEntity<Object> rentMovie(Integer idMovie) throws BlockbusterDoesntExistsException, BlockbusterAlreadyExistsException {
        movieService.rentMovie(movieService.findById(idMovie));
        return ResponseEntity.status(HttpStatus.OK).body("la pelicula se rento con exito");
    }

    @PutMapping("/unRented")
    public ResponseEntity<Object> unRentMovie(Integer idMovie) throws BlockbusterDoesntExistsException, BlockbusterAlreadyExistsException {
        movieService.unRentMovie(movieService.findById(idMovie));
        return ResponseEntity.status(HttpStatus.OK).body("La renta se finalizo con éxito");
    }

    @GetMapping("/test/{dni}")
    public ResponseEntity<Object> testMovieTitle(@PathVariable String dni) {
        return ResponseEntity.ok(movieService.getMovieTitlesByClientDni(dni));
    }

}
