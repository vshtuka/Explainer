package com.vladshtuka.explainer.presentation.screen_new_game.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vladshtuka.explainer.common.Constants
import com.vladshtuka.explainer.domain.model.Dictionary
import com.vladshtuka.explainer.domain.model.Team
import com.vladshtuka.explainer.domain.usecase.dictionary.DictionaryUseCases
import com.vladshtuka.explainer.domain.usecase.team.TeamUseCases
import com.vladshtuka.explainer.domain.usecase.time.TimeUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewGameViewModel @Inject constructor(
    private val dictionaryUseCases: DictionaryUseCases,
    private val teamUseCases: TeamUseCases,
    private val timeUseCases: TimeUseCases
) : ViewModel() {

    private val _dictionaryName = MutableLiveData<String>()
    val dictionaryName: LiveData<String>
        get() = _dictionaryName

    private val _dictionaryEnList = MutableLiveData<List<Dictionary>>()
    val dictionaryEnList: LiveData<List<Dictionary>>
        get() = _dictionaryEnList

    private val _dictionaryUaList = MutableLiveData<List<Dictionary>>()
    val dictionaryUaList: LiveData<List<Dictionary>>
        get() = _dictionaryUaList

    val teamsList = teamUseCases.getAllTeamsUseCase()

    private val _gameTime = MutableLiveData<Int>()
    val gameTime: LiveData<Int>
        get() = _gameTime

    fun getDictionaryName() {
        viewModelScope.launch {
            _dictionaryName.postValue(dictionaryUseCases.getDictionaryNameUseCase())
        }
    }

    fun getDictionariesFromJson(dictionaryId: Int) {
        viewModelScope.launch {
            val dictionaries = dictionaryUseCases.getDictionariesFromJsonUseCase(dictionaryId)
            if (dictionaryId == Constants.DICTIONARY_EN) {
                _dictionaryEnList.postValue(dictionaries)
            } else {
                _dictionaryUaList.postValue(dictionaries)
            }
        }
    }

    fun setDictionary(dictionary: Dictionary?) {
        viewModelScope.launch {
            dictionaryUseCases.setDictionaryUseCase(dictionary)
        }
    }

    fun setFullDictionary(dictionary: Dictionary?) {
        viewModelScope.launch {
            dictionaryUseCases.setFullDictionaryUseCase(dictionary)
        }
    }

    fun addTeam(team: Team) {
        viewModelScope.launch {
            if (teamUseCases.isTeamExistUseCase(team.name)) {
                teamUseCases.addTeamUseCase(team)
            }
        }
    }

    fun deleteTeam(id: Int) {
        viewModelScope.launch {
            teamUseCases.deleteTeamUseCase(id)
        }
    }

    fun addMinute() {
        viewModelScope.launch {
            val minutes = timeUseCases.getTimeUseCase()
            if (minutes != Constants.MAX_ROUND_TIME) {
                timeUseCases.setTimeUseCases(minutes + 1)
                _gameTime.postValue(minutes + 1)
            }
        }
    }

    fun subtractMinute() {
        viewModelScope.launch {
            val minutes = timeUseCases.getTimeUseCase()
            if (minutes != Constants.MIN_ROUND_TIME) {
                timeUseCases.setTimeUseCases(minutes - 1)
                _gameTime.postValue(minutes - 1)
            }
        }
    }

    fun setDefaultGameTime() {
        viewModelScope.launch {
            timeUseCases.setTimeUseCases(Constants.MIN_ROUND_TIME)
        }
    }

    fun isDictionaryChosen(): Boolean {
       return dictionaryUseCases.isDictionaryChosenUseCase()
    }

    fun isTeamAdded(): Boolean {
        return teamsList.value?.isNotEmpty() ?: false
    }

    fun setGameCreated() {
        viewModelScope.launch {
            teamUseCases.setGameCreatedUseCase()
        }
    }
}