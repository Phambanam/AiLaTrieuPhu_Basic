package com.example.ailatrieuphu1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ailatrieuphu1.databinding.ActivityLoginBinding
import com.example.ailatrieuphu1.databinding.ActivityMainBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnStart.setOnClickListener {

            var intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }


}