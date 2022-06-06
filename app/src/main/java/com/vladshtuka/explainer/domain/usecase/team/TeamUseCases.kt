package com.vladshtuka.explainer.domain.usecase.team

data class TeamUseCases(
    val addTeamUseCase: AddTeamUseCase,
    val deleteTeamUseCase: DeleteTeamUseCase,
    val getTeamNameUseCase: GetTeamNameUseCase,
    val getAllTeamsUseCase: GetAllTeamsUseCase,
    val getTeamUseCase: GetTeamUseCase,
    val setTeamUseCase: SetTeamUseCase,
    val removeTeamsUseCase: RemoveTeamsUseCase,
    val removeActiveTeamUseCase: RemoveActiveTeamUseCase,
    val isTeamChosenUseCase: IsTeamChosenUseCase,
    val updateTeamScoreUseCase: UpdateTeamScoreUseCase,
    val isTeamExistUseCase: IsTeamExistUseCase,
    val getGameCreatedStateUseCase: GetGameCreatedStateUseCase,
    val setGameDeletedUseCase: SetGameDeletedUseCase,
    val setGameCreatedUseCase: SetGameCreatedUseCase
)