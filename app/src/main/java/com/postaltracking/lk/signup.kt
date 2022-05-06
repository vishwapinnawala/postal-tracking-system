package com.postaltracking.lk

import android.content.Intent
import android.os.Bundle
import android.util.*
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.red
import androidx.core.graphics.toColor
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.SQLException
import com.postaltracking.lk.databinding.ActivitySignupBinding
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController


class signup : AppCompatActivity() {
    private lateinit var binding :ActivitySignupBinding
    private lateinit var database:DatabaseReference

    private val TAG = "MyActivity"
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
       binding= ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //emailFocusListener()
       binding.inputbtn.setOnClickListener{
           formvalidate()       }
        //setContentView(R.layout.activity_signup)
       // var inname: TextView =findViewById(R.id.textView4)
      //  var submitbutton: Button =findViewById(R.id.inputbtn)

      //  submitbutton.setOnClickListener{

       // }

       }
    fun formvalidate(){
        val name=binding.inputName.text.toString()
        val nic=binding.inputNic.text.toString()
        val mobile=binding.inputMobileno.text.toString()
        var email=binding.inputEmail.text.toString()
        val postal=binding.inputPcode.text.toString()
        val pwd=binding.inputPwd.text.toString()
        if(name.length<4){Toast.makeText(this,"Minimum 4 Chatacter Name",Toast.LENGTH_SHORT).show()}
        else if(nic.length<9){Toast.makeText(this,"Invalid NIC",Toast.LENGTH_SHORT).show()}
        else if(mobile.length<10){Toast.makeText(this,"Invalid Mobile No",Toast.LENGTH_SHORT).show()}
        else if(!(android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())){Toast.makeText(this,"Invalid Email",Toast.LENGTH_SHORT)}
        else if(postal.length<4){Toast.makeText(this,"Invalid Postal Code",Toast.LENGTH_SHORT).show()}
        else if(pwd.length<8){Toast.makeText(this,"Minimum 8 Chatacter Password",Toast.LENGTH_SHORT).show()}
        else{
            email=email.replace(".", ",")
           // email=email.replace("@", "k")
            userinsert(name,nic,mobile,email,postal,pwd)
            val intent = Intent(this, selectionwindow::class.java)
            startActivity(intent)
        }

    }
fun userinsert(name:String,nic:String,mobile:String,email:String,postal:String,pwd:String) {
    database = FirebaseDatabase.getInstance().getReference("Users")
    val User = User(name, nic, mobile, email, postal, pwd)
    database.child(email).setValue(User).addOnSuccessListener {
        Toast.makeText(this, "Successfully Saved", Toast.LENGTH_SHORT).show()
    }.addOnFailureListener {
        Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
    }
}


}

    //}
