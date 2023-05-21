package com.example.applicationlab03.ui.forms.register.ui

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.applicationlab03.ui.models.Person
import com.example.applicationlab03.ui.theme.ApplicationLab03Theme
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection

//@Preview(showSystemUi = true, showBackground = true,uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun RegisterScreen(viewModel: RegisterViewModel,navController: NavHostController, person: Person?) {
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
                Register(Modifier.align(Alignment.Center),navController,person, viewModel)
            }
        }
    }
}

@Composable
fun Register(
    modifier: Modifier,
    navController: NavHostController,
    person: Person?,
    viewModel: RegisterViewModel
) {
    var fullname by remember { mutableStateOf(person?.fullName ?: "") }
    var dateText by remember { mutableStateOf(person?.fullName ?: "") }
    var selectedText by remember { mutableStateOf(person?.fullName ?: "") }
    var telephone by remember { mutableStateOf(person?.fullName ?: "") }
    var email by remember { mutableStateOf(person?.fullName ?: "") }
    var amount by remember { mutableStateOf("") }

    var per = Person(fullname,dateText, selectedText, telephone, email, amount)

    Column(modifier = modifier,verticalArrangement = Arrangement.spacedBy(40.dp)) {
        FullName(fullname)

        Calendar(dateText)

        TypeBlood(selectedText)

        Telephone(telephone)

        Email(email)

        Amount(amount)

        SendButton(navController,viewModel,per)
    }
}

@Composable
fun SendButton(navController: NavHostController, viewModel: RegisterViewModel, per: Person) {
    Button(onClick = {
        viewModel.registerAttendee(per)
        navController.navigate("list") },
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
    ) {
        Text(text = "Send Register")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TypeBlood(selectedText: String) {
    val context = LocalContext.current
    val coffeeDrinks = arrayOf("O+", "A-", "B-", "C-", "D-")
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(selectedText) }

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
private fun FullName(person: String) {

    var fullname by remember { mutableStateOf(person) }
    TextField(
        value = fullname,
        modifier = Modifier.fillMaxWidth(),
        onValueChange = { newValue ->
            fullname = newValue
        },
        label = { Text(text = "Full Name") },
        placeholder = { Text(text = "Enter your full name") },
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun Calendar(dateText: String) {
    var dateText by remember { mutableStateOf(dateText) }
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
private fun Telephone(telephone: String) {
    var telephone by remember { mutableStateOf(telephone) }
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
private fun Email(email: String) {
    var email by remember { mutableStateOf(email) }
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
private fun Amount(amount: String) {
    var amountPaid by remember { mutableStateOf(amount) }
    TextField(
        value = amountPaid,
        modifier = Modifier.fillMaxWidth(),
        onValueChange = { newValue ->
            amountPaid = newValue
        },
        label = { Text(text = "Amount Paid") },
        placeholder = { Text(text = "Enter the amount paid") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        keyboardActions = KeyboardActions(onDone = {
            val parsedAmount = amountPaid.toDoubleOrNull()
            if (parsedAmount != null) {
                amountPaid = parsedAmount.toString()
            }
        })
    )
}

