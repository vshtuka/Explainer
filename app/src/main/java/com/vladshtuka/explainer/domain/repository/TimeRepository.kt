package com.vladshtuka.explainer.domain.repository


interface TimeRepository {

    suspend fun getTime(): Int

    suspend fun setTime(minutes: Int)

}