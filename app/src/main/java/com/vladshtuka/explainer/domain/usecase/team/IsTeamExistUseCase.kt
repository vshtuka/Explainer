package com.vladshtuka.explainer.domain.usecase.team

import com.vladshtuka.explainer.domain.repository.TeamRepository

class IsTeamExistUseCase(private val repository: TeamRepository) {

    suspend operator fun invoke(name: String): Boolean {
        return repository.getTeamName(name) == null
    }
}