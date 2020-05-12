package com.soda.whoisspymanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_manage.*
import kotlinx.android.synthetic.main.activity_others.*
import kotlinx.android.synthetic.main.manage_list.view.*
import kotlinx.android.synthetic.main.update_list.view.*
import org.jetbrains.anko.Android
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class OthersActivity : AppCompatActivity() ,AnkoLogger{
     var updateDatas = mutableListOf<UpdateData>()
    private val adapter = MyAdater()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_others)

        rc_others.setHasFixedSize(true)
        rc_others.layoutManager = LinearLayoutManager(this)
        rc_others.adapter = adapter

        FirebaseDatabase.getInstance().getReference("Update").addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                p0.children.forEach {
                    info { it.key.toString() }
                    info { it.childrenCount  }
                    updateDatas.add(UpdateData(it.key.toString(),it.childrenCount.toInt()))
                }
                adapter.notifyDataSetChanged()
            }

        })




    }
    private inner class MyAdater(): RecyclerView.Adapter<MyViewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.update_list,parent,false)
            return MyViewHolder(view)
        }

        override fun getItemCount(): Int {
            return updateDatas.size
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.date.text = updateDatas[position].date
            holder.count.text = updateDatas[position].count.toString()
            holder.itemView.setOnLongClickListener{
                startActivity(Intent(this@OthersActivity,UpdatePageActivity::class.java).putExtra("date",updateDatas[position].date))
                true
            }
        }

    }

    private inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view){
        val date = view.tv_date
        val count = view.tv_childrencount



    }


}
