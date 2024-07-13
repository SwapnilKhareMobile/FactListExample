package com.pelagohealth.codingchallenge.data.datasource.remote

import com.pelagohealth.codingchallenge.data.datasource.rest.APIFact
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

/**
 * Interface for the fact source
 */
interface FactSource {
    /**
     * Get a random fact
     */
    suspend fun getApiFact():Flow<Response<APIFact>>
}