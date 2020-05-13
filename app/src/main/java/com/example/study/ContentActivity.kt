package com.example.study

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_content.*

class ContentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content)
        content.text =
            resources.getString(R.string.lb_content, intent.extras?.getInt(Constant.CONTENT_KEY))
        changeLanguage?.setOnClickListener {
            ConfigurationChange.changeLanguage(this)
            recreate()
        }
    }

    override fun onResume() {
        if (resources.configuration.locales[0].language != ConfigurationChange.getLanguage()){
            ConfigurationChange.applyLanguage(this)
            recreate()
        }
        super.onResume()
    }


}
