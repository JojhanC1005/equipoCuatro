package com.example.pico_botella_grupo4.viewmodel

import androidx.lifecycle.ViewModel
import com.example.pico_botella_grupo4.model.Pokemon
import com.example.pico_botella_grupo4.repository.PokemonRepository

class PokemonViewModel : ViewModel() {

    private val repository = PokemonRepository()

    suspend fun getRandomPokemon(): Pokemon {

        return repository.randomPokemon()

    }

}