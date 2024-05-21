package com.quocanle.jetpacknoteapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.quocanle.jetpacknoteapp.ui.theme.JetpackNoteAppTheme
import com.quocanle.jetpacknoteapp.viewmodel.NoteViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.lifecycle.viewmodel.compose.viewModel
import com.quocanle.jetpacknoteapp.model.Note
import java.text.SimpleDateFormat

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            JetpackNoteAppTheme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Greeting(
//                        name = "Android",
//                        modifier = Modifier.padding(innerPadding)
//                    )
//                }
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val noteViewModel = viewModel<NoteViewModel>()

                    Column(
                        modifier = Modifier
                            .padding(5.dp)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        topBar()
                        mainForm(noteViewModel = noteViewModel)
                        Divider(modifier = Modifier.padding(10.dp))
                        listNote(noteViewModel = noteViewModel)
                    }
                }
            }
        }
    }
}

//@Preview(showBackground = true)
@Composable
fun topBar() {
    androidx.compose.material3.Surface(
        modifier = Modifier
            .padding(5.dp)
            .height(50.dp),
        color = Color(0xFFBAD6FF),

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.padding(start = 10.dp)
        ) {
            Text(
                text = "Note",
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Row(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.End,
            ) {
                Icon(
                    imageVector = Icons.Filled.Notifications,
                    contentDescription = "Notification",
                )
            }
        }
    }
}

@Composable
fun mainForm(noteViewModel: NoteViewModel) {
    val title = remember {
        mutableStateOf("")
    }

    val content = remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier
            .padding(10.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = title.value,
            modifier = Modifier.padding(5.dp),
            onValueChange = {
                text -> title.value = text
            },
            label = { Text("Title") }
        )
    }
    Column(
        modifier = Modifier
            .padding(10.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = content.value,
            modifier = Modifier.padding(5.dp),
            onValueChange = {
                    text -> content.value = text
            },
            label = { Text("Add a note") }
        )
        Button(
            onClick = {
                noteViewModel.addNote(Note(
                    title = title.value,
                    description = content.value,
                    entryDate = java.util.Date()
                ))
            },
            elevation = ButtonDefaults.elevatedButtonElevation(5.dp, 0.5.dp),
            modifier = Modifier.padding(5.dp),
            shape = RoundedCornerShape(20.dp),
//            colors = ButtonDefaults.buttonColors(contentColor = Color.White, backgroundColor = Color(0xFF2B2D30))
        ) {
            Text(text = "Save")
        }
    }
}

@Composable
fun listNote(noteViewModel: NoteViewModel) {
    val data by noteViewModel.noteList.collectAsState()
    LazyColumn {
        items(data) { item ->
            Surface(
                modifier = Modifier
                    .padding(5.dp)
                    .clip(RoundedCornerShape(topStart = 30.dp, bottomEnd = 30.dp))
                    .fillMaxSize(),
                color = Color(0xFF2B2D30)
            ) {
                Row() {
                    Column(
                        modifier = Modifier.padding(10.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            modifier = Modifier.padding(3.dp),
                            text = item.title,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White)
                        Text(
                            modifier = Modifier.padding(3.dp),
                            text = SimpleDateFormat("dd.MM.yyyy G 'at' HH:mm:ss z").format(item.entryDate),
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White)
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JetpackNoteAppTheme {
        Greeting("Android")
    }
}