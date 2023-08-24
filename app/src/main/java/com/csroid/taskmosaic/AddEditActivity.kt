package com.csroid.taskmosaic

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.csroid.taskmosaic.databinding.ActivityAddEditBinding

class AddEditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddEditBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var database: TodoDatabase
    private lateinit var username:String
    private lateinit var password:String
    private lateinit var todoTitle:String
    private lateinit var btn:String
    private var id:Long=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAddEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database=TodoDatabase.getDatabase(this)
        mainViewModel= ViewModelProvider(this,MainViewModelFactory(database)).get(MainViewModel::class.java)

        getTodoDetails()
        setTodoDetails()

    }

    private fun setTodoDetails() {
        if(btn=="SAVE"){
            binding.btnSave.text="SAVE TODO"
            binding.btnSave.setOnClickListener {
                todoTitle=binding.etTodo.text.toString()
                val todo:Todo=Todo(id,username,password,todoTitle)
                mainViewModel.insertTodo(todo)
                Toast.makeText(this, "${todo.todoTitle} Added", Toast.LENGTH_LONG).show()
                val iNext= Intent(this@AddEditActivity, MainActivity::class.java)
                startActivity(iNext)
            }
        }
        else{
            binding.btnSave.text="UPDATE TODO"
            binding.etTodo.setText(todoTitle)

            binding.btnSave.setOnClickListener {
                todoTitle=binding.etTodo.text.toString()
                val todo:Todo=Todo(id,username,password,todoTitle)
                mainViewModel.updateTodo(todo)
                Toast.makeText(this, "Todo Updated", Toast.LENGTH_LONG).show()
                val iNext= Intent(this@AddEditActivity, MainActivity::class.java)
                startActivity(iNext)
            }
        }
    }

    private fun getTodoDetails() {
        val intent = intent
        username = intent.getStringExtra(MainActivity.USERNAME)!!
        password = intent.getStringExtra(MainActivity.PASSWORD)!!
        todoTitle = intent.getStringExtra(MainActivity.TODO_TITLE)!!
        btn = intent.getStringExtra(MainActivity.BUTTON)!!
        id=intent.getLongExtra("ID",0)
    }
}