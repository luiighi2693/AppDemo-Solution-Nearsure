package com.demo.contact.domain;

import org.springframework.hateoas.ResourceSupport;

import javax.persistence.*;

@Entity
public class FavoriteMovie extends ResourceSupport {

    @Id
    @GeneratedValue
    private Long favoriteMovieId;
    private Integer score;
    private String observations;

    @ManyToOne
    @JoinColumn(name = "contact_id")
    Contact contact;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    Movie movie;

    public FavoriteMovie() {
    }

    public FavoriteMovie(Integer score, String observations, Movie movie, Contact contact) {
        this.score = score;
        this.observations = observations;
        this.movie = movie;
        this.contact = contact;
    }

    public Long getFavoriteMovieId() {
        return favoriteMovieId;
    }

    public void setFavoriteMovieId(Long favoriteMovieId) {
        this.favoriteMovieId = favoriteMovieId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }
}
