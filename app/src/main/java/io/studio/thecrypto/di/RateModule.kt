package io.studio.thecrypto.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.studio.thecrypto.data.NetworkClient
import io.studio.thecrypto.data.RateService
import io.studio.thecrypto.domain.RateRepository

@Module
@InstallIn(SingletonComponent::class)
class RateModule {
    @Provides
    fun providesRateService() = NetworkClient.getInstance()

    @Provides
    fun providerRateRepository(rateService: RateService) = RateRepository(rateService)
}