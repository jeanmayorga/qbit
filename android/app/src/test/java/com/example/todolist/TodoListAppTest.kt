package com.example.todolist

import org.junit.Assert.assertEquals
import org.junit.Test

class TodoListAppTest {

    @Test
    fun testCreateTodo() {
        val todos = mutableListOf<Todo>()
        val newTodo = Todo(name = "Test Todo")
        todos.add(newTodo)
        assertEquals(1, todos.size)
        assertEquals("Test Todo", todos[0].name)
    }

    @Test
    fun testCreateMockTodos() {
        val todos = mutableListOf<Todo>()
        val mockTodos = listOf(
            Todo(name = "Mock Todo 1"),
            Todo(name = "Mock Todo 2"),
            Todo(name = "Mock Todo 3")
        )
        todos.addAll(mockTodos)
        assertEquals(3, todos.size)
        assertEquals("Mock Todo 1", todos[0].name)
        assertEquals("Mock Todo 2", todos[1].name)
        assertEquals("Mock Todo 3", todos[2].name)
    }

    @Test
    fun testDeleteTodo() {
        val todos = mutableListOf(
            Todo(name = "Test Todo 1"),
            Todo(name = "Test Todo 2")
        )
        val todoToDelete = todos[0]
        todos.remove(todoToDelete)
        assertEquals(1, todos.size)
        assertEquals("Test Todo 2", todos[0].name)
    }

    @Test
    fun testDeleteAllTodos() {
        val todos = mutableListOf(
            Todo(name = "Test Todo 1"),
            Todo(name = "Test Todo 2")
        )
        todos.clear()
        assertEquals(0, todos.size)
    }
}
