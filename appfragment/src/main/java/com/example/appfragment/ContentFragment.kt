package com.example.appfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_content.view.*

class ContentFragment : Fragment() {

    private val content: String by lazy {
        arguments?.getString(Constant.CONTENT_KEY) ?: Constant.CANT_GET_CONTENT
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_content, container, false)
        view.content?.text = content
        return view
    }

    companion object {
        fun newInstance(data: Data): ContentFragment {
            return ContentFragment().apply {
                arguments = Bundle().apply {
                    putString(Constant.CONTENT_KEY, data.content)
                }
            }
        }
    }
}
