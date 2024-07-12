package com.pelagohealth.codingchallenge.domain.usecase

import com.pelagohealth.codingchallenge.data.repository.FactRepo
import com.pelagohealth.codingchallenge.domain.model.Fact
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

open class GetFactUseCase @Inject constructor (private val factRepository: FactRepo):FactUseCase {

    override suspend operator fun invoke():Flow<Fact> {
        return factRepository.getFact().map {
            it.body().let {fact ->
                Fact(fact?.text?:"", fact?.sourceUrl?:"")
            }
        }
    }
}