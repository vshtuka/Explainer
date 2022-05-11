package com.vladshtuka.explainer.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.vladshtuka.explainer.data.repository.DictionaryRepositoryImpl
import com.vladshtuka.explainer.common.Constants
import com.vladshtuka.explainer.data.repository.TeamRepositoryImpl
import com.vladshtuka.explainer.data.repository.TimeRepositoryImpl
import com.vladshtuka.explainer.data.room.TeamDatabase
import com.vladshtuka.explainer.domain.repository.DictionaryRepository
import com.vladshtuka.explainer.domain.repository.TeamRepository
import com.vladshtuka.explainer.domain.repository.TimeRepository
import com.vladshtuka.explainer.domain.usecase.dictionary.*
import com.vladshtuka.explainer.domain.usecase.team.*
import com.vladshtuka.explainer.domain.usecase.time.GetTimeUseCase
import com.vladshtuka.explainer.domain.usecase.time.SetTimeUseCase
import com.vladshtuka.explainer.domain.usecase.time.TimeUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTeamDatabase(application: Application): TeamDatabase {
        return Room.databaseBuilder(
            application,
            TeamDatabase::class.java,
            Constants.TEAM_DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(Constants.PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideDictionaryRepository(
        @ApplicationContext appContext: Context,
        sharedPreferences: SharedPreferences
    ): DictionaryRepository {
        return DictionaryRepositoryImpl(appContext, sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideTeamRepository(
        database: TeamDatabase,
        sharedPreferences: SharedPreferences
    ): TeamRepository {
        return TeamRepositoryImpl(database.teamDao, sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideTimeRepository(sharedPreferences: SharedPreferences): TimeRepository {
        return TimeRepositoryImpl(sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideDictionaryUseCases(repository: DictionaryRepository): DictionaryUseCases {
        return DictionaryUseCases(
            getDictionariesFromJsonUseCase = GetDictionariesFromJsonUseCase(repository),
            getDictionaryUseCase = GetDictionaryUseCase(repository),
            setDictionaryUseCase = SetDictionaryUseCase(repository),
            removeDictionaryUseCase = RemoveDictionaryUseCase(repository),
            getDictionaryNameUseCase = GetDictionaryNameUseCase(repository),
            isDictionaryChosenUseCase = IsDictionaryChosenUseCase(repository)
        )
    }

    @Provides
    @Singleton
    fun provideTeamUseCases(repository: TeamRepository): TeamUseCases {
        return TeamUseCases(
            addTeamUseCase = AddTeamUseCase(repository),
            deleteTeamUseCase = DeleteTeamUseCase(repository),
            getTeamNameUseCase = GetTeamNameUseCase(repository),
            getAllTeamsUseCase = GetAllTeamsUseCase(repository),
            getTeamUseCase = GetTeamUseCase(repository),
            setTeamUseCase = SetTeamUseCase(repository),
            removeTeamsUseCase = RemoveTeamsUseCase(repository),
            removeActiveTeamUseCase = RemoveActiveTeamUseCase(repository),
            isTeamChosenUseCase = IsTeamChosenUseCase(repository),
            updateTeamScoreUseCase = UpdateTeamScoreUseCase(repository),
            isTeamExistUseCase = IsTeamExistUseCase(repository)
        )
    }

    @Provides
    @Singleton
    fun provideTimeUseCases(repository: TimeRepository): TimeUseCases {
        return TimeUseCases(
            getTimeUseCase = GetTimeUseCase(repository),
            setTimeUseCases = SetTimeUseCase(repository)
        )
    }

}