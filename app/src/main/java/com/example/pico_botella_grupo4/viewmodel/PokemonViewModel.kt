package com.example.pico_botella_grupo4.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pico_botella_grupo4.repository.PokemonRepository
import kotlinx.coroutines.launch

class PokemonViewModel : ViewModel() {

    private val repository = PokemonRepository()

    fun getRandomPokemon() {

        viewModelScope.launch {

            repository.randomPokemon()

        }

    }

}