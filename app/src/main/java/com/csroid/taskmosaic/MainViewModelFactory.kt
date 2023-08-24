package com.csroid.taskmosaic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainViewModelFactory (private val todoDatabase: TodoDatabase): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T
    {
        return MainViewModel(todoDatabase) as T
    }

}