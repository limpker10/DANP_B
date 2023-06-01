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
                    var unitsWithStudents by remember { mutableStateOf<List<UnitWithStudent>>(emptyList()) }
                    val scope = rememberCoroutineScope()
                    Column(modifier = Modifier.fillMaxWidth().padding(32.dp)) {

                        Box(modifier = Modifier.fillMaxWidth()) {
                            RoomSample(repository,scope,onListUpdated = {
                                // Actualizar la lista de unitsWithStudents

                                scope.launch  {
                                    unitsWithStudents = repository.getUnitsWithStudents()
                                }
                            })

                        }
                        Box(modifier = Modifier.fillMaxWidth() ){
                            UnitWithStudentScreen(repository,unitsWithStudents)
                        }
                    }



                }
            }
        }
    }
}





@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun RoomSample(repository: Repository, scope:CoroutineScope, onListUpdated: () -> Unit) {
    val TAG: String = "RoomDatabase"
    var isLoading by remember { mutableStateOf(true) }
    Column(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val fillDataOnClick: () -> Unit = {
            fillTables(repository,scope)
            onListUpdated() // Llamar a la función de actualización después de llenar las tablas
            isLoading = false
        }

        Spacer(modifier = Modifier.height(10.dp))
        Button(modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7D5260)),
            onClick = fillDataOnClick,
            enabled = isLoading
        ) {
            Text(text = "Generate sample Curses", color = Color.White)
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

    val courseNames = listOf("Math", "English", "Science", "History")

    for (element in courseNames) {

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
fun UnitWithStudentScreen(repository: Repository, unitsWithStudents: List<UnitWithStudent>) {
    //var unitsWithStudents by remember { mutableStateOf<List<UnitWithStudent>>(emptyList()) }

//    LaunchedEffect(Unit) {
//        unitsWithStudents = repository.getUnitsWithStudents()
//    }
    val groupedUnits = unitsWithStudents.groupBy { it.unit.name }
    LazyColumn {
        if (unitsWithStudents.isEmpty()) {
            item {
                Text("No se tiene ningún item en la pantalla")
            }
        } else {
            groupedUnits.forEach { (unitName, unitWithStudents) ->
                item {
                    UnitGroupHeader(unitName)
                }
                items(unitWithStudents) { unitWithStudent ->
                    UnitWithStudentCard(unitWithStudent)
                }
            }
        }
    }
}

@Composable
fun UnitWithStudentCard(unitWithStudent: UnitWithStudent) {
    Card(
        modifier = Modifier.padding(8.dp),
    ) {
        // Customize the card UI as per your requirement
        // For example:
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = unitWithStudent.students.joinToString(", ") { it.fullname })
        }
    }
}

@Composable
fun UnitGroupHeader(unitName: String) {
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


