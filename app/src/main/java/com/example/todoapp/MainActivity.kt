package com.example.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                ToDoScreen()
            }
        }
    }
}

data class Task(
    val title: String,
    val completed: MutableState<Boolean> = mutableStateOf(false)
)

@Composable
fun ToDoScreen() {

    var taskText by remember {
        mutableStateOf("")
    }

    val taskList = remember {
        mutableStateListOf<Task>()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "My Tasks",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {

            OutlinedTextField(
                value = taskText,
                onValueChange = {
                    taskText = it
                },
                label = {
                    Text("Enter Task")
                },
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = {

                    if (taskText.isNotEmpty()) {

                        taskList.add(
                            Task(taskText)
                        )

                        taskText = ""
                    }
                }
            ) {
                Text("Add")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Total Tasks: ${taskList.size}"
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn {

            itemsIndexed(taskList) { index, task ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Row {

                            Checkbox(
                                checked = task.completed.value,
                                onCheckedChange = {
                                    task.completed.value = it
                                }
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            Text(
                                text = task.title,
                                textDecoration =
                                    if (task.completed.value)
                                        TextDecoration.LineThrough
                                    else
                                        TextDecoration.None
                            )
                        }

                        Button(
                            onClick = {
                                taskList.removeAt(index)
                            }
                        ) {
                            Text("Delete")
                        }
                    }
                }
            }
        }
    }
}