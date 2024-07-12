package com.pelagohealth.codingchallenge.data.repository

import com.pelagohealth.codingchallenge.data.datasource.remote.FactSource
import com.pelagohealth.codingchallenge.data.datasource.rest.APIFact
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject

/**
 * Repository providing random facts.
 */
class FactRepository @Inject constructor (private val factSource: FactSource): FactRepo {

    override suspend fun getFact(): Flow<Response<APIFact>> {
        return factSource.getApiFact().flowOn(Dispatchers.IO)
    }
}