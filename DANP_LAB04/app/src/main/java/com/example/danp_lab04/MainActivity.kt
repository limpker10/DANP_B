package com.example.danp_lab04

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.danp2023room.entities.BookEntity
import com.example.danp2023room.entities.StudentEntity
import com.example.danp2023room.model.AppDatabase
import com.example.danp2023room.model.Repository
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

                    RoomSample(repository)

                }
            }
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun RoomSample(repository: Repository) {
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

        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = fillDataOnClick) {
            Text(text = "Fill student & book tables")
        }

        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = studentsOnClick) {
            Text(text = "Show students")
        }

        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = booksOnClick) {
            Text(text = "Show books")
        }

        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = studentWithBooksOnClick) {
            Text(text = "Student With Books")
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


}
