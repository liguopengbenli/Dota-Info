package com.lig.hero_datasource_test.network

import com.lig.hero_datasource.network.model.HeroDto
import com.lig.hero_datasource.network.model.toHero
import com.lig.hero_domain.Hero
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

private val json = Json {
    ignoreUnknownKeys = true
}

fun deserializeHeroData(jsonData: String): List<Hero> =
    json.decodeFromString<List<HeroDto>>(jsonData).map { it.toHero() }
