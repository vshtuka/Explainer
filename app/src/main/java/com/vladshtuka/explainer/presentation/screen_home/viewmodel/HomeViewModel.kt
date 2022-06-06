package com.vladshtuka.explainer.presentation.screen_home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vladshtuka.explainer.common.Constants
import com.vladshtuka.explainer.domain.usecase.dictionary.DictionaryUseCases
import com.vladshtuka.explainer.domain.usecase.team.TeamUseCases
import com.vladshtuka.explainer.domain.usecase.time.TimeUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dictionaryUseCases: DictionaryUseCases,
    private val teamsUseCases: TeamUseCases,
    private val timeUseCases: TimeUseCases
) : ViewModel() {

    private val _isGameCreated = MutableLiveData<Boolean?>()
    val isGameCreated: LiveData<Boolean?>
        get() = _isGameCreated

    fun removeDictionary() {
        viewModelScope.launch {
            dictionaryUseCases.removeDictionaryUseCase()
        }
    }

    fun removeTeams() {
        viewModelScope.launch {
            teamsUseCases.removeTeamsUseCase()
        }
    }

    fun removeActiveTeam() {
        viewModelScope.launch {
            teamsUseCases.removeActiveTeamUseCase()
        }
    }

    fun setDefaultTime() {
        viewModelScope.launch {
            timeUseCases.setTimeUseCases(Constants.MIN_ROUND_TIME)
        }
    }

    fun setGameDeleted() {
        viewModelScope.launch {
            teamsUseCases.setGameDeletedUseCase()
        }
    }

    fun getGameCreatedState() {
        viewModelScope.launch {
            _isGameCreated.postValue(teamsUseCases.getGameCreatedStateUseCase())
        }
    }

}