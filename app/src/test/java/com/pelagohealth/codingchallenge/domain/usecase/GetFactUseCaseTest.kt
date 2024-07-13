package com.pelagohealth.codingchallenge.domain.usecase

import com.pelagohealth.codingchallenge.data.datasource.rest.APIFact
import com.pelagohealth.codingchallenge.data.repository.FactRepo
import com.pelagohealth.codingchallenge.domain.model.Fact
import com.pelagohealth.codingchallenge.presentation.ui.uistate.FactUIState
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import retrofit2.Response
import kotlin.test.Test

@ExperimentalCoroutinesApi
class GetFactUseCaseTest {

    @MockK
    private lateinit var factRepository: FactRepo

    private lateinit var getFactUseCase: GetFactUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        getFactUseCase = GetFactUseCase(factRepository)
    }

    @Test
    fun `invoke should return success state`() = runTest {
        val fact = Fact("Fact text", "Source URL")
        val apiFact = APIFact("id", "Fact text", "Source URL")
        coEvery { factRepository.getFact() } returns flow { emit(Response.success(apiFact)) }

        val result = getFactUseCase().first()

        assert(result is FactUIState.Success)
        assertEquals((result as FactUIState.Success).facts.first(), fact)
    }

    @Test
    fun `removeFact should update state`() = runTest {
        val fact = Fact("Fact text", "Source URL")
        val apiFact = APIFact("id", "Fact text", "Source URL")
        coEvery { factRepository.getFact() } returns flow { emit(Response.success(apiFact)) }

        // First, add the fact
        getFactUseCase().first()
        // Then, remove the fact
        val stateAfterRemoval = getFactUseCase.removeFact(fact)

        assert(stateAfterRemoval is FactUIState.Success)
        assertEquals((stateAfterRemoval as FactUIState.Success).facts.contains(fact), false)
    }
}