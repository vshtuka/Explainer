package com.vladshtuka.explainer.presentation.screen_finish_game.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vladshtuka.explainer.domain.usecase.team.TeamUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FinishGameViewModel @Inject constructor(
    private val teamUseCases: TeamUseCases
) : ViewModel() {

    private val _teamName = MutableLiveData<String?>()
    val teamName: LiveData<String?>
        get() = _teamName

    fun getTeamName() {
        viewModelScope.launch {
            val teamName = teamUseCases.getTeamUseCase()?.name
            _teamName.postValue(teamName)
        }
    }

    fun removeActiveTeam() {
        viewModelScope.launch {
            teamUseCases.removeActiveTeamUseCase()
        }
    }

}