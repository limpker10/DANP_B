package com.example.applicationlab03.ui.models

object PersonRepository {
    private val attendees = mutableListOf<Person>()

    init {
        attendees.add(
            Person("John Doe", "2023-05-20", "A+", "1234567890", "john.doe@example.com", 100.0)
        )
        attendees.add(
            Person("John Doe", "2023-05-20", "A+", "1234567890", "john.doe@example.com", 100.0)
        )
        attendees.add(
            Person("John Doe", "2023-05-20", "A+", "1234567890", "john.doe@example.com", 100.0)
        )
        attendees.add(
            Person("John Doe", "2023-05-20", "A+", "1234567890", "john.doe@example.com", 100.0)
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

    fun deleteAttendee(attendee: Person) {
        attendees.remove(attendee)
    }
}