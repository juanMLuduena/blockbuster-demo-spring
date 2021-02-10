package com.spring.controller;

import com.spring.exceptions.MovieAlreadyRented;
import com.spring.model.Movie;
import com.spring.service.MovieService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public void addMovie(@RequestBody Movie newMovie){
        movieService.addMovie(newMovie);
    }

    @GetMapping("/")
    public List<Movie> getAll(@RequestParam(required = false)String title){
        return movieService.getAll(title);
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<List<Movie>> getByTitle(@PathVariable String title){
        return ResponseEntity.ok(movieService.getByTitle(title));
    }

    @GetMapping("/title/simple/{title}")
    public ResponseEntity<Movie> getByTitleOneMovie(@PathVariable String title){
        return ResponseEntity.ok(movieService.getByTitleSingle(title));
    }

    @PutMapping("/rented")
    public ResponseEntity rentMovie(Integer idMovie) throws MovieAlreadyRented {
        ResponseEntity response = null;
        try {
            movieService.rentMovie(movieService.findById(idMovie));
            response = ResponseEntity.status(HttpStatus.OK).body("La renta se inicio con éxito");
            log.log(Level.FINE, "Modificación en Movie exitosa");
        }catch (MovieAlreadyRented e){
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La renta que desea iniciar ya se encontraba activa");
            log.log(Level.FINE, "Modificación en movie fallida");
        }
        return response;
    }

    @PutMapping("/unRented")
    public ResponseEntity unRentMovie(Integer idMovie)  {
        ResponseEntity response = null;
        try {
            movieService.unRentMovie(movieService.findById(idMovie));
            response = ResponseEntity.status(HttpStatus.OK).body("La renta se finalizo con éxito");
            log.log(Level.FINE, "Modificación en Movie exitosa");
        }catch (MovieAlreadyRented e){
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La renta que desea finalizar no se encuentra activa");
            log.log(Level.FINE, "Modificación en movie fallida");
        }
        return response;
    }


}
