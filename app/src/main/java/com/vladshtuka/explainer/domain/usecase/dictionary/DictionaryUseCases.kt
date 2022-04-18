package com.vladshtuka.explainer.domain.usecase.dictionary

data class DictionaryUseCases(
    val getDictionariesFromJsonUseCase: GetDictionariesFromJsonUseCase,
    val getDictionaryUseCase: GetDictionaryUseCase,
    val setDictionaryUseCase: SetDictionaryUseCase,
    val removeDictionaryUseCase: RemoveDictionaryUseCase
    )
