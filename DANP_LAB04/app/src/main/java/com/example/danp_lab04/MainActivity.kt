package com.example.danp_lab04

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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

                    val navController = rememberNavController()
                    val context = LocalContext.current
                    val repository = Repository(AppDatabase.getInstance(context.applicationContext))
                    NavHost(navController = navController, startDestination = "main") {
                        composable("main") { RoomSample(repository,navController) }
                        composable("listCourses") { UnitWithStudentScreen(repository,navController) }
                        /*...*/
                    }


                }
            }
        }
    }
}




@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun RoomSample(repository: Repository, navController:NavController) {
    val TAG: String = "RoomDatabase"
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val fillDataOnClick = {
            fillTables(repository, scope)
        }

        val studentsOnClick: () -> Unit = {
            scope.launch {
                repository.getAllStudents().forEach {
                    Log.d(TAG, it.toString())
                }
            }
        }
        val coursesOnClick: () -> Unit = {
            scope.launch {
                repository.getUnitsWithStudents().forEach {
                    Log.d(TAG, it.toString())

                }
            }
        }

        val booksOnClick: () -> Unit = {
            scope.launch {
                repository.getAllBooks().forEach {
                    Log.d(TAG, it.toString())
                }
            }
        }

        val studentWithBooksOnClick: () -> Unit = {
            scope.launch {
                repository.getStudentWithBooks() .forEach {
                    Log.d(TAG, it.toString())
                }
            }
        }

        // Nueva funcion para generar Cursos
        val CoursesSampleOnClick: () -> Unit = {
            scope.launch {
                repository.getUnitsWithStudents() .forEach {
                    Log.d(TAG, it.toString())
                }
            }
            navController.navigate("listCourses")
        }

        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = fillDataOnClick) {
            Text(text = "Fill student & book tables")
        }

        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = studentsOnClick) {
            Text(text = "Show students")
        }

        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = CoursesSampleOnClick) {
            Text(text = "Show Curses")
        }

        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = booksOnClick) {
            Text(text = "Show books")
        }

        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = studentWithBooksOnClick) {
            Text(text = "Student With Books")
        }
        Spacer(modifier = Modifier.height(10.dp))
        Button( colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7D5260)), onClick = fillDataOnClick) {
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

    //Se crearan 5 cursos

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
fun UnitWithStudentScreen(repository: Repository, navController: NavHostController) {
    var unitsWithStudents by remember { mutableStateOf<List<UnitWithStudent>>(emptyList()) }

    LaunchedEffect(Unit) {
        unitsWithStudents = repository.getUnitsWithStudents()
    }
    val groupedUnits = unitsWithStudents.groupBy { it.unit.name }
    LazyColumn {
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
        modifier = Modifier.padding(8.dp).fillMaxWidth(),colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondary,
        ),
    ) {
        Text(
            text = unitName,
            modifier = Modifier.padding(16.dp)
        )
    }
}


