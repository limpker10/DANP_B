package com.example.danp_lab04

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.danp2023room.entities.BookEntity
import com.example.danp2023room.entities.StudentEntity
import com.example.danp2023room.entities.UnitEntity
import com.example.danp2023room.model.AppDatabase
import com.example.danp2023room.model.Repository
import com.example.danp_lab04.entities.UnitWithStudent
import com.example.danp_lab04.ui.theme.DANP_LAB04Theme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DANP_LAB04Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val context = LocalContext.current
                    val repository = Repository(AppDatabase.getInstance(context.applicationContext))
                    var ListCourses by remember { mutableStateOf<List<UnitWithStudent>>(emptyList()) }
                    val scope = rememberCoroutineScope()
                    var isLoadingList by remember { mutableStateOf(false) }
                    LaunchedEffect(Unit) {
                        ListCourses = repository.getUnitsWithStudents()
                        if (ListCourses.isEmpty()){
                            isLoadingList = true
                        }
                    }
                    Column(modifier = Modifier.fillMaxWidth().padding(32.dp)) {

                        Box(modifier = Modifier.fillMaxWidth()) {
                            RoomSample(repository,scope,isLoadingList,onListUpdated = {
                                // Actualizar la lista de unitsWithStudents
                                scope.launch  {
                                    ListCourses = repository.getUnitsWithStudents()
                                    isLoadingList = false
                                }
                            })
                        }
                        Box(modifier = Modifier.fillMaxWidth() ){
                            CoursesListScreen(repository,ListCourses)
                        }

                    }



                }
            }
        }
    }
}





@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun RoomSample(repository: Repository, scope:CoroutineScope, isLoadingList : Boolean, onListUpdated: () -> Unit) {
    val TAG: String = "RoomDatabase"

    Column(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val fillDataOnClick: () -> Unit = {
            fillTables(repository,scope)
            onListUpdated()
        }

        Spacer(modifier = Modifier.height(10.dp))
        Button(modifier = Modifier.fillMaxWidth(),
            onClick = fillDataOnClick,
            enabled = isLoadingList
        ) {
            Text(text = "Generate Table Curses")
        }

    }
}

fun fillTables(rep: Repository, scope: CoroutineScope) {

    val students = ArrayList<StudentEntity>()

    for (i in 100..120) {
        val studentEntity = StudentEntity(i, fullname = "Student " + i.toString())
        students.add(studentEntity)
    }

    scope.launch {
        rep.insertStudents(students)
    }
    for (i in 0..20) {
        val studentId = Random.nextInt(100, 120)
        val bookEntity = BookEntity(name = "Book " + i.toString(), studentId)
        scope.launch {
            rep.insertBook(bookEntity)
        }
    }

    val courses = listOf("Tecnologia de Informacion", "Fundamentos de Programacion", "Organizacion Metodos", "Programacion Web", "Estructura de Datos")
    for (element in courses) {

        for (j in 1..4) {
            val studentId = Random.nextInt(100, 120)
            val unit = UnitEntity(name = element, credit = Random.nextInt(2, 4),studentId)
            scope.launch {
                rep.insertUnit(unit)
            }
        }
    }
}

@Composable
fun CoursesListScreen(repository: Repository, unitsWithStudents: List<UnitWithStudent>) {
    val groupedUnits = unitsWithStudents.groupBy { it.unit.name }
    LazyColumn {
        if (unitsWithStudents.isEmpty()) {
            item {
                Text("No se tiene ningún curso")
            }
        } else {
            groupedUnits.forEach { (unitName, unitWithStudents) ->
                item {
                    CoursesGroup(unitName)
                }
                items(unitWithStudents) { unitWithStudent ->
                    CoursesCardList(unitWithStudent)
                }
            }
        }
    }
}

@Composable
fun CoursesCardList(unitWithStudent: UnitWithStudent) {
    Card(
        modifier = Modifier.padding(8.dp),
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = unitWithStudent.students.joinToString(", ") { it.fullname })
        }
    }
}

@Composable
fun CoursesGroup(unitName: String) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondary,
        ),
    ) {
        Text(
            text = unitName,
            modifier = Modifier.padding(16.dp)
        )
    }
}


