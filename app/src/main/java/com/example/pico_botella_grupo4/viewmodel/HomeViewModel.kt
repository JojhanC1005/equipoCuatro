package com.example.pico_botella_grupo4.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel () {
    private val _soundEnabled =
        MutableLiveData(true)

    val soundEnabled: LiveData<Boolean>
        get() = _soundEnabled


    fun toggleSound() {

        _soundEnabled.value =
            !(_soundEnabled.value ?: true)

    }
}