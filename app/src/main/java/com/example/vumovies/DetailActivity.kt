package com.example.vumovies

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.vumovies.api.Entity

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val movie = intent.getSerializableExtra("movie") as? Entity

        val tvTitle = findViewById<TextView>(R.id.tvTitle)
        val tvDirector = findViewById<TextView>(R.id.tvDirector)
        val tvGenre = findViewById<TextView>(R.id.tvGenre)
        val tvYear = findViewById<TextView>(R.id.tvYear)
        val tvDescription = findViewById<TextView>(R.id.tvDescription)

        movie?.let {
            tvTitle.text = it.title
            tvDirector.text = "Director: ${it.director}"
            tvGenre.text = "Genre: ${it.genre}"
            tvYear.text = "Release Year: ${it.releaseYear}"
            tvDescription.text = it.description
        }
    }
}
