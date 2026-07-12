package com.example.pico_botella_grupo4.repository
import com.example.pico_botella_grupo4.model.Pokemon
import com.example.pico_botella_grupo4.webservice.ApiService
import com.example.pico_botella_grupo4.webservice.ApiUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PokemonRepository{

    private var apiService: ApiService = ApiUtils.getApiService()

    suspend fun randomPokemon(): Pokemon {
        return withContext(Dispatchers.IO) {
            try {
                val lista = apiService.getPokemons()
                lista.random()
            } catch (e: Exception) {
                e.printStackTrace()
                Pokemon(1,"Bulbassaur","http://www.serebii.net/pokemongo/pokemon/001.png")
            }
        }
    }
}