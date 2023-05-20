package com.example.applicationlab03

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.applicationlab03.ui.forms.register.ui.RegisterScreen
import com.example.applicationlab03.ui.lists.attends.ui.ListAttendeesScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "main") {
                composable("main") { RegisterScreen(navController) }
                composable("list") { ListAttendeesScreen() }
            }

        }
    }
}
