package com.example.applicationlab03

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.applicationlab03.ui.forms.edit.ui.EditScreen
import com.example.applicationlab03.ui.forms.edit.ui.EditViewModel
import com.example.applicationlab03.ui.forms.register.ui.RegisterScreen
import com.example.applicationlab03.ui.forms.register.ui.RegisterViewModel
import com.example.applicationlab03.ui.lists.attends.ui.ListAttendeesScreen
import com.example.applicationlab03.ui.lists.attends.ui.ListAttendeesViewModel
import com.example.applicationlab03.ui.models.PersonRepository

class MainActivity : ComponentActivity() {
    private val personRepository = PersonRepository
    private val listAttendeesViewModel = ListAttendeesViewModel(personRepository)
    private val registerViewModel = RegisterViewModel(personRepository)
    private val editViewModel = EditViewModel(personRepository)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "list") {
                composable("main/{id}", arguments = listOf(navArgument("id"){type = NavType.StringType})) {
                    backStackEntry -> val id = backStackEntry.arguments?.getString("id")
                    EditScreen(registerViewModel,navController,editViewModel,id)
                }
                composable("list") {
                    ListAttendeesScreen(listAttendeesViewModel,navController)
                }
                composable("register") {
                    RegisterScreen(registerViewModel,navController,null)
                }
            }

        }
    }
}
