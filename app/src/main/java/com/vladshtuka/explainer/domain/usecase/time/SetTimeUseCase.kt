package com.vladshtuka.explainer.domain.usecase.time

import com.vladshtuka.explainer.domain.repository.TimeRepository

class SetTimeUseCase(private val repository: TimeRepository) {

    suspend operator fun invoke(minutes: Int) {
        repository.setTime(minutes)
    }
}