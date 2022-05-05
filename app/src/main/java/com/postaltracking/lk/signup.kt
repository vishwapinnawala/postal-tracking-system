package com.postaltracking.lk

import android.content.Intent
import android.os.Bundle
import android.util.*
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.SQLException

class signup : AppCompatActivity() {
    private val TAG = "MyActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        var inname: TextView =findViewById(R.id.textView4)
        var submitbutton: Button =findViewById(R.id.inputbtn)

        submitbutton.setOnClickListener{
            try {
                val c = DriverManager.getConnection(
                    "jdbc:mysql://fdb31.biz.nf/4094753_rdadb&serverTimezone=UTC",
                    "4094753_rdadb",
                    "WOI4Kzv06UZQ])ff"
                )

val s=c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
                val r=s.executeQuery("select * from users")
                while(r.next()) {
                    val str = r.getString("email")
                    inname.setText(str)
                    Log.v(TAG, "output="+str);
                }
            }
            catch(e: SQLException){
                val error=e.printStackTrace()
                Log.v(TAG, "errorbe="+e.printStackTrace());
                inname.setText(error.toString())
            }


        }
            //val intent = Intent(this, selectionwindow::class.java)
            //startActivity(intent)
        }


    }
    fun connection():Int{
return 1
}