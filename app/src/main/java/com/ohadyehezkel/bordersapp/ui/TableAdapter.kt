package com.ohadyehezkel.bordersapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ohadyehezkel.bordersapp.R
import com.ohadyehezkel.bordersapp.model.Country
import com.ohadyehezkel.bordersapp.model.Header
import com.ohadyehezkel.bordersapp.model.ListItem

const val TYPE_HEADER = 0
const val TYPE_ITEM = 1

class TableAdapter : RecyclerView.Adapter<TableAdapter.ViewHolder>() {
    private var dataSet: MutableList<ListItem> = mutableListOf()
    var itemClicked: ((Country) -> Unit)? = null
    private var nameDescending = false
    private var areaDescending = false

    abstract class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract var left: TextView
        abstract var mid: TextView
        abstract var right: TextView
    }

    class HeaderViewHolder(private val view: View) : ViewHolder(view) {
        override var left: TextView = view.findViewById(R.id.left)
        override var mid: TextView = view.findViewById(R.id.mid)
        override var right: TextView = view.findViewById(R.id.right)
        private var leftButton: ImageButton = view.findViewById(R.id.leftButton)
        private var rightButton: ImageButton = view.findViewById(R.id.rightButton)

        fun bind(
            nameDescending: Boolean,
            areaDescending: Boolean,
            sortByName: () -> Unit,
            sortByArea: () -> Unit
        ) {
            leftButton.setImageResource(if (nameDescending) R.drawable.arrow_up else R.drawable.arrow_down)
            rightButton.setImageResource(if (areaDescending) R.drawable.arrow_up else R.drawable.arrow_down)
            left.text = view.context.getString(R.string.english_name)
            mid.text = view.context.getString(R.string.native_name)
            right.text = view.context.getString(R.string.area)
            leftButton.setOnClickListener {
                sortByName()
            }
            rightButton.setOnClickListener {
                sortByArea()
            }
        }
    }

    fun addItems(items: List<ListItem>) {
        dataSet.clear()
        dataSet.add(Header())
        dataSet.addAll(items)
        notifyDataSetChanged()
    }

    class ItemViewHolder(view: View) : ViewHolder(view) {
        override var left: TextView = view.findViewById(R.id.left)
        override var mid: TextView = view.findViewById(R.id.mid)
        override var right: TextView = view.findViewById(R.id.right)
        private var clickableView: View = view.findViewById(R.id.clickableView)

        fun bind(item: Country, callback: () -> Unit) {
            left.text = item.name
            mid.text = item.nativeName
            right.text = item.area.toString()
            clickableView.setOnClickListener { callback() }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            TYPE_HEADER -> HeaderViewHolder(
                LayoutInflater.from(viewGroup.context)
                    .inflate(R.layout.text_header_item, viewGroup, false)
            )
            else -> ItemViewHolder(
                LayoutInflater.from(viewGroup.context)
                    .inflate(R.layout.text_row_item, viewGroup, false)
            )
        }
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        when (viewHolder) {
            is HeaderViewHolder -> (dataSet[position] as? Header)?.let {
                viewHolder.bind(nameDescending, areaDescending, {
                    val list: List<Country> = dataSet.filterIsInstance(Country::class.java)
                    addItems(if (nameDescending) list.sortedBy { it.name } else list.sortedByDescending { it.name })
                    nameDescending = !nameDescending
                    areaDescending = false
                }) {
                    val list: List<Country> = dataSet.filterIsInstance(Country::class.java)
                    addItems(if (areaDescending) list.sortedBy { it.area } else list.sortedByDescending { it.area })
                    areaDescending = !areaDescending
                    nameDescending = false
                }
            }
            is ItemViewHolder -> (dataSet[position] as? Country)?.let {
                viewHolder.bind(it) {
                    itemClicked?.invoke(it)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (dataSet[position]) {
            is Header -> TYPE_HEADER
            else -> TYPE_ITEM
        }
    }

    override fun getItemCount() = dataSet.size

}

