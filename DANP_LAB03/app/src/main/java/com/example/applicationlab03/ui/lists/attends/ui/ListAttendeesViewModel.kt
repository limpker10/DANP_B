package com.example.applicationlab03.ui.lists.attends.ui
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.applicationlab03.ui.models.Person
import kotlinx.coroutines.launch


class ListAttendeesViewModel : ViewModel() {
    private var persons = mutableStateListOf<Person>()
    val attendees: List<Person> get() = persons

    init {
        viewModelScope.launch {
            persons.add(
                Person("John Doe", "2023-05-20", "A+", "1234567890", "john.doe@example.com", 100.0)
            )
            persons.add(
                Person("John Doe", "2023-05-20", "A+", "1234567890", "john.doe@example.com", 100.0)
            )
            persons.add(
                Person("John Doe", "2023-05-20", "A+", "1234567890", "john.doe@example.com", 100.0)
            )
            persons.add(
                Person("John Doe", "2023-05-20", "A+", "1234567890", "john.doe@example.com", 100.0)
            )
        }
    }
    fun deleteAttendee(attendee: Person) {
        persons.remove(attendee)
    }
}