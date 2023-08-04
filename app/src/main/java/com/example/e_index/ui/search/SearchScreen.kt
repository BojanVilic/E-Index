package com.example.e_index.ui.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.e_index.data.UserRole
import com.example.e_index.ui.login.LoginRepository.Companion.currentSessionRole
import com.example.e_index.util.MainMenuOption

@Composable
fun SearchScreen(
    onMenuOptionClicked: (SearchOption) -> Unit
) {
    SearchContent(
        onMenuOptionClicked = onMenuOptionClicked
    )
}

@Composable
fun SearchContent(
    onMenuOptionClicked: (SearchOption) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        val commandList =
            if (currentSessionRole == UserRole.ADMIN) adminSearchOptions
            else studentSearchOptions

        commandList.forEach { searchOption ->
            MainMenuOption(
                title = searchOption.titleRes,
                onRowClicked = {
                    onMenuOptionClicked(searchOption)
                }
            )
        }
    }
}