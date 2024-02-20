package com.example.apolloproject.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.apolloproject.ui.screens.navigation.navigationAct
import com.example.apolloproject.ui.theme.ApolloProjectTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ApolloProjectTheme {
                navigationAct()
            }

        }
    }
}
