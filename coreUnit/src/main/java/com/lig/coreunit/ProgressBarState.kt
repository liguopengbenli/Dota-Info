package com.lig.coreunit

sealed class ProgressBarState{
    object Loading: ProgressBarState()
    object Idle: ProgressBarState()
}
