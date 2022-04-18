package com.vladshtuka.explainer.domain.usecase.team

import com.vladshtuka.explainer.domain.repository.TeamRepository

class GetTeamNameUseCase(private val repository: TeamRepository) {

    suspend operator fun invoke(name: String): String? {
        return repository.getTeamName(name)
    }

}