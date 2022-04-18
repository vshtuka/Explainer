package com.vladshtuka.explainer.domain.repository

import androidx.lifecycle.LiveData
import com.vladshtuka.explainer.domain.model.Team

interface TeamRepository {

    suspend fun insertTeam(team: Team)

    suspend fun deleteTeamById(id: Int)

    suspend fun getTeamName(name: String): String?

    fun getAllTeams(): LiveData<List<Team>>

}