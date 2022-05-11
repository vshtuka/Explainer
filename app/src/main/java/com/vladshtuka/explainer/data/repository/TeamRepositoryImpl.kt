package com.vladshtuka.explainer.data.repository

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import com.google.gson.Gson
import com.vladshtuka.explainer.common.Constants
import com.vladshtuka.explainer.data.room.TeamDao
import com.vladshtuka.explainer.domain.model.Team
import com.vladshtuka.explainer.domain.repository.TeamRepository
import javax.inject.Inject

class TeamRepositoryImpl @Inject constructor(
    private val teamDao: TeamDao,
    private val sharedPreferences: SharedPreferences
) : TeamRepository {

    override suspend fun addTeam(team: Team) {
        teamDao.addTeam(team)
    }

    override suspend fun deleteTeam(id: Int) {
        teamDao.deleteTeam(id)
    }

    override suspend fun getTeamName(name: String): String? {
        return teamDao.getTeamName(name)
    }

    override fun getAllTeams(): LiveData<List<Team>> {
        return teamDao.getAllTeams()
    }

    override suspend fun getTeam(): Team? {
        val json = sharedPreferences.getString(Constants.TEAM_KEY, null)
        return Gson().fromJson(json, Team::class.java)
    }

    override suspend fun setTeam(team: Team?) {
        val json = Gson().toJson(team)
        sharedPreferences.edit().putString(Constants.TEAM_KEY, json).apply()
    }

    override suspend fun removeActiveTeam() {
        sharedPreferences.edit().remove(Constants.TEAM_KEY).apply()
    }

    override fun isTeamChosen(): Boolean {
        return null != sharedPreferences.getString(Constants.TEAM_KEY, null)
    }

    override suspend fun removeTeams() {
        teamDao.removeTeams()
    }

    override suspend fun updateTeamScore(score: Int, id: Int) {
        teamDao.updateTeamScore(score, id)
    }

}