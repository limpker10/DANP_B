package com.example.applicationlab03.ui.forms.edit.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.applicationlab03.ui.forms.register.ui.RegisterScreen
import com.example.applicationlab03.ui.forms.register.ui.RegisterViewModel
import com.example.applicationlab03.ui.models.Person

@Composable
fun EditScreen(registerViewModel: RegisterViewModel,navController: NavHostController, viewModel: EditViewModel, id: String?) {

    if (id != null) {
        RegisterScreen(registerViewModel,navController,viewModel.findAttendee(id));
    }else {
        RegisterScreen(registerViewModel,navController,null);
    }
}