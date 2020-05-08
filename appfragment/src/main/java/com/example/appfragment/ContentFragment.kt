package com.example.appfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_content.view.*

class ContentFragment(private val data: Data) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_content, container, false)
        view.content?.text = data.content
        return view
    }

    companion object {
        fun newInstance(data: Data): ContentFragment {
            return ContentFragment(data)
        }
    }
}
