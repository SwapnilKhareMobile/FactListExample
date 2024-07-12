package com.pelagohealth.codingchallenge

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
import io.mockk.mockk
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestModule {

    @Singleton
    @Provides
    fun provideMockFactsRestApi(): FactsRestApi = mockk()

    @Singleton
    @Provides
    fun provideFactSource(api: FactsRestApi): FactSource = RemoteFactSource(api)

    @Singleton
    @Provides
    fun provideFactRepo(source: FactSource): FactRepo = FactRepository(source)

    @Singleton
    @Provides
    fun provideFactUseCase(repo: FactRepo): FactUseCase = GetFactUseCase(repo)
}