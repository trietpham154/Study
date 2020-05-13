package com.example.appfragment

import android.os.Bundle
import androidx.fragment.app.FragmentActivity

class MainActivity : FragmentActivity(), ListItemFragment.OnClickItemListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.mainActivity, ListItemFragment.newInstance(Data.createList(this)))
            .commit()
    }

    override fun onClickDetailButton(data: Data) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.mainActivity, ContentFragment.newInstance(data))
            .addToBackStack(null)
            .commit()
    }

    override fun configurationChange() {
        recreate()
    }
}
