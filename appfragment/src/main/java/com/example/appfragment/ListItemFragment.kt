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
import java.util.*
import kotlin.collections.ArrayList


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

//        changeLanguage?.setOnClickListener {
//            Toast.makeText(context,
//                (root?.rvArticle?.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
//                    .toString(),
//                Toast.LENGTH_SHORT
//            ).show()
//        }

        adapter?.setOnItemClickListener(object : MyAdapter.OnItemClick {
            override fun onDetailButtonClick(data: Data) {
                listener?.onClickDetailButton(data)
            }
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(
            Constant.CURRENT_FIRST_POS,
            (root?.rvArticle?.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
        )
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null) {
            linearLayoutManager.scrollToPosition(savedInstanceState.getInt(Constant.CURRENT_FIRST_POS))
        }
    }

    override fun onResume() {
        super.onResume()
        changeLanguage?.setOnClickListener {
            if (resources.configuration.locales[0].language == Constant.VN_LANGUAGE)
                applyLanguage(Constant.EN_LANGUAGE)
            else if (resources.configuration.locales[0].language == Constant.EN_LANGUAGE)
                applyLanguage(Constant.VN_LANGUAGE)
        }
    }

    private fun applyLanguage(language: String) {
        val locale = Locale(language)
        resources.configuration.run {
            setLocale(locale)
            resources.updateConfiguration(this, resources.displayMetrics)
        }
        listener?.configurationChange()
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
        fun configurationChange()
    }


}
