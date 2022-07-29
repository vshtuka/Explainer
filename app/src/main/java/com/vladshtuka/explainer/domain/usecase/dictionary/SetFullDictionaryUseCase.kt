package com.vladshtuka.explainer.domain.usecase.dictionary

import com.vladshtuka.explainer.domain.model.Dictionary
import com.vladshtuka.explainer.domain.repository.DictionaryRepository

class SetFullDictionaryUseCase(private val repository: DictionaryRepository) {

    suspend operator fun invoke(dictionary: Dictionary?) {
        return repository.setFullDictionary(dictionary)
    }
}