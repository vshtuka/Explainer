package com.vladshtuka.explainer.domain.usecase.team

import com.vladshtuka.explainer.domain.repository.TeamRepository

class ClearTableUseCase(private val repository: TeamRepository) {

    suspend operator fun invoke() {
        repository.clearTable()
    }

}