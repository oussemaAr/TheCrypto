package io.studio.thecrypto.domain

import io.studio.thecrypto.US_RATE
import io.studio.thecrypto.data.RateService
import io.studio.thecrypto.model.RateModel

class RateRepository(
    private val rateService: RateService
) {
    suspend fun fetchRate() =
        rateService.fetchRates().find { rate: RateModel -> rate.code == US_RATE }
}