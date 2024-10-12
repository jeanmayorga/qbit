package com.example.todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import com.example.todolist.components.CreateTodoDialog
import com.example.todolist.ui.theme.TodoListTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoListTheme {
                TodoListApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListApp() {
    var showDialog by remember { mutableStateOf(false) }
    var showMenu by remember { mutableStateOf(false) }
    var todos by rememberSaveable { mutableStateOf(listOf<Todo>()) }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    fun createTodo(name: String) {
        if (name.isEmpty()) return
        val newTodo = Todo(name = name)
        todos = todos + newTodo
    }

    fun createMockTodos() {
        val mockTodos = listOf(
            Todo(name = "Mock Todo 1"),
            Todo(name = "Mock Todo 2"),
            Todo(name = "Mock Todo 3"),
            Todo(name = "Mock Todo 4"),
            Todo(name = "Mock Todo 5"),
            Todo(name = "Mock Todo 6"),
            Todo(name = "Mock Todo 7"),
            Todo(name = "Mock Todo 8"),
            Todo(name = "Mock Todo 9"),
            Todo(name = "Mock Todo 10")
        )
        todos = todos + mockTodos
    }

    fun deleteTodo(id: String) {
        todos = todos.filter { todo -> todo.id != id }
    }

    fun deleteAllTodos () {
        todos = emptyList()
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MediumTopAppBar(
                title = { Text(text = "TodoList") },
                actions = {
                    Box {
                        IconButton(onClick = { showMenu = true }) {
                            Icon(
                                imageVector = Icons.Filled.MoreVert,
                                contentDescription = "Open menu"
                            )
                        }
                        DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }) {
                            DropdownMenuItem(
                                text = { Text("Delete all") },
                                onClick = {
                                    deleteAllTodos()
                                    showMenu = false
                                },
                                leadingIcon = {
                                    Icon(Icons.Outlined.Delete, contentDescription = "Delete all")
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Create mock todos") },
                                onClick = {
                                    createMockTodos()
                                    showMenu = false
                                },
                                leadingIcon = {
                                    Icon(Icons.Outlined.Menu, contentDescription = "Create mock todos")
                                }
                            )
                        }
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { showDialog = true },
                icon = { Icon(Icons.Filled.Add, "Create todo") },
                text = { Text(text = "Create todo") },
            )
        },
        content = { paddingValues ->
            if (todos.isEmpty()) {
                Text(
                    text = "No todos created.",
                    modifier = Modifier
                        .padding(paddingValues)
                        .padding(horizontal = 20.dp)
                )
            } else {
                LazyColumn(
                    contentPadding = paddingValues,
                ) {
                    itemsIndexed(
                        todos.sortedByDescending { it.createdAt },
                        key = { index, _ -> index }
                    ) { index, todo ->
                        ListItem(
                            overlineContent = { Text(text = todo.getCreatedTime()) },
                            leadingContent = { Text(text = (index + 1).toString()) },
                            headlineContent = { Text(text = todo.name) },
                            trailingContent = {
                                IconButton(onClick = { deleteTodo(todo.id) }) {
                                    Icon(
                                        imageVector = Icons.Outlined.Delete,
                                        contentDescription = "Delete todo"
                                    )
                                }
                            },
                        )
                    }
                }
            }

            CreateTodoDialog(
                expanded = showDialog,
                onDismissRequest = { showDialog = false },
                onConfirmation = { name ->
                    createTodo(name)
                    showDialog = false
                }
            )
        }
    )
}