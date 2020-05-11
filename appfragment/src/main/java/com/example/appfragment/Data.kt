package com.example.appfragment

import android.content.Context
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Data(val title: String, val description: String, val isActive: Boolean, val content: String) :
    Parcelable {
    companion object {
        fun createList(context: Context): ArrayList<Data> {
            val data = ArrayList<Data>()
            for (x in 0..1000) {
                if (x % 3 == 0)
                    data.add(
                        Data(
                            context.getString(R.string.lb_title, x),
                            context.getString(R.string.lb_description, x),
                            false,
                            context.getString(R.string.lb_content, x)
                        )
                    )
                else
                    data.add(
                        Data(
                            context.getString(R.string.lb_title, x),
                            context.getString(R.string.lb_description, x),
                            true,
                            context.getString(R.string.lb_content, x)
                        )
                    )

            }
            return data
        }

    }
}