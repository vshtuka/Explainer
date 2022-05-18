package com.vladshtuka.explainer.presentation.screen_game.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vladshtuka.explainer.domain.model.Dictionary
import com.vladshtuka.explainer.domain.model.Word
import com.vladshtuka.explainer.domain.usecase.dictionary.DictionaryUseCases
import com.vladshtuka.explainer.domain.usecase.team.TeamUseCases
import com.vladshtuka.explainer.domain.usecase.time.TimeUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.properties.Delegates

@HiltViewModel
class GameViewModel @Inject constructor(
    private val dictionaryUseCases: DictionaryUseCases,
    private val teamUseCases: TeamUseCases,
    private val timeUseCases: TimeUseCases
) : ViewModel() {

    private val _teamName = MutableLiveData<String?>()
    val teamName: LiveData<String?>
        get() = _teamName

    private val _gameTime = MutableLiveData<Int?>()
    val gameTime: LiveData<Int?>
        get() = _gameTime

    private val _isGameActive = MutableLiveData<Boolean?>()
    val isGameActive: LiveData<Boolean?>
        get() = _isGameActive

    private lateinit var dictionary: Dictionary
    private var timeRemaining = 0L
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

    fun getTime() {
        viewModelScope.launch {
            _gameTime.postValue(timeUseCases.getTimeUseCase())
        }
    }

    fun startGame() {
        _isGameActive.postValue(true)
    }

    fun cancelGame() {
        _isGameActive.postValue(false)
    }

    fun setTimeRemaining(millisUntilFinished: Long) {
        timeRemaining = millisUntilFinished
    }

    fun getTimeRemaining(): Long {
        return timeRemaining
    }

}