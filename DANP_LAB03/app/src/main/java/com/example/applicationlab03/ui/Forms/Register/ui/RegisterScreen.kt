package com.example.applicationlab03.ui.forms.register.ui

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.applicationlab03.ui.theme.ApplicationLab03Theme
import java.util.Calendar
import java.util.Date

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun RegisterScreen (){
    ApplicationLab03Theme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
            ){
                Text(text = "Registro de Datos")
                Register(Modifier.align(Alignment.Center))
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Register(modifier: Modifier) {
    var name by remember {
        mutableStateOf("")
    }
    Column(modifier = modifier) {
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text(text = "Full Name") },
            placeholder = { Text(text = "Enter your full name") },
        )
    }
}