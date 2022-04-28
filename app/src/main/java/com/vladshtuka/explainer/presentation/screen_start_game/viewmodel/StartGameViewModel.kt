package com.vladshtuka.explainer.presentation.screen_start_game.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vladshtuka.explainer.domain.model.Team
import com.vladshtuka.explainer.domain.usecase.team.TeamUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StartGameViewModel @Inject constructor(
    private val teamUseCases: TeamUseCases,
) : ViewModel() {

    val teamsList = teamUseCases.getAllTeamsUseCase()

    fun setTeam(team: Team) {
        viewModelScope.launch {
            teamUseCases.setTeamUseCase(team)
        }
    }

    fun isTeamChosen(): Boolean {
        return teamUseCases.isTeamChosenUseCase()
    }

}
