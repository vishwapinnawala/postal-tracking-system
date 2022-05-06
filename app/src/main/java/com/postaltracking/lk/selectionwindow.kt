package com.postaltracking.lk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import kotlin.math.log

class selectionwindow : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_selectionwindow)

        var loginbutton:Button =findViewById(R.id.loginbtn)
        loginbutton.setOnClickListener{
            val intent = Intent(this, selectionwindow::class.java)
            startActivity(intent)
        }

        var regbutton: Button =findViewById(R.id.regbtn)
        regbutton.setOnClickListener{
            val intent = Intent(this, signup::class.java)
            startActivity(intent)
        }
    }
}