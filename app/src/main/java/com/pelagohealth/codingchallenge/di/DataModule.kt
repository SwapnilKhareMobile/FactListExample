package com.pelagohealth.codingchallenge.di

import com.pelagohealth.codingchallenge.data.datasource.remote.FactSource
import com.pelagohealth.codingchallenge.data.datasource.remote.RemoteFactSource
import com.pelagohealth.codingchallenge.data.datasource.rest.FactsRestApi
import com.pelagohealth.codingchallenge.data.repository.FactRepo
import com.pelagohealth.codingchallenge.data.repository.FactRepository
import com.pelagohealth.codingchallenge.domain.usecase.FactUseCase
import com.pelagohealth.codingchallenge.domain.usecase.GetFactUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Data module to provide dependencies
 */
@InstallIn(SingletonComponent::class)
@Module
class DataModule {

    @Singleton
    @Provides
    fun provideSource(apiFact: FactsRestApi):FactSource{
        return RemoteFactSource(apiFact)
    }

    @Singleton
    @Provides
    fun provideRepo(remoteFactSource: FactSource):FactRepo{
        return FactRepository(remoteFactSource)
    }

    @Singleton
    @Provides
    fun provideUseCase(factRepo: FactRepo):FactUseCase{
        return GetFactUseCase(factRepo)
    }
}