package com.example.e_index.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.e_index.R

enum class TopLevelDestinations(
    @DrawableRes val icon: Int,
    @StringRes val title: Int,
    val route: String,
    visible: MutableState<Boolean> = mutableStateOf(true)
) {
    Search(
        icon = R.drawable.ic_search,
        title = R.string.menu_search,
        route = "search_route"
    ),
    Add(
        icon = R.drawable.ic_add_circle,
        title = R.string.menu_add,
        route = "add_route"
    );

    companion object {
        fun fromRoute(route: String?): TopLevelDestinations? =
            when (route?.substringBefore("/")) {
                Search.route -> Search
                Add.route -> Add
                null -> Search
                else -> null
            }
    }
}