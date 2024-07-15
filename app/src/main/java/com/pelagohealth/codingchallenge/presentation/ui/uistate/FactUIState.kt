package com.pelagohealth.codingchallenge.presentation.ui.uistate

import com.pelagohealth.codingchallenge.domain.model.Fact

/**
 * UI state for the fact list screen
 */
sealed class FactUIState {
    data object None : FactUIState()
    data object Loading : FactUIState()
    data class Success(val facts: List<Fact>) : FactUIState()
    data object Error : FactUIState()

}