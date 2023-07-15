package com.pokemonreview.api.repository;

import com.pokemonreview.api.models.Review;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class ReviewRepositoryTest {


    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewRepositoryTest(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }


    @Test
    public void ReviewRepository_SaveAll_ReturnsReview() {
        Review review = Review.builder().title("title").content("content").build();

        Review reviewSave = reviewRepository.save(review);
        Assertions.assertThat(reviewSave).isNotNull();
        Assertions.assertThat(reviewSave.getId()).isGreaterThan(0);
    }

    @Test
    public void ReviewRepository_GetAll_ReturnsMoreThenOneReview() {
        Review review1 = Review.builder().title("title").content("content").build();
        Review review2 = Review.builder().title("title").content("content").build();
        Review reviewSave1 = reviewRepository.save(review1);
        Review reviewSave2 = reviewRepository.save(review2);
        List<Review> reviewList = reviewRepository.findAll();
        Assertions.assertThat(reviewList).isNotNull();
        Assertions.assertThat(reviewList.size()).isEqualTo(2);
    }

    @Test
    public void ReviewRepository_FindById_ReturnsReview() {
        Review review = Review.builder().title("title").content("content").build();

        Review reviewSave = reviewRepository.save(review);
        Review reviewReturn = reviewRepository.findById(review.getId()).get();

        Assertions.assertThat(reviewReturn).isNotNull();
    }


    @Test
    public void ReviewRepository_UpdateReview_ReturnReview() {
        Review review = Review.builder().title("title").content("content").build();

        Review reviewSave = reviewRepository.save(review);
        reviewSave.setTitle("title");
        reviewSave.setContent("content");
        Review updatedPokemon = reviewRepository.save(reviewSave);

        Assertions.assertThat(updatedPokemon.getTitle()).isNotNull();
        Assertions.assertThat(updatedPokemon.getContent()).isNotNull();
    }



    @Test
    public void ReviewRepository_PokemonDelete_ReturnPokemonIsEmpty() {
        Review review = Review.builder().title("title").content("content").build();
        reviewRepository.save(review);
        reviewRepository.deleteById(review.getId());
        Optional<Review> reviewReturn = reviewRepository.findById(review.getId());
        Assertions.assertThat(reviewReturn).isNotNull();
    }
}