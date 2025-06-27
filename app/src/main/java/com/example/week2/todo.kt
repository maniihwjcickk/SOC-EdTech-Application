package com.example.week2

import java.util.Date

data class todo(
    var id: Int,
    var title: String,
    var createdAt: Date,
    var isDone: Boolean = false
)
