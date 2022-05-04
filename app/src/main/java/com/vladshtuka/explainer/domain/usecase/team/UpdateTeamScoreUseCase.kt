package com.vladshtuka.explainer.domain.usecase.team

import com.vladshtuka.explainer.domain.repository.TeamRepository

class UpdateTeamScoreUseCase(private val repository: TeamRepository) {

    suspend operator fun invoke(score: Int, id: Int) {
        repository.updateTeamScore(score, id)
    }
}