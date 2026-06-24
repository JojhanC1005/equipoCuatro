package com.example.pico_botella_grupo4.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.pico_botella_grupo4.model.Challenge
import com.example.pico_botella_grupo4.repository.ChallengeRepository
import kotlinx.coroutines.launch

class ChallengeViewModel(
    private val repository: ChallengeRepository
) : ViewModel() {

    val challenges =
        repository.getAllChallenges()

    fun insert(description: String) {

        viewModelScope.launch {

            repository.insert(
                Challenge(
                    description = description
                )
            )
        }
    }

    fun update(challenge: Challenge) {

        viewModelScope.launch {
            repository.update(challenge)
        }
    }

    fun delete(challenge: Challenge) {

        viewModelScope.launch {
            repository.delete(challenge)
        }
    }
}