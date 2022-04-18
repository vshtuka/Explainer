package com.vladshtuka.explainer.presentation.screen_home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vladshtuka.explainer.domain.usecase.dictionary.DictionaryUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dictionaryUseCases: DictionaryUseCases
) : ViewModel() {

    fun removeDictionary() {
        viewModelScope.launch {
            dictionaryUseCases.removeDictionaryUseCase()
        }
    }

}