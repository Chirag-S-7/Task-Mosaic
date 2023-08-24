package com.csroid.taskmosaic

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val todoDatabase: TodoDatabase):ViewModel() {
    private val _todos = MutableLiveData<List<Todo>>()

    val todos:LiveData<List<Todo>>
        get() = _todos

    fun insertTodo(todo: Todo)
    {
        viewModelScope.launch(Dispatchers.IO){
            todoDatabase.todoDAO().insertTodo(todo)
        }
    }

    fun updateTodo(todo: Todo)
    {
        viewModelScope.launch(Dispatchers.IO){
            todoDatabase.todoDAO().updateTodo(todo)
        }
    }

    fun deleteTodo(todo: Todo)
    {
        viewModelScope.launch(Dispatchers.IO) {
            todoDatabase.todoDAO().deleteTodo(todo)
        }
    }

    fun getAllTodos(username:String)
    {
        viewModelScope.launch(Dispatchers.IO){
            var result:List<Todo> = todoDatabase.todoDAO().getAllTodos(username)
            _todos.postValue(result)
        }
    }

}