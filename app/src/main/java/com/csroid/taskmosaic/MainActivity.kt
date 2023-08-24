package com.csroid.taskmosaic

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.csroid.taskmosaic.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), TodoClickInterface, TodoClickDeleteInterface {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var todoAdapter: TodoAdapter
    private lateinit var database: TodoDatabase
    private lateinit var username:String
    private lateinit var password:String
    private lateinit var iNext: Intent

    companion object
    {
        const val USERNAME="com.csroid.khanakhazana.fragment.username"
        const val PASSWORD="com.csroid.khanakhazana.fragment.password"
        const val TODO_TITLE="com.csroid.khanakhazana.fragment.todoTitle"
        const val BUTTON="com.csroid.khanakhazana.fragment.button"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        todoAdapter=TodoAdapter(this,this)
        database=TodoDatabase.getDatabase(this)
        mainViewModel= ViewModelProvider(this,MainViewModelFactory(database)).get(MainViewModel::class.java)

        getUsernameAndPassword()
        setWelcomeTextAndListener()
        addTodoOnCLick()
        setTodoAdapter()
        updateRecyclerView()
    }

    private fun setTodoAdapter() {
        binding.rvTodo.apply {
            layoutManager= LinearLayoutManager(this@MainActivity)
            adapter=todoAdapter
        }
    }

    //todoAdapter.setTodoList(todos = it as ArrayList<Todo>)
    private fun updateRecyclerView() {
        mainViewModel.getAllTodos(username)
        mainViewModel.todos.observe(this,{list->
            list?.let {
                todoAdapter.setTodoList(it)
                binding.rvTodo.scrollToPosition(todoAdapter.itemCount - 1);
            }
        })
    }

    override fun onTodoClick(todo: Todo) {
        val intent=Intent(this@MainActivity,AddEditActivity::class.java)
        intent.putExtra(USERNAME,username)
        intent.putExtra(PASSWORD,password)
        intent.putExtra(TODO_TITLE,todo.todoTitle)
        intent.putExtra("ID",todo.id)
        intent.putExtra(BUTTON,"Edit")
        startActivity(intent)
    }

    override fun onDeleteIconClick(todo: Todo) {
        mainViewModel.deleteTodo(todo)
        setTodoAdapter()
        updateRecyclerView()
        Toast.makeText(this, "${todo.todoTitle} Deleted", Toast.LENGTH_LONG).show()
    }

    private fun addTodoOnCLick() {
        binding.idFAB.setOnClickListener {
            val intent=Intent(this@MainActivity,AddEditActivity::class.java)
            intent.putExtra(USERNAME,username)
            intent.putExtra(PASSWORD,password)
            intent.putExtra(TODO_TITLE,"")
            intent.putExtra("ID",0)
            intent.putExtra(BUTTON,"SAVE")

            startActivity(intent)
        }
    }

    private fun setWelcomeTextAndListener() {
        binding.tvUsername.text="Welcome, "+username

        binding.btnLogout.setOnClickListener {
            clearSharedPreferenceData()
            iNext=Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(iNext)
        }
    }

    private fun clearSharedPreferenceData() {
        val pref: SharedPreferences = getSharedPreferences("login", MODE_PRIVATE)
        val editor: SharedPreferences.Editor =  pref.edit()
        editor.clear()
        editor.apply()
    }

    private fun getUsernameAndPassword() {
        val pref=getSharedPreferences("login", MODE_PRIVATE)
        username=pref.getString("username","user")!!
        password=pref.getString("password","pass")!!
    }
}