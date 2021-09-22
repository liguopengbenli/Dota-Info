package com.lig.hero_interactors

import com.lig.hero_datasource.network.HeroService

class HeroInteractors(
    val getHeros: GetHero
) {
    companion object Factory{
        fun build(): HeroInteractors {
            val service = HeroService.build()
            return HeroInteractors(
                getHeros = GetHero(
                    service = service
                )
            )
        }
    }


}