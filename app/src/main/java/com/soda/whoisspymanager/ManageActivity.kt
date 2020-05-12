package com.soda.whoisspymanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_manage.*
import kotlinx.android.synthetic.main.manage_list.view.*
import org.jetbrains.anko.AnkoLogger

class ManageActivity : AppCompatActivity(),AnkoLogger {
    var riddleList = mutableListOf<List<String>>()
    private val adapter = MyAdater()
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage)
        rc_list.setHasFixedSize(true)
        rc_list.layoutManager = LinearLayoutManager(this)

        rc_list.adapter = adapter
        FirebaseDatabase.getInstance().getReference("Riddle").addValueEventListener(object :
            ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                p0.children.forEach {
                    var list = mutableListOf<String>()
                    it.children.forEach {
                        list.add(it.value as String)
                    }


                    riddleList.add(list)
                }
                adapter.notifyDataSetChanged()
            }

        })
    }


    private inner class MyAdater(): RecyclerView.Adapter<MyViewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.manage_list,parent,false)
            return MyViewHolder(view)
        }

        override fun getItemCount(): Int {
            return riddleList.size
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.num.text = position.toString()
            holder.riddle1.text = riddleList[position][0]
            holder.riddle2.text = riddleList[position][1]
            holder.itemView.setOnLongClickListener{
                startActivity(Intent(this@ManageActivity,ModifuActivity::class.java).putExtra("index",position))
                true
            }
        }

    }

    private inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view){
        val num = view.list_num
        val riddle1 = view.list_riddle1
        val riddle2 = view.list_riddle2

    }
}
