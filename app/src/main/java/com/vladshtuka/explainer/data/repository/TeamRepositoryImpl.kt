package com.vladshtuka.explainer.data.repository

import androidx.lifecycle.LiveData
import com.vladshtuka.explainer.data.room.TeamDao
import com.vladshtuka.explainer.domain.model.Team
import com.vladshtuka.explainer.domain.repository.TeamRepository
import javax.inject.Inject

class TeamRepositoryImpl @Inject constructor(private val teamDao: TeamDao) : TeamRepository {

    override suspend fun insertTeam(team: Team) {
        teamDao.insertTeam(team)
    }

    override suspend fun deleteTeamById(id: Int) {
        teamDao.deleteTeamById(id)
    }

    override suspend fun getTeamName(name: String): String? {
        return teamDao.getTeamName(name)
    }

    override fun getAllTeams(): LiveData<List<Team>> {
        return teamDao.getAllTeams()
    }
}