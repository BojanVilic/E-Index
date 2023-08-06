package com.example.e_index.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.e_index.navigation.LOGIN
import com.example.e_index.navigation.TopLevelDestinations
import com.example.e_index.navigation.addScreen
import com.example.e_index.navigation.components.TabRow
import com.example.e_index.navigation.loginScreen
import com.example.e_index.navigation.searchScreen
import com.example.e_index.ui.theme.EIndexTheme
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EIndexTheme {

                val allScreens = TopLevelDestinations.values().toList()
                val navController = rememberNavController()
                val backstackEntry = navController.currentBackStackEntryAsState()
                val currentScreen: TopLevelDestinations? = TopLevelDestinations.fromRoute(backstackEntry.value?.destination?.route)

                Scaffold(
                    bottomBar = {
                        if (currentScreen != null) {
                            TabRow(
                                allScreens = allScreens,
                                onTabSelected = { screen ->
                                    navController.navigate(screen.route)
                                },
                                currentScreen = currentScreen
                            )
                        }
                    }
                ) { paddingValues ->
                    AppNavHost(
                        navController = navController,
                        modifier = Modifier.padding(paddingValues)
                    )
                }
            }
        }
    }
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = LOGIN,
        modifier = modifier
    ) {
        loginScreen(navController)
        searchScreen(navController)
        addScreen(navController)
    }
}