package com.example.study

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private val myListData: ArrayList<Data>): RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    var listener: OnItemClick? = null

    inner class MyViewHolder(dataView: View): RecyclerView.ViewHolder(dataView){
        val title: TextView = itemView.findViewById<TextView>(R.id.title)
        val description: TextView = itemView.findViewById<TextView>(R.id.description)
        val copyButton: Button = itemView.findViewById<Button>(R.id.detailBtn)
    }

    interface OnItemClick {
        fun onCopyButtonClick(position: Int)
    }

    fun setOnItemClickListener(OnItemClick: OnItemClick){
        this.listener = OnItemClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val dataView = inflater.inflate(R.layout.item, parent, false)
        return MyViewHolder(dataView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = myListData[position]
        holder.title.text = data.title
        holder.description.text = data.description
        holder.copyButton.isEnabled = data.isActive
//        holder.copyButton.background = x
        holder.copyButton.setOnClickListener{
                listener?.onCopyButtonClick(position)
        }
    }



    override fun getItemCount() = myListData.size
}