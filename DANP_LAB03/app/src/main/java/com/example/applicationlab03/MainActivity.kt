package com.example.applicationlab03

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.applicationlab03.ui.forms.register.ui.RegisterScreen
import com.example.applicationlab03.ui.lists.attends.ui.ListAttendeesScreen
import com.example.applicationlab03.ui.lists.attends.ui.ListAttendeesViewModel

class MainActivity : ComponentActivity() {
    private val viewListModel by viewModels<ListAttendeesViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "main") {
                composable("main") { RegisterScreen(navController) }
                composable("list") { ListAttendeesScreen(viewListModel) }
            }

        }
    }
}
