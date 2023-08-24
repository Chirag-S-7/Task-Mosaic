package com.csroid.taskmosaic

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.csroid.taskmosaic.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var iNext: Intent
        binding.btnLogin.setOnClickListener {
            setSharedPreferenceValues()
            if(binding.etUsername.text.isNotEmpty() && binding.etPassword.text.isNotEmpty()) {
                iNext=Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(iNext)
            }
            else{
                if(binding.etUsername.text.isEmpty() && binding.etPassword.text.isEmpty())
                    Toast.makeText(this,"Please Enter Username and Password",Toast.LENGTH_LONG).show()
                else if(binding.etUsername.text.isEmpty())
                    Toast.makeText(this,"Please Enter Username",Toast.LENGTH_LONG).show()
                else
                    Toast.makeText(this,"Please Enter Password",Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setSharedPreferenceValues() {
        val pref:SharedPreferences = getSharedPreferences("login", MODE_PRIVATE)
        val editor: SharedPreferences.Editor =  pref.edit()
        editor.putString("username",binding.etUsername.text.toString())
        editor.putString("password",binding.etPassword.text.toString())
        editor.putBoolean("isLoggedIn",true)
        editor.apply()
    }
}