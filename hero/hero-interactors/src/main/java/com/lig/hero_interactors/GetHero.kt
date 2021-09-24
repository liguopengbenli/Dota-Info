package com.lig.hero_interactors

import DataState
import com.lig.coreunit.ProgressBarState
import com.lig.coreunit.UIComponent
import com.lig.hero_datasource.cache.HeroCache
import com.lig.hero_datasource.network.HeroService
import com.lig.hero_domain.Hero
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class GetHero(
    private val service: HeroService,
    private val cache: HeroCache
) {
    fun execute(): Flow<DataState<List<Hero>>> = flow {
        try {
            emit(DataState.Loading(ProgressBarState.Loading))
            val hero: List<Hero> = try {
                service.getHeroStates()

            } catch (e: Exception) {
                e.printStackTrace()
                emit(
                    DataState.Response<List<Hero>>(
                        uiComponent = UIComponent.Dialog(
                            titile = "Error",
                            description = e.message ?: "Network Error"
                        )
                    )
                )
                listOf()
            }
            cache.insert(hero)
            val cachedHeros = cache.selectAll()

            emit(DataState.Data(cachedHeros))

        } catch (e: Exception) {
            e.printStackTrace()

            emit(
                DataState.Response<List<Hero>>(
                    uiComponent = UIComponent.Dialog(
                        titile = "Error",
                        description = e.message ?: "Unknown Error"
                    )
                )
            )
        } finally {
            emit(DataState.Loading(progressBarState = ProgressBarState.idle))
        }
    }


}