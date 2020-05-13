package com.example.study

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var activityLanguage: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        activityLanguage = resources.configuration.locales[0].language

        val adapter = MyAdapter(this)

        adapter.setOnItemClickListener(object : MyAdapter.OnItemClick {
            override fun onDetailButtonClick(data: Data, index: Int) {
                val intent = Intent(this@MainActivity, ContentActivity::class.java)
                intent.putExtra(Constant.CONTENT_KEY, index)
                startActivity(intent)
            }
        })

        rvArticle?.adapter = adapter
        rvArticle?.layoutManager = LinearLayoutManager(this)

        addItem?.setOnClickListener {
            adapter.run {
                add(this@MainActivity)
                rvArticle?.scrollToPosition(this.itemCount - 1)
            }
        }

        removeItem?.setOnClickListener {
            adapter.takeIf { it.itemCount > 0 }?.run {
                remove()
                rvArticle?.scrollToPosition(this.itemCount - 1)
            }
        }

        changeLanguage?.setOnClickListener {
            ConfigurationChange.changeLanguage(this)
            this.recreate()
        }
    }

    override fun onResume() {
        if (activityLanguage != ConfigurationChange.getLanguage()){
            ConfigurationChange.applyLanguage(this)
            activityLanguage = ConfigurationChange.getLanguage()
            recreate()
        }
        super.onResume()
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase)
    }

}

