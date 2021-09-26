package com.lig.hero_ui_herolist.di

import com.lig.hero_interactors.GetHero
import com.lig.hero_interactors.HeroInteractors
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HeroListModule {

    @Provides
    @Singleton
    fun provideGetHero(
        interactors: HeroInteractors
    ): GetHero = interactors.getHeros


}