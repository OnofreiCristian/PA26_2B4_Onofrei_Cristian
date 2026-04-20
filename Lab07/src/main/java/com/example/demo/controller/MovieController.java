package com.example.demo.controller;

import com.example.demo.entity.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Score;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.repository.MovieRepository;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping
    public List<Movie> getMovies() {
        return movieRepository.findAll();
    }

    @PostMapping
    public Movie addMovie(@RequestBody Movie movie) {
        return movieRepository.save(movie);
    }

    @PutMapping("/{id}")
    public Movie updateMovie(@PathVariable int id, @RequestBody Movie movieDetails) {
        Movie movie = movieRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Movie not found"));
        movie.setTitle(movieDetails.getTitle());
        movie.setReleaseDate(movieDetails.getReleaseDate());
        movie.setDuration(movieDetails.getDuration());
        return movieRepository.save(movie);
    }

    @PatchMapping("/{id}/score")
    public Movie updateScore(@PathVariable int id, @RequestBody Float score) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found"));
        movie.setScore(score);
        return movieRepository.save(movie);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMovie(@PathVariable int id) {
        movieRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
