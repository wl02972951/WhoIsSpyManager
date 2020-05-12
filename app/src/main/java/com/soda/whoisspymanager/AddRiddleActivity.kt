package com.soda.whoisspymanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_add_riddle.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class AddRiddleActivity : AppCompatActivity() ,AnkoLogger {
    var riddleCount = 0
    var isCheckRiddle = false
    val firebaseDatabase =  FirebaseDatabase.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_riddle)
        checkRiddleCount()

        bt_send.setOnClickListener {
            val r1 =  et_riddle1.text.toString()
            val r2 =  et_riddle2.text.toString()
            if (isCheckRiddle && checkIllegal(r1, r2)){
                firebaseDatabase.getReference( "Riddle")
                    .child(riddleCount.toString())
                    .setValue(listOf(r1,r2))
                Toast.makeText(this,"已上傳",Toast.LENGTH_SHORT).show()
                et_riddle1.setText("")
                et_riddle2.setText("")
                riddleCount ++
            }
        }

        bt_clear.setOnClickListener {
            et_riddle1.setText("")
            et_riddle2.setText("")
        }

    }

    private fun  checkRiddleCount(){
            firebaseDatabase.getReference("Riddle")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(this@AddRiddleActivity, error.message, Toast.LENGTH_SHORT).show()
                    }

                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        riddleCount = dataSnapshot.childrenCount.toInt()


                        isCheckRiddle = true
                    }


                })
    }

    private fun checkIllegal(r1:String , r2 :String):Boolean{


        return if (r1 == "" || r2 ==""){
            Toast.makeText(this,"輸入請勿空白",Toast.LENGTH_SHORT).show()
            false
        } else if (r1 == r2){
            Toast.makeText(this,"題目不可一樣",Toast.LENGTH_SHORT).show()
            false
        } else{
            true
        }

    }


}
