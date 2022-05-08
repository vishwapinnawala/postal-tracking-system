package com.postaltracking.lk

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.*
import com.google.firebase.database.ValueEventListener
import android.content.SharedPreferences
import android.widget.Toast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.postaltracking.lk.databinding.ActivityAddtopostmanBinding
import java.sql.Timestamp
import java.text.SimpleDateFormat


class addtopostman : AppCompatActivity() {
    private lateinit var dbref : DatabaseReference
    private lateinit var userRecyclerview : RecyclerView
    private lateinit var userArrayList : ArrayList<trackingno>
    private val timeformat = SimpleDateFormat("yyyy/MM/dd HH:mm")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_addtopostman)

        userRecyclerview = findViewById(R.id.userList)
        userRecyclerview.layoutManager = LinearLayoutManager(this)
        userRecyclerview.setHasFixedSize(true)

        userArrayList = arrayListOf<trackingno>()
        getUserData()
    }
    private fun getUserData() {
        val sharedpref=getSharedPreferences("postlogin", Context.MODE_PRIVATE)
        val postofficeid=sharedpref.getString("Username",null)

        dbref = FirebaseDatabase.getInstance().getReference("Precords")
            dbref.orderByChild("pofficeid").equalTo(postofficeid.toString())
                .addValueEventListener(object : ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){

                    for (userSnapshot in snapshot.children){


                        val df = userSnapshot.getValue(trackingno::class.java)

                        userArrayList.add(df!!)
                        //userArrayList.toString()

                    }
var adapter =MyAdapter(userArrayList)
                    userRecyclerview.adapter = adapter
                    adapter.setOnItemClickListener(object :MyAdapter.onItemClickListener{
                        override fun onItemClick(position: String) {
                            MaterialAlertDialogBuilder(this@addtopostman)
                                .setTitle("Confirmation")
                                .setMessage("Do you need to add this Package to Delivery?")
                                .setNegativeButton("No")
                                { dialog, which ->
                                    //Toast.makeText(this, "${}", Toast.LENGTH_SHORT).show()
                                }
                                .setPositiveButton("Okay")
                                { dialog, which ->
                                    Toast.makeText(this@addtopostman, "Added to Delivery", Toast.LENGTH_SHORT).show()
                                    addtodelivery(position)
                                }
                                .show()
                        }

                    })



                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })

    }
    fun addtodelivery(track:String){
        val timestamp = Timestamp(System.currentTimeMillis())
        var newtime=timeformat.format((timestamp))
       var database= FirebaseDatabase.getInstance().getReference("Precords")
        database.child(track).get().addOnSuccessListener {
                val pofficeid=it.child("pofficeid").value.toString()
                val time=it.child("time").value.toString()

        database.child(track).removeValue().addOnSuccessListener {

                var databasee = FirebaseDatabase.getInstance().getReference("DeliveryRec")
                val delivery = deliveryrec(pofficeid, time, newtime.toString(), track)
                databasee.child(track).setValue(delivery).addOnSuccessListener {
                    Toast.makeText(this, "Successfully Saved", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener {
                    Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                }

        }.addOnFailureListener{
            Toast.makeText(this@addtopostman, "Adding to Delivery Failed!", Toast.LENGTH_SHORT).show()
        }




        }.addOnFailureListener{ Toast.makeText(this, "Data Retrieve Failed", Toast.LENGTH_SHORT).show()}

    }
}