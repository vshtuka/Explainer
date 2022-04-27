package com.vladshtuka.explainer.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.vladshtuka.explainer.R
import com.vladshtuka.explainer.common.Constants
import com.vladshtuka.explainer.domain.model.Dictionary
import com.vladshtuka.explainer.domain.repository.DictionaryRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import java.lang.reflect.Type
import javax.inject.Inject

class DictionaryRepositoryImpl @Inject constructor(
    @ApplicationContext val appContext: Context,
    private val sharedPreferences: SharedPreferences
) :
    DictionaryRepository {

    override suspend fun getDictionariesFromJson(dictionaryId: Int): List<Dictionary> {
        val jsonString = appContext.resources.openRawResource(dictionaryId)
            .bufferedReader().use { it.readText() }
        val listType: Type = object : TypeToken<List<Dictionary>>() {}.type
        return Gson().fromJson(jsonString, listType)
    }

    override suspend fun getDictionary(): Dictionary? {
        val json = sharedPreferences.getString(Constants.DICTIONARY_KEY, null)
        return Gson().fromJson(json, Dictionary::class.java)
    }

    override suspend fun setDictionary(dictionary: Dictionary?) {
        val json = Gson().toJson(dictionary)
        sharedPreferences.edit().putString(Constants.DICTIONARY_KEY, json).apply()
    }

    override suspend fun removeDictionary() {
        sharedPreferences.edit().remove(Constants.DICTIONARY_KEY).apply()
    }

    override suspend fun getDictionaryName(): String {
        val json = sharedPreferences.getString(Constants.DICTIONARY_KEY, null)
        return if (json == null) {
            appContext.getString(R.string.select_dictionary)
        } else {
            Gson().fromJson(json, Dictionary::class.java).name
        }
    }

    override fun isDictionaryChosen(): Boolean {
        return null != sharedPreferences.getString(Constants.DICTIONARY_KEY, null)
    }
}