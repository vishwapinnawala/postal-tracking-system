package com.postaltracking.lk

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import com.postaltracking.lk.databinding.ActivityAddpackageBinding
import com.postaltracking.lk.databinding.ActivityPostofficedashBinding
import java.sql.Timestamp
import java.text.SimpleDateFormat

class addpackage : AppCompatActivity() {
    private val timeformat = SimpleDateFormat("yyyy/MM/dd HH:mm")
    private lateinit var binding :ActivityAddpackageBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding= ActivityAddpackageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var track=binding.trackingno.text
        binding.submit.setOnClickListener{
            addtrackingdetails(track.toString())
        }

    }

    fun addtrackingdetails(trackno:String){
        val sharedpref=getSharedPreferences("postlogin", Context.MODE_PRIVATE)
        val postofficeid=sharedpref.getString("Username",null)
        val timestamp = Timestamp(System.currentTimeMillis())
        var newtime=timeformat.format((timestamp))
        var database = FirebaseDatabase.getInstance().getReference("Precords")
        val Tracking = trackingno(postofficeid,trackno,newtime)
        database.child(trackno).setValue(Tracking).addOnSuccessListener {
            Toast.makeText(this, "Successfully Saved", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
        }
    }
}