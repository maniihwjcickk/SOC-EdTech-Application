package com.example.week2

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Locale

val colourList = listOf(
    Color(red = 0xFF, green = 0xA7, blue = 0xA6), // Soft Coral
    Color(red = 0xA3, green = 0xD2, blue = 0xCA), // Pastel Teal
    Color(red = 0xFF, green = 0xE0, blue = 0xAC), // Soft Apricot
    Color(red = 0xC2, green = 0xB9, blue = 0xFF), // Light Lavender
    Color(red = 0xFF, green = 0xC1, blue = 0xCC), // Baby Pink
    Color(red = 0xB8, green = 0xE1, blue = 0xFF), // Sky Blue
    Color(red = 0xDC, green = 0xED, blue = 0xC1), // Light Olive Green
    Color(red = 0xFD, green = 0xD2, blue = 0xA2), // Peach
    Color(red = 0xFF, green = 0xAF, blue = 0xCC), // Light Rose
    Color(red = 0xC5, green = 0xE1, blue = 0xA5)  // Pale Green
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Todolistpage(viewModel: TodoViewModel) {
    val todolist by viewModel.todolist.observeAsState(initial = arrayListOf())
    var inputText by remember { mutableStateOf("") }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("ToDo - List") },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(painter = painterResource(id = R.drawable.check)
                            , contentDescription = "CheckIcon")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(red = 0xB8, green = 0xE1, blue = 0xFF),
                    titleContentColor = Color.Black
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                    ,
                    value = inputText,
                    onValueChange = { inputText = it },
                    label = { Text("Enter task") }
                )
                Button(
                    onClick = {
                        if (inputText.isNotBlank()) {
                            viewModel.addTodo(inputText)
                            inputText = ""
                        }
                    },
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .align(Alignment.CenterVertically)
                ) {
                    Text(text = "Add")
                }
            }

            if (todolist.isEmpty()) {
                Text(
                    text = "No items yet",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp
                )
            } else {
                Text(
                    text = "Long press on a task to delete it",
                    fontSize = 12.sp,
                    color = Color.Gray,
                    modifier = Modifier
                        .padding(top = 4.dp, start = 12.dp)
                )
                LazyColumn(
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    itemsIndexed(todolist) { _, item ->
                        TodoItem(
                            item = item,
                            onDeleteClick = { viewModel.deleteTodo(it)},
                            onCheckedChange = {viewModel.toggleTodo(item.id)}
                        )
                    }
                }
            }
        }
    }
}

@Composable

fun TodoItem(item: todo, onDeleteClick: (Int) -> Unit, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(color = colourList[item.id % colourList.size])
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {
                        onDeleteClick(item.id)
                    }
                )
            }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = item.isDone,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(
                checkedColor = Color(0xFFA3D2CA),
                uncheckedColor = Color.Gray,
                checkmarkColor = Color.White
            )
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault()).format(item.createdAt),
                fontSize = 12.sp,
                color = Color.DarkGray
            )
            Text(
                text = item.title,
                fontSize = 20.sp,
                color = Color.Black,
                style = if (item.isDone) {
                    TextStyle(textDecoration = TextDecoration.LineThrough)
                } else {
                    TextStyle.Default
                }
            )
        }
    }
}

