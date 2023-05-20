package com.example.applicationlab03.ui.forms.register.ui

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.applicationlab03.ui.models.Person
import com.example.applicationlab03.ui.models.PersonRepository

class RegisterViewModel : ViewModel(){
    private val people = mutableStateListOf<Person>()
    val attendees: List<Person> get() = people

    init {
        people.addAll(PersonRepository.getAllAttendees())
    }

    fun addAttendee(attendee: Person) {
        PersonRepository.addAttendee(attendee)
        people.addAll(PersonRepository.getAllAttendees())
    }

    fun updateAttendee(attendee: Person) {
        PersonRepository.updateAttendee(attendee)
        people.addAll(PersonRepository.getAllAttendees())
    }

    fun deleteAttendee(attendee: Person) {
        PersonRepository.deleteAttendee(attendee)
        people.addAll(PersonRepository.getAllAttendees())
    }
}