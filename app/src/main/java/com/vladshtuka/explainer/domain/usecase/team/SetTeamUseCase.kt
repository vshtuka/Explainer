package com.vladshtuka.explainer.domain.usecase.team

import com.vladshtuka.explainer.domain.model.Team
import com.vladshtuka.explainer.domain.repository.TeamRepository

class SetTeamUseCase(private val repository: TeamRepository) {

    suspend operator fun invoke(team: Team) {
        repository.setTeam(team)
    }
}