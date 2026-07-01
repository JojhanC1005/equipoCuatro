package com.example.pico_botella_grupo4.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pico_botella_grupo4.model.Challenge
import com.example.pico_botella_grupo4.repository.ChallengeRepository
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: ChallengeRepository
) : ViewModel() {

    private val _soundEnabled =
        MutableLiveData(true)

    val soundEnabled: LiveData<Boolean>
        get() = _soundEnabled

    private val _randomChallenge =
        MutableLiveData<Challenge?>()

    val randomChallenge: LiveData<Challenge?>
        get() = _randomChallenge

    fun toggleSound() {
        _soundEnabled.value =
            !(_soundEnabled.value ?: true)
    }

    fun loadRandomChallenge() {
        viewModelScope.launch {
            _randomChallenge.value =
                repository.getRandomChallenge()
        }
    }
}