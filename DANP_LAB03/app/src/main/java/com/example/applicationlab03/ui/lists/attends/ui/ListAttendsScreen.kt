package com.example.applicationlab03.ui.lists.attends.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ListAttendeesScreen(){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
    ){
        Text(text = "Hola mundo")
    }
}