package com.vladshtuka.explainer.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "team_table")
data class Team(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name: String,
    val score: Int = 0
)
