package com.vladshtuka.explainer.presentation.screen_start_game.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vladshtuka.explainer.domain.model.Team
import com.vladshtuka.explainer.domain.usecase.team.TeamUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StartGameViewModel @Inject constructor(
    private val teamUseCases: TeamUseCases,
) : ViewModel() {

    val teamsList = teamUseCases.getAllTeamsUseCase()

}
