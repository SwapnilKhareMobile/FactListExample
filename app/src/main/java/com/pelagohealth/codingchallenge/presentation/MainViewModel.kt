package com.pelagohealth.codingchallenge.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pelagohealth.codingchallenge.domain.model.Fact
import com.pelagohealth.codingchallenge.domain.usecase.FactUseCase
import com.pelagohealth.codingchallenge.domain.usecase.GetFactUseCase
import com.pelagohealth.codingchallenge.presentation.ui.uistate.FactUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
 class MainViewModel @Inject constructor (private val useCase: FactUseCase) : ViewModel() {
     var factList  = MutableStateFlow<FactUIState>(FactUIState.None)
    val mFactList: StateFlow<FactUIState> = factList
    var currentFacts = mutableListOf<Fact>()


    init {
        fetchNewFact()
    }

    fun fetchNewFact() {
        // Get a new fact from the REST API and display it to the user
        viewModelScope.launch {
            // Display the fact to the user
            useCase()
                .map { fact ->
                    currentFacts.add(0, fact)
                    if (currentFacts.size > 3) currentFacts = currentFacts.take(3).toMutableList()
                    FactUIState.Success(currentFacts.toList())
                }
            .catch { factList.value = FactUIState.Error }
            .collect{factList.value = it}
        }
    }
    fun removeFact(fact: Fact) {
        currentFacts.remove(fact)
        factList.value = FactUIState.Success(currentFacts.toList())
    }
}