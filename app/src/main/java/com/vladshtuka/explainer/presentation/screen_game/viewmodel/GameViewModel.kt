package com.vladshtuka.explainer.presentation.screen_game.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vladshtuka.explainer.domain.model.Dictionary
import com.vladshtuka.explainer.domain.model.Word
import com.vladshtuka.explainer.domain.usecase.dictionary.DictionaryUseCases
import com.vladshtuka.explainer.domain.usecase.team.TeamUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.random.Random.Default.nextInt

@HiltViewModel
class GameViewModel @Inject constructor(
    private val dictionaryUseCases: DictionaryUseCases,
    private val teamUseCases: TeamUseCases
) : ViewModel() {

    private val _teamName = MutableLiveData<String?>()
    val teamName: LiveData<String?>
        get() = _teamName

    private lateinit var dictionary: Dictionary
    private val wordsList = mutableListOf<Word>()

    fun getTeamName() {
        viewModelScope.launch {
            val teamName = teamUseCases.getTeamUseCase()?.name
            _teamName.postValue(teamName)
        }
    }

    fun getDictionary() {
        viewModelScope.launch {
            dictionary = dictionaryUseCases.getDictionaryUseCase()!!
        }
    }

    fun getRandomWord(): String {
        return dictionary.words.random()
    }

    fun addWordToList(word: Word) {
        wordsList.add(word)
    }

    fun getWordsList(): List<Word> {
        return wordsList
    }

    fun clearWordsList() {
        wordsList.clear()
    }

}