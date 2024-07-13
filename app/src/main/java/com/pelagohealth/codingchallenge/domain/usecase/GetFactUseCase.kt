package com.pelagohealth.codingchallenge.domain.usecase

import com.pelagohealth.codingchallenge.data.repository.FactRepo
import com.pelagohealth.codingchallenge.domain.model.Fact
import com.pelagohealth.codingchallenge.presentation.ui.uistate.FactUIState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Get Fact Use Case implementation
 */
 class GetFactUseCase @Inject constructor(private val factRepository: FactRepo) : FactUseCase {
    private var currentFacts = mutableListOf<Fact>()

    override suspend operator fun invoke(): Flow<FactUIState> = flow {
        factRepository.getFact().map { response ->
            val fact = response.body().let {
                Fact(it?.text ?: "", it?.sourceUrl ?: "")
            }
            currentFacts.add(0, fact)
            if (currentFacts.size > 3) currentFacts = currentFacts.take(3).toMutableList()
            FactUIState.Success(currentFacts.toList())
        }.catch { emit(FactUIState.Error) }
            .collect { emit(it) }
    }

    override suspend fun removeFact(fact: Fact): FactUIState {
        currentFacts.remove(fact)
        return FactUIState.Success(currentFacts.toList())
    }
}