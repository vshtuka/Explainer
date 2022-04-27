package com.vladshtuka.explainer.domain.repository

import com.vladshtuka.explainer.domain.model.Dictionary

interface DictionaryRepository {

    suspend fun getDictionariesFromJson(dictionaryId: Int): List<Dictionary>

    suspend fun getDictionary(): Dictionary?

    suspend fun setDictionary(dictionary: Dictionary?)

    suspend fun removeDictionary()

    suspend fun getDictionaryName(): String

    fun isDictionaryChosen(): Boolean
}