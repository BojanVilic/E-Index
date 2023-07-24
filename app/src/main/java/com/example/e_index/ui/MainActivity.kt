package com.example.e_index.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import com.example.e_index.ui.login.LoginScreen
import com.example.e_index.ui.theme.EIndexTheme
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EIndexTheme {
                Scaffold { paddingValues ->
                    LoginScreen(paddingValues)
                }
            }
        }
    }
}