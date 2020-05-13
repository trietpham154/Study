package com.example.study

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(context: Context) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    private var mListData = Data.createList(context)

    var listener: OnItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val dataView = inflater.inflate(R.layout.item, parent, false)
        return MyViewHolder(dataView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = mListData[position]
        holder.apply {
            title.text = data.title
            description.text = data.description
            copyButton.isEnabled = data.isActive
            copyButton.setOnClickListener {
                listener?.onDetailButtonClick(data, position)
            }
        }
    }

    override fun getItemCount() = mListData.size

    fun setOnItemClickListener(OnItemClick: OnItemClick) {
        this.listener = OnItemClick
    }

    fun add(context: Context) {
        if (itemCount % 3 == 0) {
            mListData.add(
                itemCount,
                Data(
                    context.getString(R.string.lb_title, itemCount),
                    context.getString(R.string.lb_description, itemCount),
                    false,
                    context.getString(R.string.lb_content, itemCount)
                )
            )
        } else {
            mListData.add(
                itemCount,
                Data(
                    context.getString(R.string.lb_title, itemCount),
                    context.getString(R.string.lb_description, itemCount),
                    true,
                    context.getString(R.string.lb_content, itemCount)
                )
            )
        }
        notifyDataSetChanged()
    }

    fun remove() = mListData.takeIf { it.size >= itemCount - 1 }?.run {
        removeAt(itemCount - 1)
        notifyDataSetChanged()
    }

    inner class MyViewHolder(dataView: View) : RecyclerView.ViewHolder(dataView) {
        val title: TextView = itemView.findViewById<TextView>(R.id.title)
        val description: TextView = itemView.findViewById<TextView>(R.id.description)
        val copyButton: Button = itemView.findViewById<Button>(R.id.detailBtn)
    }

    interface OnItemClick {
        fun onDetailButtonClick(data: Data, index: Int)
    }
}
