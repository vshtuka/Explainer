package com.vladshtuka.explainer.domain.usecase.time

import com.vladshtuka.explainer.domain.repository.TimeRepository

class GetTimeUseCase(private val repository: TimeRepository) {

    operator fun invoke(): Int {
        return repository.getTime()
    }

}