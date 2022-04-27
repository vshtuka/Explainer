package com.vladshtuka.explainer.domain.usecase.dictionary

import com.vladshtuka.explainer.domain.repository.DictionaryRepository

class IsDictionaryChosenUseCase(private val repository: DictionaryRepository) {

    operator fun invoke(): Boolean {
        return repository.isDictionaryChosen()
    }
}