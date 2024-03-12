package com.example.androidtask.data

import com.example.androidtask.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

// ItemRepositoryModule.kt
@Module
@InstallIn(SingletonComponent::class)
object ItemRepositoryModule {
    @Provides
    fun provideItemRepository(itemDao: ItemDao ,apiService: ApiService): ItemRepository {
        return ItemRepository(itemDao,apiService)
    }

}
