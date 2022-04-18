package com.vladshtuka.explainer.domain.usecase.team

import androidx.lifecycle.LiveData
import com.vladshtuka.explainer.domain.model.Team
import com.vladshtuka.explainer.domain.repository.TeamRepository

class GetAllTeamsUseCase(private val repository: TeamRepository) {

    operator fun invoke(): LiveData<List<Team>> {
        return repository.getAllTeams()
    }

}