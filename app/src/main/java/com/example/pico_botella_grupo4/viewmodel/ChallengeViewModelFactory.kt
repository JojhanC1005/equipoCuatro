package com.example.pico_botella_grupo4.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pico_botella_grupo4.repository.ChallengeRepository

class ChallengeViewModelFactory(
    private val repository: ChallengeRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {

        if (modelClass.isAssignableFrom(ChallengeViewModel::class.java)) {
            return ChallengeViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}