package com.example.jollyphonics

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView

class RecordingAdapter(
    private val context: Context,
    private val recordings: List<RecordingItem>,
    private val onPlayClick: (RecordingItem) -> Unit,
    private val onDeleteClick: (RecordingItem) -> Unit
) : BaseAdapter() {

    override fun getCount(): Int = recordings.size

    override fun getItem(position: Int): Any = recordings[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_recording, parent, false)

        val tvRecTitle = view.findViewById<TextView>(R.id.tvRecTitle)
        val tvRecTimestamp = view.findViewById<TextView>(R.id.tvRecTimestamp)
        val btnPlayRec = view.findViewById<Button>(R.id.btnPlayRec)
        val btnDeleteRec = view.findViewById<Button>(R.id.btnDeleteRec)

        val item = recordings[position]
        tvRecTitle.text = "Letter ${item.letter} - ${item.word}"
        tvRecTimestamp.text = item.timestamp

        btnPlayRec.setOnClickListener {
            onPlayClick(item)
        }

        btnDeleteRec.setOnClickListener {
            onDeleteClick(item)
        }

        return view
    }
}