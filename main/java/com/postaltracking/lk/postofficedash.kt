package com.postaltracking.lk

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.postaltracking.lk.databinding.ActivityPostloginBinding
import com.postaltracking.lk.databinding.ActivityPostofficedashBinding
import kotlinx.coroutines.NonCancellable.cancel
import java.sql.Timestamp
import java.text.SimpleDateFormat


class postofficedash : AppCompatActivity() {
    private lateinit var codeScanner: CodeScanner
    private lateinit var binding : ActivityPostofficedashBinding
    private lateinit var database : DatabaseReference
    private val timeformat = SimpleDateFormat("yyyy/MM/dd HH:mm")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding= ActivityPostofficedashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val scannerView=binding.scannerView
        codeScanner = CodeScanner(this, scannerView)
        codeScanner.camera = CodeScanner.CAMERA_BACK // or CAMERA_FRONT or specific camera id
        codeScanner.formats = CodeScanner.ALL_FORMATS // list of type BarcodeFormat,
        codeScanner.autoFocusMode = AutoFocusMode.SAFE // or CONTINUOUS
        codeScanner.scanMode = ScanMode.SINGLE // or CONTINUOUS or PREVIEW
        codeScanner.isAutoFocusEnabled = true // Whether to enable auto focus or not
        codeScanner.isFlashEnabled = false // Whether to enable flash or not

        binding.button.setOnClickListener {

            codeScanner.decodeCallback = DecodeCallback {
                runOnUiThread {

                //Toast.makeText(this, "Scan result: ${it.text}", Toast.LENGTH_LONG).show()

                    MaterialAlertDialogBuilder(this)
                        .setTitle("Tracking No")
                        .setMessage("Tracking No is ${it.text}. Do you need to add this to Records?")
                        .setNegativeButton("No")
                        { dialog, which ->
                            //Toast.makeText(this, "${}", Toast.LENGTH_SHORT).show()
                        }
                        .setPositiveButton("Okay")
                        { dialog, which ->
                            //Toast.makeText(this, "Clicked Okay", Toast.LENGTH_SHORT).show()
                            addtrackingdetails(it.text)
                        }
                        .show()
                }
            }
            codeScanner.errorCallback = ErrorCallback { // or ErrorCallback.SUPPRESS
                runOnUiThread {
                    Toast.makeText(this, "Camera initialization error: ${it.message}",
                        Toast.LENGTH_LONG).show()
                }
            }

            codeScanner.startPreview()
        }
    }

   fun addtrackingdetails(trackno:String){
       val sharedpref=getSharedPreferences("postlogin", Context.MODE_PRIVATE)
       val postofficeid=sharedpref.getString("Username",null)
       val timestamp = Timestamp(System.currentTimeMillis())
       var newtime=timeformat.format((timestamp))
       database = FirebaseDatabase.getInstance().getReference("Precords")
       val Tracking = trackingno(postofficeid,trackno,newtime)
       database.child(trackno).setValue(Tracking).addOnSuccessListener {
           Toast.makeText(this, "Successfully Saved", Toast.LENGTH_SHORT).show()
       }.addOnFailureListener {
           Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
       }
   }


    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }
    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }

}
