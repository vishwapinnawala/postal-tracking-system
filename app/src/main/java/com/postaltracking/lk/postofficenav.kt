package com.postaltracking.lk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.postaltracking.lk.databinding.ActivityPostofficedashBinding
import com.postaltracking.lk.databinding.ActivityPostofficenavBinding

class postofficenav : AppCompatActivity() {
    private lateinit var binding : ActivityPostofficenavBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding= ActivityPostofficenavBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button3.setOnClickListener{
            val intent = Intent(this, postofficedash::class.java)
            startActivity(intent)
        }
        binding.button4.setOnClickListener{
            val intent = Intent(this, addtopostman::class.java)
            startActivity(intent)
        }
    }
}