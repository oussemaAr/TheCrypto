package io.studio.thecrypto.data

import io.studio.thecrypto.model.RateModel
import retrofit2.http.GET

interface RateService {
    @GET("api/rates")
    suspend fun fetchRates(): Collection<RateModel>
}