package com.example.applicationlab03.ui.lists.attends.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.applicationlab03.ui.models.Person
import androidx.compose.ui.Alignment
import androidx.navigation.NavHostController
import com.example.applicationlab03.ui.theme.ApplicationLab03Theme


@Composable
fun ListAttendeesScreen(viewModel: ListAttendeesViewModel, navController: NavHostController) {
    ApplicationLab03Theme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            if (viewModel.attendees.isNotEmpty()) {
                AttendeesList(viewModel.attendees, viewModel,navController)
                Box(
                    contentAlignment = Alignment.BottomEnd,
                    modifier = Modifier.padding(16.dp)
                ){
                    FloatingActionButton(
                        onClick = {
                            navController.navigate("register")
                        },
                    ) {
                        Icon(Icons.Filled.Add, "Localized description")
                    }
                }
            } else {
                EmptyListMessage()
            }
        }
    }
}

@Composable
fun AttendeesList(
    attendees: List<Person>,
    viewModel: ListAttendeesViewModel,
    navController: NavHostController
) {
    LazyColumn {
        items(attendees) { person ->
            AnimatedVisibility(
                visible = true,
                enter = fadeIn(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 5.dp)
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    AttendeeItem(person, viewModel,navController)
                }
            }
        }
    }

}

@Composable
fun AttendeeItem(
    attendee: Person,
    viewModel: ListAttendeesViewModel,
    navController: NavHostController
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "ID: ${attendee.personId}")
            Text(text = "Full Name: ${attendee.fullName}")
            Text(text = "Registration Date: ${attendee.registrationDate}")
            Text(text = "Blood Type: ${attendee.bloodType}")
            Text(text = "Phone: ${attendee.phone}")
            Text(text = "Email: ${attendee.email}")
            Text(text = "Amount Paid: ${attendee.amountPaid}")

            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                Button(onClick = {
                    viewModel.deleteAttendee(attendee)
                }) {
                    Text(text = "Delete")
                }

                Button(onClick = {
                    navController.navigate("main/${attendee.personId}")
                }) {
                    Text(text = "edit")
                }
            }
        }
    }
}

@Composable
fun EmptyListMessage() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "No attendees registered")
    }
}
