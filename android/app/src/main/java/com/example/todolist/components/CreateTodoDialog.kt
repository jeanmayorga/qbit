package com.example.todolist.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver


@Composable
fun CreateTodoDialog(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    onConfirmation: (String) -> Unit,
) {
    var text by rememberSaveable { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }
    val lifecycleOwner = LocalLifecycleOwner.current
    val view = LocalView.current


    if (expanded) {
        DisposableEffect(lifecycleOwner) {
            val observer = LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_RESUME) {
                    view.post { focusRequester.requestFocus() }
                }
            }
            lifecycleOwner.lifecycle.addObserver(observer)
            onDispose {
                lifecycleOwner.lifecycle.removeObserver(observer)
            }
        }

        AlertDialog(
            title = { Text(text = "Create todo") },
            text = {
                TextField(
                    value = text,
                    maxLines = 1,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(autoCorrect = false),
                    onValueChange = { text = it },
                    label = { Text("Name:") },

                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester)
                )
            },
            onDismissRequest = {
                onDismissRequest()
                text = ""
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onConfirmation(text)
                        text = ""
                    }
                ) { Text("Add") }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        onDismissRequest()
                        text = ""
                    }
                ) { Text("Cancel") }
            }
        )
    }
}