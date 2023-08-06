package com.example.e_index.ui.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.e_index.data.UserRole
import com.example.e_index.ui.login.LoginRepository.Companion.currentSessionRole
import com.example.e_index.util.MainMenuOption

@Composable
fun SearchScreen(
    onMenuItemClicked: (SearchMenuItem) -> Unit
) {
    SearchContent(
        onMenuItemClicked = onMenuItemClicked
    )
}

@Composable
fun SearchContent(
    onMenuItemClicked: (SearchMenuItem) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        val menuItems =
            if (currentSessionRole == UserRole.ADMIN) adminSearchMenuItems
            else studentSearchMenuItems

        LazyColumn {
            items(menuItems.size) { index ->
                MainMenuOption(
                    title = menuItems[index].titleRes,
                    onRowClicked = {
                        onMenuItemClicked(menuItems[index])
                    }
                )
            }
        }
    }
}