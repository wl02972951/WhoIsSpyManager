package com.soda.whoisspymanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_add_riddle.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val FirebaseDatabase = FirebaseDatabase.getInstance()

        FirebaseDatabase.getReference("Report").child("riddle").addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                tv_error_report.text ="回報錯誤數: ${p0.childrenCount}"
            }

        })
        FirebaseDatabase.getReference("Riddle").addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                tv_riddlecount.text ="當前題目數量: ${p0.childrenCount}"
            }

        }

        )
        FirebaseDatabase.getReference("Update").addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                tv_other_add_riddle.text ="他人新增數: ${p0.childrenCount}"
            }

        }

        )

        bt_addriddle.setOnClickListener {
            startActivity(Intent(this,AddRiddleActivity::class.java))
        }

        bt_error.setOnClickListener {
            startActivity(Intent(this,ErrorActivity::class.java))
        }
        bt_manage.setOnClickListener {
            startActivity(Intent(this,ManageActivity::class.java))
        }
        bt_others.setOnClickListener {
            startActivity(Intent(this,OthersActivity::class.java))
        }

    }
}
