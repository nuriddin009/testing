package com.pokemonreview.api.service;

import com.pokemonreview.api.dto.PokemonDto;
import com.pokemonreview.api.dto.ReviewDto;
import com.pokemonreview.api.models.Pokemon;
import com.pokemonreview.api.models.Review;
import com.pokemonreview.api.repository.PokemonRepository;
import com.pokemonreview.api.repository.ReviewRepository;
import com.pokemonreview.api.service.impl.ReviewServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private PokemonRepository pokemonRepository;

    @InjectMocks
    private ReviewServiceImpl reviewService;

    private Pokemon pokemon;
    private Review review;
    private ReviewDto reviewDto;
    private PokemonDto pokemonDto;


    @BeforeEach
    public void init() {
        pokemon = Pokemon.builder().name("pikachu").type("electric").build();
        pokemonDto = PokemonDto.builder().name("pickachu").type("electric").build();
        review = Review.builder().title("title").content("content").build();
        reviewDto = ReviewDto.builder().content("content").title("title").stars(5).build();
    }

    @Test
    void createReview() {
        when(pokemonRepository.findById(pokemon.getId())).thenReturn(Optional.of(pokemon));
        when(reviewRepository.save(Mockito.any(Review.class))).thenReturn(review);
        ReviewDto saveReview = reviewService.createReview(pokemon.getId(), reviewDto);
        Assertions.assertThat(saveReview).isNotNull();
    }

    @Test
    void getReviewsByPokemonId() {
        int reviewId = 1;
        when(reviewRepository.findByPokemonId(reviewId)).thenReturn(Collections.singletonList(review));
        List<ReviewDto> reviewsByPokemonId = reviewService.getReviewsByPokemonId(reviewId);
        Assertions.assertThat(reviewsByPokemonId).isNotNull();
    }

    @Test
    void getReviewById() {
        int reviewId = 1;
        int pokemonId = 1;

        review.setPokemon(pokemon);

        when(pokemonRepository.findById(pokemonId)).thenReturn(Optional.of(pokemon));
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));
        ReviewDto reviewById = reviewService.getReviewById(reviewId, pokemonId);

        Assertions.assertThat(reviewById).isNotNull();
    }

    @Test
    void updateReview() {
        int pokemonId = 1;
        int reviewId = 1;

        pokemon.setReviews(Collections.singletonList(review));
        review.setPokemon(pokemon);
        when(pokemonRepository.findById(pokemonId)).thenReturn(Optional.of(pokemon));
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));

        when(reviewRepository.save(review)).thenReturn(review);

        ReviewDto updateReview = reviewService.updateReview(pokemonId, reviewId, reviewDto);

        Assertions.assertThat(updateReview).isNotNull();
    }

    @Test
    void deleteReview() {
        int pokemonId = 1;
        int reviewId = 1;

        pokemon.setReviews(Collections.singletonList(review));
        review.setPokemon(pokemon);

        when(pokemonRepository.findById(pokemonId)).thenReturn(Optional.of(pokemon));
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));

        assertAll(() -> reviewService.deleteReview(pokemonId, reviewId));
    }
}