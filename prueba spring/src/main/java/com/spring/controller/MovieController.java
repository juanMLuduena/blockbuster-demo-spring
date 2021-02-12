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

import java.util.logging.Level;

@Log
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
    public ResponseEntity<Object> getAll(@RequestParam(required = false) String title) {
        ResponseEntity<Object> response;
        try {
            response = ResponseEntity.ok(movieService.getAll(title));
            log.log(Level.FINE, "Se listaron las películas de manera exitosa.");
        } catch (BlockbusterDoesntExistsException e) {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            log.log(Level.WARNING, e.getMessage());
        }
        return response;
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<Object> getByTitle(@PathVariable String title) {
        ResponseEntity<Object> response;
        try {
            response = ResponseEntity.ok(movieService.getAll(title));
            log.log(Level.FINE, "Se listaron las películas por titulo de manera exitosa.");
        } catch (BlockbusterDoesntExistsException e) {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            log.log(Level.WARNING, e.getMessage());
        }
        return response;
    }

    @GetMapping("/title/single/{title}")
    public ResponseEntity<Object> getByTitleOneMovie(@PathVariable String title) {
        ResponseEntity<Object> response;
        try {
            response = ResponseEntity.ok(movieService.getByTitleSingle(title));
            log.log(Level.FINE, "Se listaron los clientes de manera exitosa.");
        } catch (BlockbusterDoesntExistsException e) {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            log.log(Level.WARNING, e.getMessage());
        }
        return response;
    }

    @PutMapping("/rented")
    public ResponseEntity<Object> rentMovie(Integer idMovie){
        ResponseEntity<Object> response;
        try {
            movieService.rentMovie(movieService.findById(idMovie));
            response = ResponseEntity.status(HttpStatus.OK).body("La renta se inicio con éxito");
            log.log(Level.FINE, "Modificación en Movie exitosa");
        } catch (BlockbusterAlreadyExistsException | BlockbusterDoesntExistsException e) {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            log.log(Level.WARNING, e.getMessage());
        }
        return response;
    }

    @PutMapping("/unRented")
    public ResponseEntity<Object> unRentMovie(Integer idMovie) {
        ResponseEntity<Object> response;
        try {
            movieService.unRentMovie(movieService.findById(idMovie));
            response = ResponseEntity.status(HttpStatus.OK).body("La renta se finalizo con éxito");
            log.log(Level.FINE, "Modificación en Movie exitosa");
        } catch (BlockbusterAlreadyExistsException | BlockbusterDoesntExistsException e) {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            log.log(Level.WARNING, e.getMessage());
        }
        return response;
    }

    @GetMapping("/test/{dni}")
    public ResponseEntity<Object> testMovieTitle(@PathVariable String dni) {
        return ResponseEntity.ok(movieService.getMovieTitlesByClientDni(dni));
    }

}
