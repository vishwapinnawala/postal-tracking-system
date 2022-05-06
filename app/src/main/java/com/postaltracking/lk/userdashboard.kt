package com.postaltracking.lk

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.postaltracking.lk.databinding.ActivityUserdashboardBinding

class userdashboard : AppCompatActivity() {

    private lateinit var binding :ActivityUserdashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding= ActivityUserdashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val sharedpref=getSharedPreferences("logins", Context.MODE_PRIVATE)
        val text=sharedpref.getString("Email",null)
        //binding.textView5.setText(text)
    }
}