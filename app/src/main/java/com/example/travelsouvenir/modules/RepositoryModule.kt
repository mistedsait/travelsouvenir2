package com.example.travelsouvenir.modules


import com.example.travelsouvenir.data.LandmarkRepository
import com.example.travelsouvenir.data.LandmarkRepositoryImpl
import com.example.travelsouvenir.data.PlaceRepository
import com.example.travelsouvenir.data.PlaceRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindPlaceRepository(
        placeRepositoryImpl: PlaceRepositoryImpl
    ): PlaceRepository

    @Binds
    abstract fun bindLandmarkRepository(
        impl: LandmarkRepositoryImpl
    ): LandmarkRepository
}
