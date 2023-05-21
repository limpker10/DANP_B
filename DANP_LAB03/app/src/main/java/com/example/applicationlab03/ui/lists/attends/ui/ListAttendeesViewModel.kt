package com.example.applicationlab03.ui.lists.attends.ui
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.applicationlab03.ui.models.Person
import com.example.applicationlab03.ui.models.PersonRepository
import kotlinx.coroutines.launch


class ListAttendeesViewModel(private val personRepository: PersonRepository) : ViewModel() {

    private var persons = mutableStateListOf<Person>()
    val attendees: List<Person> get() = persons

    init {
        viewModelScope.launch {
            persons.addAll(personRepository.getAllAttendees())
        }
    }
    fun addAttendee(attendee: Person) {
        personRepository.addAttendee(attendee)
    }
    fun deleteAttendee(attendee: Person) {
        personRepository.deleteAttendee(attendee)
    }
}