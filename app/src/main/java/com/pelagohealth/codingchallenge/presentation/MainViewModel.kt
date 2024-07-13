package com.pelagohealth.codingchallenge.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pelagohealth.codingchallenge.domain.model.Fact
import com.pelagohealth.codingchallenge.domain.usecase.FactUseCase
import com.pelagohealth.codingchallenge.presentation.ui.uistate.FactUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
 class MainViewModel @Inject constructor (private val useCase: FactUseCase) : ViewModel() {
     var factList  = MutableStateFlow<FactUIState>(FactUIState.None)
    val mFactList: StateFlow<FactUIState> = factList

    init {
        fetchNewFact()
    }

    fun fetchNewFact() {
        // Get a new fact from the REST API and display it to the user
        viewModelScope.launch {
            useCase().collect { factList.value = it }
        }
    }
    fun removeFact(fact: Fact) {
        viewModelScope.launch {
            factList.value = useCase.removeFact(fact)
        }
    }
}