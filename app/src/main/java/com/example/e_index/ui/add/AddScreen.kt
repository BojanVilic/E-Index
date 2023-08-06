package com.example.e_index.ui.add

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.e_index.ui.theme.EIndexTheme
import com.example.e_index.util.MainMenuOption

@Composable
fun AddScreen(
    onMenuItemClicked: (AddMenuItem) -> Unit
) {
    AddContent(
        onMenuItemClicked = onMenuItemClicked
    )
}

@Composable
fun AddContent(
    onMenuItemClicked: (AddMenuItem) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        LazyColumn {
            items(addMenuItems.size) { index ->
                MainMenuOption(
                    title = addMenuItems[index].titleRes,
                    onRowClicked = {
                        onMenuItemClicked(addMenuItems[index])
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddContentPreview() {
    EIndexTheme {
        AddContent(
            onMenuItemClicked = {}
        )
    }
}