package com.example.appfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_content.view.*

class ContentFragment : Fragment() {

    private val index: Int by lazy {
        arguments?.getInt(Constant.POSITION_KEY) ?: 0
    }

    private val data: Data by lazy {
        arguments?.getParcelable(Constant.POSITION_KEY) ?: Data("", "", false, "")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_content, container, false)
        view.content?.text = resources.getString(R.string.lb_content, index)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.changeLanguage.setOnClickListener {
            context?.let {
                ConfigurationChange.changeLanguage(it)
                (it as MainActivity).run {
                    supportFragmentManager
                        .beginTransaction()
                        .remove(this@ContentFragment)
                        .commit()
                    replaceItself(newInstance(data, index))
                }
            }
        }
    }

    companion object {
        fun newInstance(data: Data, index: Int): ContentFragment {
            return ContentFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(Constant.DATA_KEY, data)
                    putInt(Constant.POSITION_KEY, index)
                }
            }
        }
    }
}
