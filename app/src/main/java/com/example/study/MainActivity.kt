package com.example.study

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(){
    private val myData = Data.data
        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Data.createList(this)

        val intent = Intent(this, ContentActivity::class.java)

        val recyclerView = findViewById<RecyclerView>(R.id.list_article)
        val adapter = MyAdapter(myData)


        adapter.setOnItemClickListener(object: MyAdapter.OnItemClick {
            override fun onCopyButtonClick(position: Int) {
                intent.putExtra("content", Data.data[position].content)
                startActivity(intent)
            }

        })

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)



        addItem.setOnClickListener {
            myData.add(myData.size,
                        Data(this.getString(R.string.lb_title, myData.size),
                            this.getString(R.string.lb_description,myData.size),
                            true,
                            this.getString(R.string.lb_content,myData.size)))
            adapter.notifyDataSetChanged()
            recyclerView.scrollToPosition(myData.size - 1)
        }
        removeItem.setOnClickListener {
            if (myData.isNotEmpty()) {
                myData.removeAt(myData.size - 1)
                adapter.notifyDataSetChanged()
                recyclerView.scrollToPosition(myData.size - 1)
            }

        }
    }
}
