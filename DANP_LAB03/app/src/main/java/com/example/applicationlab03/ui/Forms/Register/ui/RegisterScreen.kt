package com.example.applicationlab03.ui.forms.register.ui

import android.content.res.Configuration
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection

@Preview(showSystemUi = true, showBackground = true,uiMode = Configuration.UI_MODE_NIGHT_YES)
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
                    .padding(15.dp),
            ){
                Text(text = "Registro de Datos")
                Register(Modifier.align(Alignment.Center))
            }
        }
    }
}

@Composable
fun Register(modifier: Modifier) {
    Column(modifier = modifier,verticalArrangement = Arrangement.spacedBy(40.dp)) {
        FullName()

        Calendar()

        TypeBlood()

        Telephone()

        Email()

        Amount()

        SendButton()
    }
}

@Composable
fun SendButton() {
    Button(onClick = { /*TODO*/ },
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
    ) {
        Text(text = "Send Register")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TypeBlood() {
    val context = LocalContext.current
    val coffeeDrinks = arrayOf("O+", "A-", "B-", "C-", "D-")
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("") }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        TextField(
            value = selectedText,
            onValueChange = {},
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor(),
            placeholder = { Text(text = "Type Blood") },
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            coffeeDrinks.forEach { item ->
                DropdownMenuItem(
                    text = { Text(text = item) },
                    onClick = {
                        selectedText = item
                        expanded = false
                        Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun FullName() {
    var fullname by remember { mutableStateOf("") }
    TextField(
        value = fullname,
        modifier = Modifier.fillMaxWidth(),
        onValueChange = { fullname = it },
        label = { Text(text = "Full Name") },
        placeholder = { Text(text = "Enter your full name") },
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun Calendar() {
    var dateText by remember { mutableStateOf("") }
    val calendarState =  rememberSheetState()
    CalendarDialog (
        state = calendarState,
        selection = CalendarSelection.Date{ date ->
            Log.d("SeleccionDate","$date")
            dateText = date.toString()
        },
        config =  CalendarConfig (
            monthSelection = true,
            yearSelection = true
        )
    )
    var dateText1 = dateText
    TextField(
        value = dateText1,
        modifier = Modifier.fillMaxWidth(),
        readOnly = true,
        leadingIcon = {
            Icon(imageVector = Icons.Filled.DateRange,
                contentDescription = "emailIcon",
                modifier = Modifier.clickable { calendarState.show() })
        },
        onValueChange = { dateText1 = it },
        label = { Text(text = "registration date") },
        placeholder = { Text(text = "Enter your date") },
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun Telephone() {
    var telephone by remember { mutableStateOf("") }
    TextField(
        value = telephone,
        modifier = Modifier.fillMaxWidth(),
        onValueChange = { telephone = it },
        label = { Text(text = "Telephone") },
        placeholder = { Text(text = "Enter your telephone") },
    )
}
@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun Email() {
    var email by remember { mutableStateOf("") }
    TextField(
        value = email,
        modifier = Modifier.fillMaxWidth(),
        onValueChange = { email = it },
        label = { Text(text = "Email") },
        placeholder = { Text(text = "Enter your email") },
    )
}
@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun Amount() {
    var amount by remember { mutableStateOf("") }
    TextField(
        value = amount,
        modifier = Modifier.fillMaxWidth(),
        onValueChange = { amount = it },
        label = { Text(text = "Amount") },
        placeholder = { Text(text = "Enter your amount") },
    )
}

