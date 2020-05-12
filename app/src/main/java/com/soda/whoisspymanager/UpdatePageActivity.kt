package com.soda.whoisspymanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_update_page.*
import kotlinx.android.synthetic.main.manage_list.view.*
import kotlinx.android.synthetic.main.update_list.view.*

class UpdatePageActivity : AppCompatActivity() {
    private var list = mutableListOf<MutableList<String>>()
    private val adater = MyAdater()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_page)
        val date = intent.getStringExtra("date")

        textView9.text = "日期: ${date}"
        rc_update_page.setHasFixedSize(true)
        rc_update_page.layoutManager = LinearLayoutManager(this)
        rc_update_page.adapter = adater


        FirebaseDatabase.getInstance().getReference("Update").child(date).addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {

                p0.children.forEach {
                    val riddle = mutableListOf<String>()
                    it.children.forEach {
                        riddle.add(it.value as String)
                    }
                    list.add(riddle)
                }
                adater.notifyDataSetChanged()
            }

        })




    }

    private inner class MyAdater(): RecyclerView.Adapter<MyViewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.manage_list,parent,false)
            return MyViewHolder(view)
        }

        override fun getItemCount(): Int {
            return list.size
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.num.text = position.toString()
            holder.riddle1.text = list[position][0]
            holder.riddle2.text = list[position][1]
            holder.itemView.setOnLongClickListener{

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
