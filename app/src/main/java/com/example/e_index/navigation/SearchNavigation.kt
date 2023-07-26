package com.example.e_index.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.e_index.ui.search.SearchScreen

fun NavGraphBuilder.searchScreen(navController: NavHostController) {
    composable(TopLevelDestinations.Search.route) {
        SearchScreen()
    }
}