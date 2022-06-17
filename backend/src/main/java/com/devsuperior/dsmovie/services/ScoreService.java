package com.devsuperior.dsmovie.services;

import com.devsuperior.dsmovie.dto.MovieDTO;
import com.devsuperior.dsmovie.dto.ScoreDTO;
import com.devsuperior.dsmovie.entities.Movie;
import com.devsuperior.dsmovie.entities.Score;
import com.devsuperior.dsmovie.entities.User;
import com.devsuperior.dsmovie.repositories.MovieRepository;
import com.devsuperior.dsmovie.repositories.ScoreRepository;
import com.devsuperior.dsmovie.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ScoreService {

    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ScoreRepository scoreRepository;

    @Transactional
    public MovieDTO saveScore(ScoreDTO scoreDTO) {
        User user = userRepository.findByEmail(scoreDTO.getEmail());
        if (user == null) {
            user = new User(scoreDTO.getEmail());
            user = userRepository.saveAndFlush(user);
        }

        Movie movie = movieRepository.findById(scoreDTO.getMovieId()).get();

        Score score = new Score();

        score.setMovie(movie);
        score.setUser(user);
        score.setValue(scoreDTO.getScore());

        score = scoreRepository.saveAndFlush(score);

        double sum = 0;
        for (Score s : movie.getScores()) {
            sum += s.getValue();
        }
        int countScores = movie.getScores().size();
        double avg = sum / countScores;
        avg = Math.round(avg * 100.0) / 100.0;

        movie.setScore(avg);
        movie.setCount(countScores);

        movieRepository.save(movie);

        return new MovieDTO(movie);
    }
}
