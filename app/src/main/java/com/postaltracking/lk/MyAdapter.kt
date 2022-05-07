package com.postaltracking.lk
import androidx.recyclerview.widget.RecyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
class MyAdapter(private val userList : ArrayList<trackingno>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.user_item,
            parent,false)
        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentitem = userList[position]

        holder.firstName.text = currentitem.trackno
        holder.lastName.text = currentitem.time
        holder.age.text = currentitem.pofficeid
    }

    override fun getItemCount(): Int {

        return userList.size
    }


    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val firstName : TextView = itemView.findViewById(R.id.tvfirstName)
        val lastName : TextView = itemView.findViewById(R.id.tvlastName)
        val age : TextView = itemView.findViewById(R.id.tvage)

    }

}