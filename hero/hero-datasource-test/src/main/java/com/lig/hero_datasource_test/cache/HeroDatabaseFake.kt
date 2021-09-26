package com.lig.hero_datasource_test.cache

import com.lig.hero_domain.Hero

class HeroDatabaseFake {
    val heros: MutableList<Hero> = mutableListOf()
}