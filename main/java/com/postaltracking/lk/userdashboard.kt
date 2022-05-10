package com.postaltracking.lk

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import com.google.firebase.database.FirebaseDatabase
import com.postaltracking.lk.databinding.ActivityUserdashboardBinding

class userdashboard : AppCompatActivity() {

    private lateinit var binding :ActivityUserdashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding= ActivityUserdashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //val sharedpref=getSharedPreferences("logins", Context.MODE_PRIVATE)
        //val text=sharedpref.getString("Email",null)
        //binding.textView5.setText(text)
        binding.searchbtn.setOnClickListener {
            readdata(binding.trackno.text.toString())
        }

    }
    fun readdata(trackno:String){
        //Toast.makeText(this, "readdata", Toast.LENGTH_SHORT).show()
      var  database= FirebaseDatabase.getInstance().getReference("Precords")
        database.child(trackno).get().addOnSuccessListener {
            if(it.exists()){
                val pofficeid=it.child("pofficeid").value.toString()
                val time=it.child("time").value.toString()

                binding.textView9.setText("Package is in $pofficeid Branch")
                binding.textView10.setText("$time")
                binding.button7.visibility= View.VISIBLE
                binding.radioButton3.visibility= View.VISIBLE
                binding.radioButton3.isClickable=false
            }

            else{
                var  database2= FirebaseDatabase.getInstance().getReference("DeliveryRec")
                database2.child(trackno).get().addOnSuccessListener {
                    if(it.exists()){
                        val pofficeid=it.child("pofficeid").value.toString()
                        val time=it.child("time").value.toString()
                        val previoustime=it.child("previoustime").value.toString()

                        binding.textView9.setText("Package is in $pofficeid Branch")
                        binding.textView10.setText("$previoustime")
                        binding.button7.visibility= View.VISIBLE
                        binding.radioButton3.visibility= View.VISIBLE
                        binding.radioButton3.isClickable=false

                        binding.button6.visibility= View.VISIBLE
                        binding.radioButton2.visibility= View.VISIBLE
                        binding.radioButton2.isClickable=false
                        binding.textView11.setText("$time")
                        binding.textView8.setText("Package is being Delivered")
                    }
                    else{
                        Toast.makeText(this, "Invalid Tracking No", Toast.LENGTH_SHORT).show()
                    }
                }.addOnFailureListener{ Toast.makeText(this, "Data Retrieve Failed", Toast.LENGTH_SHORT).show()}

                }

        }.addOnFailureListener{ Toast.makeText(this, "Data Retrieve Failed", Toast.LENGTH_SHORT).show()}

    }
}