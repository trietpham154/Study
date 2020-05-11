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
        val mListData =
            arguments?.getParcelableArrayList<Data>(Constant.LIST_DATA_KEY) ?: ArrayList()
        adapter = context?.let { MyAdapter(it, mListData) }
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

        adapter?.setOnItemClickListener(object : MyAdapter.OnItemClick {
            override fun onDetailButtonClick(data: Data) {
                listener?.onClickDetailButton(data)
            }
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
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
        fun onClickDetailButton(data: Data)
    }


}
