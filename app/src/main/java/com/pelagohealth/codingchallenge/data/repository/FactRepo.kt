package com.pelagohealth.codingchallenge.data.repository

import com.pelagohealth.codingchallenge.data.datasource.rest.APIFact
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

/**
 * Interface for the fact repository
 */
interface FactRepo {
    /**
     * Get the fact
     */
    suspend fun getFact():Flow<Response<APIFact>>
}