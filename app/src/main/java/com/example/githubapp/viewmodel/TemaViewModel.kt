package com.example.githubapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.githubapp.tema.TemaManager
import kotlinx.coroutines.launch

class TemaViewModel(private val pref: TemaManager): ViewModel() {

    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isLightModeActive: Boolean) {
        viewModelScope.launch {
            pref.saveThemeSetting(isLightModeActive)
        }
    }
}