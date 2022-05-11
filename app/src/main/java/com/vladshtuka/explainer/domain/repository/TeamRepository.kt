package com.vladshtuka.explainer.domain.repository

import androidx.lifecycle.LiveData
import com.vladshtuka.explainer.domain.model.Team

interface TeamRepository {

    suspend fun addTeam(team: Team)

    suspend fun deleteTeam(id: Int)

    suspend fun getTeamName(name: String): String?

    fun getAllTeams(): LiveData<List<Team>>

    suspend fun getTeam(): Team?

    suspend fun setTeam(team: Team?)

    suspend fun removeActiveTeam()

    fun isTeamChosen(): Boolean

    suspend fun removeTeams()

    suspend fun updateTeamScore(score: Int, id: Int)

}