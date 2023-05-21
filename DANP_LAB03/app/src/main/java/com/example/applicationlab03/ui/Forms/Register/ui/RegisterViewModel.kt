package com.example.applicationlab03.ui.forms.register.ui


import androidx.lifecycle.ViewModel
import com.example.applicationlab03.ui.models.Person
import com.example.applicationlab03.ui.models.PersonRepository

class RegisterViewModel (private val personRepository: PersonRepository): ViewModel(){

    fun registerAttendee(attendee: Person) {
        personRepository.addAttendee(attendee)
    }
}