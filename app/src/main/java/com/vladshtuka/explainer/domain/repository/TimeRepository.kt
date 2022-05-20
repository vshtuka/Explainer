package com.vladshtuka.explainer.domain.repository


interface TimeRepository {

    fun getTime(): Int

    suspend fun setTime(minutes: Int)

}