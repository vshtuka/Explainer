package com.vladshtuka.explainer.presentation.screen_finish_game.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vladshtuka.explainer.domain.model.Word
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

   var score = 0

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

    fun updateTeamScore() {
        viewModelScope.launch {
            val teamId = teamUseCases.getTeamUseCase()!!.id!!
            val teamScore = teamUseCases.getTeamUseCase()!!.score
            val teamNewScore = teamScore + score
            teamUseCases.updateTeamScoreUseCase(teamNewScore, teamId)
        }
    }

    fun initScore(words: List<Word>?) {
        var score = 0
        if (words != null) {
            for (word in words) {
                if (word.isAnswerTrue) {
                    score++
                } else {
                    score--
                }
            }
            this.score = score
        }
    }

    fun addOnePoint() {
        score++
    }

    fun subtractOnePoint() {
        score--
    }

}