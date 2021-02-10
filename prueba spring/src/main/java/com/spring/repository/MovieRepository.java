package com.spring.repository;

import com.spring.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie,Integer> {
    List<Movie> findByTitle(String title);

    List<Movie> findByTitleAndRented(String title, boolean b);

    @Modifying
    @Transactional
    @Query(value = "UPDATE movies SET rented = true WHERE id = ?1" , nativeQuery = true)
    void rentMovie(Integer id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE movies SET rented = false WHERE id = ?1" , nativeQuery = true)
    void unRentMovie(Integer id);
}
