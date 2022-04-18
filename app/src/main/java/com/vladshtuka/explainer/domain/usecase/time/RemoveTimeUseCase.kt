package com.vladshtuka.explainer.domain.usecase.time

import com.vladshtuka.explainer.domain.repository.TimeRepository

class RemoveTimeUseCase(private val repository: TimeRepository) {

    suspend operator fun invoke() {
        repository.removeTime()
    }

}