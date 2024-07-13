package com.pelagohealth.codingchallenge.domain.usecase

import com.pelagohealth.codingchallenge.domain.model.Fact
import com.pelagohealth.codingchallenge.presentation.ui.uistate.FactUIState
import kotlinx.coroutines.flow.Flow

/**
 * Interface for the fact use case
 */
interface FactUseCase {
        suspend operator fun invoke(): Flow<FactUIState>
        suspend fun removeFact(fact: Fact):FactUIState
}