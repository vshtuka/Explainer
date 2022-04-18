package com.vladshtuka.explainer.domain.usecase.team

data class TeamUseCases(
    val insertTeamUseCase: InsertTeamUseCase,
    val deleteTeamUseCase: DeleteTeamUseCase,
    val getTeamNameUseCase: GetTeamNameUseCase,
    val getAllTeamsUseCase: GetAllTeamsUseCase
)