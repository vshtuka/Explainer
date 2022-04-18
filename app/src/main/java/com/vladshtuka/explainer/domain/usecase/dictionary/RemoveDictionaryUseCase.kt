package com.vladshtuka.explainer.domain.usecase.dictionary

import com.vladshtuka.explainer.domain.repository.DictionaryRepository

class RemoveDictionaryUseCase(private val repository: DictionaryRepository) {

    suspend operator fun invoke() {
        repository.removeDictionary()
    }
}