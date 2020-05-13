package com.example.appfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

class MainActivity : FragmentActivity(), ListItemFragment.OnClickItemListener {

    val fragmentManager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.mainActivity, ListItemFragment.newInstance(Data.createList(this)))
            .commit()
    }

    override fun onResume() {
        super.onResume()

    }

    override fun onClickDetailButton(data: Data, index: Int) {
        fragmentManager
            .beginTransaction()
            .replace(R.id.mainActivity, ContentFragment.newInstance(data, index))
            .addToBackStack(null)
            .commit()
    }

    fun replaceItself(fragment: Fragment) {
        fragmentManager.beginTransaction().replace(R.id.mainActivity, fragment).commit()
    }

}
