package com.example.todolist

data class Todo(
    val name: String
) {
    val id: String = java.util.UUID.randomUUID().toString()
    val createdAt: Long = System.currentTimeMillis()

    fun getCreatedTime(): String {
        val date = java.util.Date(this.createdAt)

        val defaultLocale = java.util.Locale.getDefault()
        val simpleDateFormat = java.text.SimpleDateFormat("HH:mm:ss", defaultLocale)
        val dateTime = simpleDateFormat.format(date)

        return "Created on $dateTime"
    }
}