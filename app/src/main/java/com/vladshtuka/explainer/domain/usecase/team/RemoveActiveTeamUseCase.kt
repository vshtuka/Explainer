package com.vladshtuka.explainer.domain.usecase.team

import com.vladshtuka.explainer.domain.repository.TeamRepository

class RemoveActiveTeamUseCase(private val repository: TeamRepository) {

    suspend operator fun invoke() {
        repository.removeTeam()
    }

}