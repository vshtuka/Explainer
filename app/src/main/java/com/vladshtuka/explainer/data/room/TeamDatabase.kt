package com.vladshtuka.explainer.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vladshtuka.explainer.domain.model.Team

@Database(entities = [Team::class], version = 1, exportSchema = false)
abstract class TeamDatabase: RoomDatabase() {
    abstract val teamDao: TeamDao
}