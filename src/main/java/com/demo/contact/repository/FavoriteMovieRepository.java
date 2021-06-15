package com.demo.contact.repository;

import com.demo.contact.domain.FavoriteMovie;
import org.springframework.data.repository.CrudRepository;

public interface FavoriteMovieRepository extends CrudRepository<FavoriteMovie, Long> {

}
