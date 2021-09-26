package com.codingwithmitch.dotainfo.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import com.codingwithmitch.dotainfo.R
import com.codingwithmitch.dotainfo.ui.theme.DotaInfoTheme
import com.lig.coreunit.Logger
import com.lig.hero_interactors.HeroInteractors
import com.lig.hero_ui_herolist.HeroListViewModel
import com.lig.hero_ui_herolist.ui.HeroList
import com.lig.hero_ui_herolist.ui.HeroListState
import com.squareup.sqldelight.android.AndroidSqliteDriver
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

   @Inject
   lateinit var imageLoader: ImageLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DotaInfoTheme {
                val viewmodel: HeroListViewModel = hiltViewModel()
                HeroList(state = viewmodel.state.value, imageLoader = imageLoader)
            }
        }


    }
}















