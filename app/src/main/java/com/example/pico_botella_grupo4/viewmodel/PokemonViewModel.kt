package com.example.pico_botella_grupo4.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pico_botella_grupo4.model.Pokemon
import com.example.pico_botella_grupo4.repository.PokemonRepository
import kotlinx.coroutines.launch

class PokemonViewModel : ViewModel() {

    private val repository = PokemonRepository()

    private val _pokemon = MutableLiveData<Pokemon>()

    val pokemon: LiveData<Pokemon>
        get() = _pokemon

    fun getRandomPokemon() {

        viewModelScope.launch {

            _pokemon.value = repository.randomPokemon()

        }

    }

}