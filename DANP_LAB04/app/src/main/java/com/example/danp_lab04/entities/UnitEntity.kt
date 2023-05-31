package com.example.danp2023room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "unit")
data class UnitEntity(
    @PrimaryKey(autoGenerate = true)
    val unitId: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "credit")
    val credit: Int
)