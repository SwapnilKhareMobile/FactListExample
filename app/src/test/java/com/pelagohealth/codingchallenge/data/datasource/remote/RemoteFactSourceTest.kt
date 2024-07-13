package com.pelagohealth.codingchallenge.data.datasource.remote

import com.pelagohealth.codingchallenge.data.datasource.rest.APIFact
import com.pelagohealth.codingchallenge.data.datasource.rest.FactsRestApi
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import retrofit2.Response
import kotlin.test.Test

@ExperimentalCoroutinesApi
class RemoteFactSourceTest {

    private lateinit var factsRestApi: FactsRestApi
    private lateinit var remoteFactSource: RemoteFactSource

    @Before
    fun setUp() {
        factsRestApi = mockk()
        remoteFactSource = RemoteFactSource(factsRestApi)
    }

    @Test
    fun `getApiFact should return API response`() = runTest {
        // Mock API response
        val apiResponse = Response.success(APIFact("id","Fact text", "Source URL"))
        coEvery { factsRestApi.getFact() } returns apiResponse

        // Call the repository method
        val flow = remoteFactSource.getApiFact()

        // Collect the flow and assert the result
        val response = flow.first()

        assertEquals(apiResponse, response)
    }
}