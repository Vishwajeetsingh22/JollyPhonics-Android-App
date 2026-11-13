package com.example.jollyphonics

import android.content.Intent
import android.os.Bundle
import android.widget.GridView
import androidx.appcompat.app.AppCompatActivity

class AlphabetActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alphabet)

        val gridView = findViewById<GridView>(R.id.gridViewAlphabet)
        val phonicsList = PhonicsItem.getAllPhonics()

        val adapter = AlphabetAdapter(this, phonicsList)
        gridView.adapter = adapter

        gridView.setOnItemClickListener { _, _, position, _ ->
            val selectedItem = phonicsList[position]
            val intent = Intent(this, PhonicsActivity::class.java).apply {
                putExtra("LETTER", selectedItem.letter)
                putExtra("SOUND", selectedItem.sound)
                putExtra("WORD", selectedItem.word)
                putExtra("DESCRIPTION", selectedItem.description)
            }
            startActivity(intent)
        }
    }
}