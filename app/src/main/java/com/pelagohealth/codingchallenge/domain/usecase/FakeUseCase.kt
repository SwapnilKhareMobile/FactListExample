package com.pelagohealth.codingchallenge.domain.usecase

import com.pelagohealth.codingchallenge.domain.model.Fact
import com.pelagohealth.codingchallenge.presentation.ui.uistate.FactUIState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

/**
 * Fake implementation of the FactUseCase interface for testing purposes.
 */
class FakeUseCase(private var initialState: FactUIState = FactUIState.None) : FactUseCase {

    override suspend operator fun invoke(): Flow<FactUIState> {
        return flowOf(initialState)
    }

    override suspend fun removeFact(fact: Fact): FactUIState {
        when (initialState) {
            is FactUIState.Success -> {
                val updatedFacts = (initialState as FactUIState.Success).facts.toMutableList()
                updatedFacts.remove(fact)
                return FactUIState.Success(updatedFacts)
            }
            else -> return initialState
        }
    }

    // Helper method to set initial state for testing
    fun setInitialState(state: FactUIState) {
        initialState = state
    }
}