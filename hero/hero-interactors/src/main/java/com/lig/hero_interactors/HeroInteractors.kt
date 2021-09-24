package com.lig.hero_interactors

import com.lig.hero_datasource.cache.HeroCache
import com.lig.hero_datasource.network.HeroService
import com.squareup.sqldelight.db.SqlDriver

class HeroInteractors(
    val getHeros: GetHero
) {
    companion object Factory {
        fun build(sqlDriver: SqlDriver): HeroInteractors {
            val service = HeroService.build()
            val cache = HeroCache.build(sqlDriver)
            return HeroInteractors(
                getHeros = GetHero(
                    service = service,
                    cache = cache
                )
            )
        }

        val schema: SqlDriver.Schema = HeroCache.schema
        val dbName: String = HeroCache.dbName
    }


}