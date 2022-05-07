package com.postaltracking.lk

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlin.math.log
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.postaltracking.lk.databinding.ActivitySelectionwindowBinding

class selectionwindow : AppCompatActivity() {
private lateinit var binding : ActivitySelectionwindowBinding
private lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding= ActivitySelectionwindowBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(R.layout.activity_selectionwindow)
        binding.pofficebtn.setOnClickListener{
            val intent = Intent(this, postlogin::class.java)
            startActivity(intent)
        }
             binding.loginbtn.setOnClickListener{
            val email=binding.emailtet.text.toString()
            val pwd=binding.pwdtext.text.toString()

         readdata(email,pwd)
        }

        binding.regbtn.setOnClickListener{
            val intent = Intent(this, signup::class.java)
            startActivity(intent)
        }

    }

    fun readdata(email:String,pwd:String){
        var newemail=email.replace(".", ",")
        database=FirebaseDatabase.getInstance().getReference("Users")
        database.child(newemail).get().addOnSuccessListener {
            if(it.exists()){
                val uname=it.child("name").value
                val retpwd=it.child("pwd").value
                verifypwd(email,pwd,retpwd.toString())
                           }

          else{Toast.makeText(this, "User Doesn't Exist", Toast.LENGTH_SHORT).show()}


    }.addOnFailureListener{Toast.makeText(this, "Data Retrieve Failed", Toast.LENGTH_SHORT).show()}

    }

    fun verifypwd(email:String,pwd:String,newpwd:String){
        if(pwd==newpwd){
            Toast.makeText(this, "Login Successfull", Toast.LENGTH_SHORT).show()
            addsession(email,pwd)
            val intent = Intent(this, userdashboard::class.java)
            startActivity(intent)
        }
        else{
            Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()}
    }
    fun addsession(Email:String,Pwd:String){
        val sharedpref=getSharedPreferences("logins", Context.MODE_PRIVATE)
        val editor = sharedpref.edit()

        editor.apply{
            putString("Email",Email)
            putString("Pwd",Pwd)
            apply()
        }
    }
}