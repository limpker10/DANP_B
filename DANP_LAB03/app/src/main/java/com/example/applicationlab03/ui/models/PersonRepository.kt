package com.example.applicationlab03.ui.models

object PersonRepository {
    private val attendees = mutableListOf<Person>()

    init {
        attendees.add(
            Person("AC23400","Jose Miguel", "2023-05-20", "A+", "1234567890", "john.doe@example.com", "24.34")
        )
        attendees.add(
            Person("AC21423","Jose Talavera", "2023-05-20", "A+", "1234567890", "john.doe@example.com", "234.34")
        )
        attendees.add(
            Person("AC23223","Joel Durand", "2023-05-20", "A+", "1234567890", "john.doe@example.com", "34.34")
        )
        attendees.add(
            Person("AC27283","Cesar Daniel", "2023-05-20", "A+", "1234567890", "john.doe@example.com", "34.34")
        )
    }

    //Simulation fetch the data from the database
    fun getAllAttendees(): List<Person> {
        return attendees
    }

    fun addAttendee(attendee: Person) {
        attendees.add(attendee)
    }

    fun updateAttendee(attendee: Person) {
        val index = attendees.indexOfFirst { it.fullName == attendee.fullName }
        if (index != -1) {
            attendees[index] = attendee
        }
    }

    fun findAttendee(id: String) : Person? {
        val index = attendees.indexOfFirst { it.personId == id }
        if (index != -1) {
            return attendees[index]
        }
        return null
    }

    fun deleteAttendee(attendee: Person) {
        attendees.remove(attendee)
    }
}