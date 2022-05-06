package com.postaltracking.lk

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.postaltracking.lk.databinding.ActivityPostloginBinding
import com.postaltracking.lk.databinding.ActivitySelectionwindowBinding

class postlogin : AppCompatActivity() {
    private lateinit var binding : ActivityPostloginBinding
    private lateinit var database : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding= ActivityPostloginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.loginbtn.setOnClickListener{
            val uname=binding.unametext.text.toString()
            val pwd=binding.pwdtext.text.toString()

            readdata(uname,pwd)
        }
    }

    fun readdata(uname:String,pwd:String){
        database= FirebaseDatabase.getInstance().getReference("Postoffice")
        database.child(uname).get().addOnSuccessListener {
            if(it.exists()){
                val uname=it.child("name").value.toString()
                val newpwd=it.child("pwd").value.toString()

                verifypwd(uname,pwd,newpwd)
            }

            else{
                Toast.makeText(this, "User Doesn't Exist", Toast.LENGTH_SHORT).show()}


        }.addOnFailureListener{ Toast.makeText(this, "Data Retrieve Failed", Toast.LENGTH_SHORT).show()}

    }


    fun verifypwd(uname:String,pwd:String,newpwd:String){
        if(pwd.equals(newpwd)){
            Toast.makeText(this, "Login Successfull", Toast.LENGTH_SHORT).show()
            addsession(uname,pwd)
            val intent = Intent(this, postlogin::class.java)
            startActivity(intent)
        }
        else{
            Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()}
    }

    fun addsession(uname:String,pwd:String){
        val sharedpref=getSharedPreferences("postlogin", Context.MODE_PRIVATE)
        val editor = sharedpref.edit()

        editor.apply{
            putString("Username",uname)
            putString("Password",pwd)
            apply()
        }
    }
}