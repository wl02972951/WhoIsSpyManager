package com.soda.whoisspymanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_modifu.*
import org.jetbrains.anko.editText

class ModifuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modifu)
        val index = intent.getIntExtra("index", 0)
        var list = mutableListOf<String>()
        tv_index.text = "題目: $index"

        FirebaseDatabase.getInstance().getReference("Riddle").child(index.toString())
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                }

                override fun onDataChange(p0: DataSnapshot) {

                    p0.children.forEach {
                        list.add(it.value as String)

                    }
                    et_riddle1.setText(list[0])
                    et_riddle2.setText(list[1])
                }
            })

        bt_modify.setOnClickListener {
            val newRiddle1 = et_riddle1.text.toString()
            val newRiddle2 = et_riddle2.text.toString()

            AlertDialog.Builder(this)
                .setTitle("是否確認送出")
                .setMessage("謎底1: ${list[0]}  將會改為  $newRiddle1\n" +
                        "謎底2: ${list[1]}  將會改為  $newRiddle2")
                .setPositiveButton("確認"){dialog, which ->
                    FirebaseDatabase.getInstance().getReference("Riddle").child(index.toString())
                        .setValue(listOf(newRiddle1,newRiddle2))
                        finish()
                }
                .setNegativeButton("取消"){dialog , which ->
                    finish()
                }.show()

        }

    }
}
