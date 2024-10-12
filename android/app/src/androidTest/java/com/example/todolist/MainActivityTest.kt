package com.example.todolist

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testInitialState() {
        composeTestRule.onNodeWithText("No todos created.").assertExists()
    }

    @Test
    fun testOpenCreateTodoDialog() {
        composeTestRule.onNodeWithContentDescription("Create todo").performClick()
        composeTestRule.onNodeWithText("Cancel").assertExists()
        composeTestRule.onNodeWithText("Add").assertExists()
    }

    @Test
    fun testAddNewTodo() {
        composeTestRule.onNodeWithContentDescription("Create todo").performClick()
        composeTestRule.onNodeWithText("Name:").performTextInput("New Todo")
        composeTestRule.onNodeWithText("Add").performClick()
        composeTestRule.onNodeWithText("New Todo").assertExists()
    }

    @Test
    fun testDeleteTodo() {
        composeTestRule.onNodeWithContentDescription("Create todo").performClick()
        composeTestRule.onNodeWithText("Name:").performTextInput("New Todo to Delete")
        composeTestRule.onNodeWithText("Add").performClick()
        composeTestRule.onNodeWithText("New Todo to Delete").assertExists()
        composeTestRule.onAllNodesWithContentDescription("Delete todo")[0].performClick()
        composeTestRule.onNodeWithText("New Todo to Delete").assertDoesNotExist()
    }

    @Test
    fun testCreateMockTodos() {
        composeTestRule.onNodeWithContentDescription("Open menu").performClick()
        composeTestRule.onNodeWithText("Create mock todos").performClick()
        composeTestRule.onNodeWithText("Mock Todo 1").assertExists()
        composeTestRule.onNodeWithText("Mock Todo 10").assertExists()
    }

    @Test
    fun testDeleteAllTodos() {
        composeTestRule.onNodeWithContentDescription("Open menu").performClick()
        composeTestRule.onNodeWithText("Create mock todos").performClick()
        composeTestRule.onNodeWithText("Mock Todo 1").assertExists()
        composeTestRule.onNodeWithText("Mock Todo 10").assertExists()
        composeTestRule.onNodeWithContentDescription("Open menu").performClick()
        composeTestRule.onNodeWithText("Delete all").performClick()
        composeTestRule.onNodeWithText("No todos created.").assertExists()
    }
}
