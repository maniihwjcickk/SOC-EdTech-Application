package com.example.week2

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.Date

class TodoViewModel : ViewModel() {
    private val _todolist = MutableLiveData<ArrayList<todo>>(arrayListOf())
    val todolist: LiveData<ArrayList<todo>> = _todolist


    fun addTodo(title: String) {
        if (title.isBlank()) return
        val newTodo = todo(
            id = System.currentTimeMillis().toInt(),
            title = title,
            createdAt = Date()
        )
        val currentList = _todolist.value ?: arrayListOf()
        val updatedList = ArrayList(currentList)
        updatedList.add(newTodo)
        _todolist.value = updatedList}

    fun deleteTodo(id: Int) {
        val currentList = _todolist.value ?: arrayListOf()
        val updatedList = ArrayList(currentList.filterNot { it.id == id })
        _todolist.value = updatedList
    }

    fun toggleTodo(id: Int) {
        val currentList = _todolist.value ?: arrayListOf()
        val updatedList = ArrayList(currentList.map {
            if (it.id == id) it.copy(isDone = !it.isDone) else it
        })
        _todolist.value = updatedList
    }
}