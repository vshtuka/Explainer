package com.vladshtuka.explainer.domain.usecase.dictionary

import com.vladshtuka.explainer.domain.model.Dictionary
import com.vladshtuka.explainer.domain.repository.DictionaryRepository

class GetDictionariesFromJsonUseCase(private val repository: DictionaryRepository) {

    suspend operator fun invoke(dictionaryId: Int): List<Dictionary> {
        return repository.getDictionariesFromJson(dictionaryId)
    }
}