package com.pelagohealth.codingchallenge.data.datasource.remote

import com.pelagohealth.codingchallenge.data.datasource.rest.FactsRestApi
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoteFactSource @Inject constructor (private val factsRestApi: FactsRestApi) :FactSource{
    override suspend fun getApiFact() = flow {
        emit(factsRestApi.getFact())
    }
}