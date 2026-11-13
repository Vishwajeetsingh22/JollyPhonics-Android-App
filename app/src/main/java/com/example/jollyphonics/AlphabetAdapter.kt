package com.example.jollyphonics

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class AlphabetAdapter(
    private val context: Context,
    private val items: List<PhonicsItem>
) : BaseAdapter() {

    override fun getCount(): Int = items.size

    override fun getItem(position: Int): Any = items[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_alphabet, parent, false)
        
        val tvLetter = view.findViewById<TextView>(R.id.tvLetter)
        val tvWord = view.findViewById<TextView>(R.id.tvWord)

        val item = items[position]
        tvLetter.text = item.letter
        tvWord.text = item.word

        return view
    }
}