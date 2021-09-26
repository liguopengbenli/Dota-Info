package com.lig.hero_ui_herolist.ui

import com.lig.coreunit.ProgressBarState
import com.lig.hero_domain.Hero

data class HeroListState(
    val progressBarState: ProgressBarState = ProgressBarState.idle,
    val heros: List<Hero> = listOf()
)