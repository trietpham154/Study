package com.example.appfragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.fragment_list.view.*


open class ListItemFragment() : Fragment() {

    private var listener: OnClickItemListener? = null

    private var fragmentLanguage: String? = null

    private var adapter: MyAdapter? = null
    private lateinit var linearLayoutManager: RecyclerView.LayoutManager

    private var root: View? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as OnClickItemListener?
        if (listener == null) throw ClassCastException("$context must implement OnCopyClickedListener")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fragmentLanguage = context?.resources?.configuration?.locales?.get(0)?.language

        val mListData =
            arguments?.getParcelableArrayList<Data>(Constant.LIST_DATA_KEY) ?: ArrayList()

        adapter = context?.let { MyAdapter(mListData) }
        linearLayoutManager = LinearLayoutManager(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        if (root != null) return root

        val view = inflater.inflate(R.layout.fragment_list, container, false)
        view.rvArticle?.apply {
            layoutManager = linearLayoutManager
            adapter = this@ListItemFragment.adapter
        }
        root = view

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (ConfigurationChange.getCurrentPosition() > 0) {
            linearLayoutManager.scrollToPosition(ConfigurationChange.getCurrentPosition())
        }

        addItem.setOnClickListener {
            adapter?.run {
                context?.let { context -> this.add(context) }
                view.rvArticle?.scrollToPosition(this.itemCount - 1)
            }
        }

        removeItem.setOnClickListener {
            adapter?.run {
                remove()
                view.rvArticle?.scrollToPosition(this.itemCount - 1)
            }
        }

        changeLanguage?.setOnClickListener {
            context?.run {
                ConfigurationChange.changeLanguage(this)
                (this as MainActivity).recreate()
            }
        }

        adapter?.setOnItemClickListener(object : MyAdapter.OnItemClick {
            override fun onDetailButtonClick(data: Data, index: Int) {
                listener?.onClickDetailButton(data, index)
                ConfigurationChange.setCurrentPosition((view.rvArticle?.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition())
            }
        })
    }

    override fun onResume() {
        super.onResume()
        if (fragmentLanguage != ConfigurationChange.getLanguage()) {
            context?.let { ConfigurationChange.applyLanguage(it) }
            fragmentLanguage = ConfigurationChange.getLanguage()
            context?.let {
                (it as MainActivity).recreate()
            }
        }
    }

    companion object {
        fun newInstance(mListData: ArrayList<Data>): ListItemFragment {
            return ListItemFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(Constant.LIST_DATA_KEY, mListData)
                }
            }
        }
    }

    interface OnClickItemListener {
        fun onClickDetailButton(data: Data, index: Int)
    }


}
