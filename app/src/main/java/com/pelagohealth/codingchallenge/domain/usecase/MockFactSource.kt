package com.pelagohealth.codingchallenge.domain.usecase

import com.pelagohealth.codingchallenge.data.datasource.remote.FactSource
import com.pelagohealth.codingchallenge.data.datasource.rest.APIFact
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class MockFactSource : FactSource {
    override suspend fun getApiFact(): Flow<Response<APIFact>> = flow {
        // Emit mocked data for testing
        emit(Response.success(APIFact(id = "2", text = "Mocked Fact", sourceUrl = "http://mocked.com",)))
    }
}