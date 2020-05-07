package com.example.study

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_content.*

class ContentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content)
        content.text = intent.getStringExtra("content")
    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.example_menu, menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        if (item.isChecked) {
//            when (item.itemId) {
//                R.id.first_item -> Toast.makeText(this, "First Item", Toast.LENGTH_SHORT).show()
//                R.id.second_item -> Toast.makeText(this, "Second Item", Toast.LENGTH_SHORT).show()
//                R.id.third_item -> Toast.makeText(this, "Third Item", Toast.LENGTH_SHORT).show()
//                R.id.fourth_item -> Toast.makeText(this, "Fourth Item", Toast.LENGTH_SHORT).show()
//                R.id.submenu_item1 -> Toast.makeText(this, "Sub Item", Toast.LENGTH_SHORT).show()
//            }
//        }
//        return super.onOptionsItemSelected(item)
//    }
}
