package com.vladshtuka.explainer.data.repository

import android.content.SharedPreferences
import com.vladshtuka.explainer.common.Constants
import com.vladshtuka.explainer.domain.repository.TimeRepository
import javax.inject.Inject


class TimeRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : TimeRepository {

    override suspend fun getTime(): Int {
        return sharedPreferences.getInt(Constants.TIME_KEY, 1)
    }

    override suspend fun setTime(minutes: Int) {
        sharedPreferences.edit().putInt(Constants.TIME_KEY, minutes).apply()
    }

    override suspend fun removeTime() {
        sharedPreferences.edit().remove(Constants.TIME_KEY).apply()
    }
}