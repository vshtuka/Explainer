package com.vladshtuka.explainer.domain.usecase.team

import com.vladshtuka.explainer.domain.repository.TeamRepository

class DeleteTeamUseCase(private val repository: TeamRepository) {

    suspend operator fun invoke(id: Int) {
        repository.deleteTeamById(id)
    }

}