package com.actores.actores.microservicio.repositories;

import com.actores.actores.microservicio.models.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface ActorRepository extends JpaRepository<Actor,Integer> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE actors SET id_movie = ?2 WHERE id = ?1" , nativeQuery = true)
    void updateMovie(Integer idActor, Integer idMovie);
}
