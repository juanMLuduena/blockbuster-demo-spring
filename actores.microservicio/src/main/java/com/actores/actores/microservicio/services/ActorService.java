package com.actores.actores.microservicio.services;

import com.actores.actores.microservicio.models.Actor;
import com.actores.actores.microservicio.models.Movie;
import com.actores.actores.microservicio.repositories.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;

@Service
public class ActorService {

    private final ActorRepository actorRepository;
    private final RestTemplate restClient;

    @Autowired
    public ActorService(ActorRepository actorRepository, RestTemplate restClient) {
        this.actorRepository = actorRepository;
        this.restClient = restClient;
    }

    public Movie getMovieByTitle(String title){
        Map<String, String> pathVariables = new HashMap<String, String>();
        pathVariables.put("title", title);
        Movie movie = restClient.getForObject("http://localhost:8080/movie/title/single/{title}",Movie.class,pathVariables);
        return movie;
    }

    public List<Actor> getAll() {
        return actorRepository.findAll();
    }

    public void addActor(Actor actor) throws IllegalArgumentException{
        if(isNull(actor.getAge())
                || isNull(actor.getFirstname())
                || isNull(actor.getLastname()))
            throw new IllegalArgumentException("Los datos no pueden estar vacios");
        try {
            actorRepository.save(actor);
        }catch(Exception e){
            e.getMessage();
        }
    }

    public void addMovieToActor(Integer idActor, String movieTitle) {
        Integer idMovie = this.getMovieByTitle(movieTitle).getId();
        actorRepository.updateMovie(idActor,idMovie);
    }
}
