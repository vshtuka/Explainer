package com.vladshtuka.explainer.domain.usecase.dictionary

import com.vladshtuka.explainer.domain.repository.DictionaryRepository

class GetDictionaryNameUseCase(private val repository: DictionaryRepository) {

    suspend operator fun invoke(): String {
        return repository.getDictionaryName()
    }
}