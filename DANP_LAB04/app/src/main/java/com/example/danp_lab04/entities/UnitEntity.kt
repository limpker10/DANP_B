package com.example.danp2023room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "unit")
data class UnitEntity(
    @PrimaryKey(autoGenerate = true)
    val unitId: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "credit")
    val credit: Int,

    @ColumnInfo(name = "studentId")
    val studentId: Int

)
{
    constructor(name: String,credit: Int,studentId: Int) : this(0, name,credit,studentId)
}