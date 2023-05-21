package com.example.applicationlab03.ui.models

data class Person(
    val personId: String,
    val fullName: String,
    val registrationDate: String,
    val bloodType: String,
    val phone: String,
    val email: String,
    val amountPaid: String
){
    constructor(
        fullName: String,
        registrationDate: String,
        bloodType: String,
        phone: String,
        email: String,
        amountPaid: String
    ) : this("", fullName, registrationDate, bloodType, phone, email, amountPaid)
}