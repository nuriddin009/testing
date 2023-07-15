package com.pokemonreview.api.repository;


import com.pokemonreview.api.models.Pokemon;
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
public class PokemonRepositoryTest {

    @Autowired
    private PokemonRepository pokemonRepository;

    @Test
    public void PokemonRepository_SaveAll_ReturnSavedPokemon() {

        Pokemon pokemon = Pokemon.builder()
                .name("pikachu")
                .type("electric")
                .build();

        Pokemon pokemonSaved = pokemonRepository.save(pokemon);

        Assertions.assertThat(pokemonSaved).isNotNull();
        Assertions.assertThat(pokemonSaved.getId()).isGreaterThan(0);
    }

    @Test
    public void PokemonRepository_GetAll_ReturnMoreThanPokemon() {
        Pokemon pokemon = Pokemon.builder()
                .name("pikachu")
                .type("electric")
                .build();
        Pokemon pokemon2 = Pokemon.builder()
                .name("pikachu")
                .type("electric")
                .build();

        pokemonRepository.save(pokemon);
        pokemonRepository.save(pokemon2);

        List<Pokemon> pokemonList = pokemonRepository.findAll();

        Assertions.assertThat(pokemonList).isNotNull();
        Assertions.assertThat(pokemonList.size()).isEqualTo(2);
    }

    @Test
    public void PokemonRepository_FindById_ReturnPokemon() {
        Pokemon pokemon = Pokemon.builder()
                .name("pikachu")
                .type("electric")
                .build();
        pokemonRepository.save(pokemon);
        Pokemon pokemonSave = pokemonRepository.findById(pokemon.getId()).get();
        Assertions.assertThat(pokemonSave).isNotNull();
    }

    @Test
    public void PokemonRepository_FindByType_ReturnPokemonNotNull() {
        Pokemon pokemon = Pokemon.builder()
                .name("pikachu")
                .type("electric")
                .build();
        pokemonRepository.save(pokemon);


        Pokemon pokemonSave = pokemonRepository.findByType(pokemon.getType()).get();
        Assertions.assertThat(pokemonSave).isNotNull();
    }


    @Test
    public void PokemonRepository_UpdatePokemon_ReturnPokemonNotNull() {
        Pokemon pokemon = Pokemon.builder()
                .name("pikachu")
                .type("electric")
                .build();
        pokemonRepository.save(pokemon);


        Pokemon pokemonSave = pokemonRepository.findById(pokemon.getId()).get();
        pokemonSave.setType("Electric");
        pokemonSave.setName("Raichu");

        Pokemon updatedPokemon = pokemonRepository.save(pokemonSave);

        Assertions.assertThat(updatedPokemon.getName()).isNotNull();
        Assertions.assertThat(updatedPokemon.getType()).isNotNull();
    }

    @Test
    public void PokemonRepository_PokemonDelete_ReturnPokemonIsEmpty() {
        Pokemon pokemon = Pokemon.builder()
                .name("pikachu")
                .type("electric")
                .build();
        pokemonRepository.save(pokemon);

        pokemonRepository.deleteById(pokemon.getId());
        Optional<Pokemon> pokemonReturn = pokemonRepository.findById(pokemon.getId());
        Assertions.assertThat(pokemonReturn).isNotNull();
    }


}
