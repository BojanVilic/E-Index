package com.example.e_index.navigation.components

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.e_index.navigation.TopLevelDestinations
import com.example.e_index.ui.theme.EIndexTheme

@Composable
fun TabRow(
    allScreens: List<TopLevelDestinations>,
    onTabSelected: (TopLevelDestinations) -> Unit,
    currentScreen: TopLevelDestinations
) {
    Surface(
        Modifier
            .fillMaxWidth()
    ) {
        Row(Modifier
            .fillMaxWidth()
            .selectableGroup(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            allScreens.forEach { screen ->
                Tab(
                    titleRes = screen.title,
                    iconRes = screen.icon,
                    onSelected = { onTabSelected(screen) },
                    selected = currentScreen == screen
                )
            }
        }
    }
}

@Composable
private fun Tab(
    @StringRes titleRes: Int,
    @DrawableRes iconRes: Int,
    onSelected: () -> Unit,
    selected: Boolean
) {

    val color = MaterialTheme.colorScheme.onSurface
    val durationMillis = if (selected) TabFadeInAnimationDuration else TabFadeOutAnimationDuration
    val animSpec = remember {
        tween<Color>(
            durationMillis = durationMillis,
            easing = LinearEasing,
            delayMillis = TabFadeInAnimationDelay
        )
    }

    val tabTintColor by animateColorAsState(
        targetValue = if (selected) color else color.copy(alpha = InactiveTabOpacity),
        animationSpec = animSpec, label = ""
    )

    Column(
        modifier = Modifier
            .padding(vertical = 16.dp, horizontal = 12.dp)
            .animateContentSize()
            .selectable(
                selected = selected,
                onClick = onSelected,
                role = Role.Tab,
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(
                    bounded = false,
                    radius = Dp.Unspecified,
                    color = Color.Unspecified
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(painterResource(id = iconRes), contentDescription = null, tint = tabTintColor)
        Spacer(Modifier.height(12.dp))
        Text(stringResource(id = titleRes).uppercase(), color = tabTintColor)
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun TabRowPreview() {
    EIndexTheme {
        TabRow(TopLevelDestinations.values().toList(),
            {},
            TopLevelDestinations.Search
        )
    }
}

private const val InactiveTabOpacity = 0.60f
private const val TabFadeInAnimationDuration = 150
private const val TabFadeInAnimationDelay = 200
private const val TabFadeOutAnimationDuration = 100