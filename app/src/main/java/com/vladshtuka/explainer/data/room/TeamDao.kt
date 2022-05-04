package com.vladshtuka.explainer.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vladshtuka.explainer.domain.model.Team

@Dao
interface TeamDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTeam(team: Team)

    @Query("DELETE FROM team_table WHERE id = :id")
    suspend fun deleteTeamById(id: Int)

    @Query("SELECT name FROM team_table WHERE name = :name LIMIT 1")
    suspend fun getTeamName(name: String): String?

    @Query("SELECT * FROM team_table")
    fun getAllTeams(): LiveData<List<Team>>

    @Query("SELECT * FROM team_table WHERE id = :id ")
    suspend fun getTeamById(id: Int): Team?

    @Query("UPDATE team_table SET score =:score WHERE id = :id")
    suspend fun updateTeamScore(score: Int, id: Int)

    @Query("DELETE FROM team_table")
    suspend fun clearTable()
}