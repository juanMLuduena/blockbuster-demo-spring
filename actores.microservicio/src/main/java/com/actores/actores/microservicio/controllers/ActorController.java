package com.actores.actores.microservicio.controllers;

import com.actores.actores.microservicio.models.Actor;
import com.actores.actores.microservicio.models.Movie;
import com.actores.actores.microservicio.services.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/actor")
public class ActorController {
    private final ActorService actorService;

    @Autowired
    public ActorController(ActorService actorService) {
        this.actorService = actorService;
    }

    @GetMapping("/")
    public List<Actor> getAll(){
        return actorService.getAll();
    }

    @PostMapping("/")
    public ResponseEntity addActor(@RequestBody Actor actor){
        ResponseEntity response = null;
        try{
            actorService.addActor(actor);
            response = ResponseEntity.status(HttpStatus.CREATED).body("El actor se creo correctamente");
        }catch(IllegalArgumentException iae){
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error en la carga, los datos no pueden ser vacios");
        }catch(Exception e){
            response = ResponseEntity.status(500).body("Error interno desconocido");
        }
        return response;
    }
    @PutMapping("/movie")
    public ResponseEntity addMovieToActor(@RequestParam Integer idActor, @RequestParam String movieTitle){
        ResponseEntity response = null;

            actorService.addMovieToActor(idActor,movieTitle);
            response = ResponseEntity.status(HttpStatus.OK).body("La pelicula se agrego correctamente");

        return response;
    }

    @GetMapping("/movie/{title}")
    public Movie getMovieByTitle(@PathVariable String title){
        return actorService.getMovieByTitle(title);
    }
}
