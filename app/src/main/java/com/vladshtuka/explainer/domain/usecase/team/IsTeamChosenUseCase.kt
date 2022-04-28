package com.vladshtuka.explainer.domain.usecase.team

import com.vladshtuka.explainer.domain.repository.TeamRepository

class IsTeamChosenUseCase (private val repository: TeamRepository) {

    operator fun invoke(): Boolean {
        return repository.isTeamChosen()
    }

}