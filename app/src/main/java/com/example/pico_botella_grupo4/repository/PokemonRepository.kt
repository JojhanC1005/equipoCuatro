package com.example.pico_botella_grupo4.repository
import com.example.pico_botella_grupo4.model.Pokemon
import com.example.pico_botella_grupo4.webservice.ApiService
import com.example.pico_botella_grupo4.webservice.ApiUtils

class PokemonRepository{
    private var apiService: ApiService = ApiUtils.getApiService()

    suspend fun randomPokemon(): Pokemon {
        val lista = apiService.getPokemons()
        return lista.random()
    }
}