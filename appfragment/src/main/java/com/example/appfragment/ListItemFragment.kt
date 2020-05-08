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


open class ListItemFragment(context: Context, mListData: ArrayList<Data>) : Fragment() {

    private var listener: OnClickItemListener? = null

    private val thisContext = context

    private val adapter = MyAdapter(thisContext, mListData)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        val rv = view.findViewById<RecyclerView>(R.id.rvArticle)
        rv.layoutManager = LinearLayoutManager(thisContext)
        rv.adapter = adapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addItem.setOnClickListener {
            adapter.run {
                add(thisContext)
                view.rvArticle?.scrollToPosition(this.itemCount - 1)
            }
        }
        removeItem.setOnClickListener {
            adapter.run {
                remove()
                view.rvArticle?.scrollToPosition(this.itemCount - 1)
            }
        }
        adapter.setOnItemClickListener(object : MyAdapter.OnItemClick {
            override fun onDetailButtonClick(data: Data) {
                listener?.onClickDetailButton(data)
            }
        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as OnClickItemListener?
        if (listener == null) throw ClassCastException("$context must implement OnCopyClickedListener")
    }


    companion object {
        fun newInstance(context: Context, mListData: ArrayList<Data>) =
            ListItemFragment(context, mListData)
    }

    interface OnClickItemListener {
        fun onClickDetailButton(data: Data)
    }


}
