package com.pelagohealth.codingchallenge.data.datasource.rest

import com.pelagohealth.codingchallenge.util.GET_FACT
import retrofit2.Response
import retrofit2.http.GET

/**
 * REST API for fetching random facts.
 */
interface FactsRestApi {

    @GET(GET_FACT)
    suspend fun getFact(): Response<APIFact>
}