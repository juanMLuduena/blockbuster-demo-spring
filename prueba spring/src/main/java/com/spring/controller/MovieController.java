package com.spring.controller;

import com.spring.model.Movie;
import com.spring.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/title")
    public ResponseEntity<List<Movie>> getByTitle(@RequestParam String title){
        return ResponseEntity.ok(movieService.getByTitle(title));
    }


}
