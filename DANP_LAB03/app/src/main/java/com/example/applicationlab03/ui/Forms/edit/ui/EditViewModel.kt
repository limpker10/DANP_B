package com.example.applicationlab03.ui.forms.edit.ui

import androidx.lifecycle.ViewModel
import com.example.applicationlab03.ui.models.Person
import com.example.applicationlab03.ui.models.PersonRepository

class EditViewModel(private val personRepository: PersonRepository): ViewModel() {
    fun findAttendee(id: String) : Person? {
        return personRepository.findAttendee(id)
    }
}