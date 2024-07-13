package com.pelagohealth.codingchallenge.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.pelagohealth.codingchallenge.domain.model.Fact
import com.pelagohealth.codingchallenge.domain.usecase.FakeUseCase
import com.pelagohealth.codingchallenge.presentation.ui.uistate.FactUIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import kotlin.test.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    // The ViewModel being tested
    private lateinit var mainViewModel: MainViewModel

    // Fake UseCase for testing
    private lateinit var fakeUseCase: FakeUseCase



    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined) // Set the main dispatcher
        fakeUseCase = FakeUseCase()
        mainViewModel = MainViewModel(fakeUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // Reset main dispatcher
    }

    @Test
    fun `fetchNewFact should update state`() = runTest {
        val fact = Fact("Fact text", "Source URL")
        val factState = MutableStateFlow(FactUIState.Success(listOf(fact)))
        fakeUseCase.setInitialState(factState.value) // Set initial state for FakeUseCase

        mainViewModel.fetchNewFact()

        advanceUntilIdle()

        assert(mainViewModel.mFactList.value is FactUIState.Success)
        assertEquals(listOf(fact), (mainViewModel.mFactList.value as FactUIState.Success).facts)
    }

    @Test
    fun `removeFact should update state`() = runTest {
        val factToRemove = Fact("Fact text", "Source URL")
        val initialFacts = listOf(
            Fact("Fact 1", "Source URL 1"),
            Fact("Fact 2", "Source URL 2"),
            factToRemove
        )
        val initialState = MutableStateFlow(FactUIState.Success(initialFacts))
        fakeUseCase.setInitialState(initialState.value) // Set initial state for FakeUseCase

        mainViewModel.removeFact(factToRemove)

        advanceUntilIdle()

        assert(mainViewModel.mFactList.value is FactUIState.Success)
        assertEquals(initialFacts.filter { it != factToRemove }, (mainViewModel.mFactList.value as FactUIState.Success).facts)
    }
}