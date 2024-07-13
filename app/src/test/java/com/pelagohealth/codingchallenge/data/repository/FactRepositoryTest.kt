package com.pelagohealth.codingchallenge.data.repository

import com.pelagohealth.codingchallenge.data.datasource.remote.FactSource
import com.pelagohealth.codingchallenge.data.datasource.rest.APIFact
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import retrofit2.Response
import kotlin.test.Test

@ExperimentalCoroutinesApi
class FactRepositoryTest {

    private lateinit var factSource: FactSource
    private lateinit var factRepository: FactRepository

    @Before
    fun setUp() {
        factSource = mockk()
        factRepository = FactRepository(factSource)
    }

    @Test
    fun `getFact should return successful response`() = runTest {
        // Mock the response from factSource.getApiFact()
        val apiResponse = Response.success(APIFact("id","Fact text", "Source URL"))
        coEvery { factSource.getApiFact() } returns flowOf(apiResponse)

        // Call the repository method
        val flow: Flow<Response<APIFact>> = factRepository.getFact()

        // Collect the flow and assert the result
        val response = flow.first()

        assertEquals(apiResponse, response)
    }
}