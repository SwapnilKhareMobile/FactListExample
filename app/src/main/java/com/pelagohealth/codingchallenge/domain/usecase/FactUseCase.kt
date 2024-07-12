package com.pelagohealth.codingchallenge.domain.usecase

import com.pelagohealth.codingchallenge.domain.model.Fact
import kotlinx.coroutines.flow.Flow

interface FactUseCase {
        suspend operator fun invoke(): Flow<Fact>
}