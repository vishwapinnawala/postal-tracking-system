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


class addtopostman : AppCompatActivity() {
    private lateinit var dbref : DatabaseReference
    private lateinit var userRecyclerview : RecyclerView
    private lateinit var userArrayList : ArrayList<trackingno>

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
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

                    userRecyclerview.adapter = MyAdapter(userArrayList)


                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })

    }
}