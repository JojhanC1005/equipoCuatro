package com.example.pico_botella_grupo4.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pico_botella_grupo4.repository.ChallengeRepository

class HomeViewModelFactory(
    private val repository: ChallengeRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {

        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {

            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(repository) as T

        }

        throw IllegalArgumentException(
            "Unknown ViewModel class"
        )
    }
}