package com.example.pico_botella_grupo4.webservice

import com.example.pico_botella_grupo4.model.Pokemon
import com.example.pico_botella_grupo4.model.PokemonResponse
import com.example.pico_botella_grupo4.utils.Constants.END_POINT
import retrofit2.http.GET

interface ApiService {
    @GET(END_POINT)
    suspend fun getPokemons(): PokemonResponse
}