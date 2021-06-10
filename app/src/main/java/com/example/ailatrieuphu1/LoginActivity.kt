package com.example.ailatrieuphu1

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.ailatrieuphu1.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }


    private fun about() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("About")
        builder.setMessage(R.string.about)
        builder.show()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnStart -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            R.id.btnAbout -> {
                about()
            }
            R.id.btnHighScore -> {

            }
            R.id.btnExit -> {
                finish()
            }

        }
    }


}