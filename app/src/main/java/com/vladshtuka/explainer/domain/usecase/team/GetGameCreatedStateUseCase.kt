package com.vladshtuka.explainer.domain.usecase.team

import com.vladshtuka.explainer.domain.repository.TeamRepository

class GetGameCreatedStateUseCase(private val repository: TeamRepository) {

    suspend operator fun invoke(): Boolean {
        return repository.getGameCreatedState()
    }

}