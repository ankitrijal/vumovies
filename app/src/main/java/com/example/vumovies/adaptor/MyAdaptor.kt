package com.example.vumovies.adaptor

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vumovies.DetailActivity
import com.example.vumovies.R
import com.example.vumovies.api.Entity

class MyAdaptor(private val entityList: List<Entity>) :
    RecyclerView.Adapter<MyAdaptor.EntityViewHolder>() {

    inner class EntityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvGenre: TextView = itemView.findViewById(R.id.tvGenre)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntityViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_entity, parent, false)
        return EntityViewHolder(view)
    }

    override fun onBindViewHolder(holder: EntityViewHolder, position: Int) {
        val movie = entityList[position]
        holder.tvTitle.text = movie.title
        holder.tvGenre.text = movie.genre

        // 🔗 Open detail screen on click
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("movie", movie) // Entity must implement Serializable
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = entityList.size
}
