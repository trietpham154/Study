package com.example.study

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = MyAdapter(this)
        adapter.setOnItemClickListener(object : MyAdapter.OnItemClick {
            override fun onDetailButtonClick(content: String) {
                val intent = Intent(this@MainActivity, ContentActivity::class.java)
                intent.putExtra("content", content)
                startActivity(intent)
            }
        })

        rvArticle?.adapter = adapter
        rvArticle?.layoutManager = LinearLayoutManager(this)

        addItem?.setOnClickListener {
            adapter.run {
                add(this@MainActivity)
                notifyDataSetChanged()
                rvArticle?.scrollToPosition(this.itemCount - 1)
            }
        }
        removeItem?.setOnClickListener {
            adapter.takeIf { it.itemCount > 0 }?.run {
                remove()
                notifyDataSetChanged()
                rvArticle?.scrollToPosition(this.itemCount - 1)
            }


        }

    }
}

