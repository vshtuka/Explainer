package com.vladshtuka.explainer.domain.usecase.team

import com.vladshtuka.explainer.domain.model.Team
import com.vladshtuka.explainer.domain.repository.TeamRepository

class GetTeamUseCase(private val repository: TeamRepository) {

    suspend operator fun invoke(): Team? {
        return repository.getTeam()
    }
}