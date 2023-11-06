package com.example.nasa_api

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NASA_Adapter (private val nasaList: MutableList<String>) : RecyclerView.Adapter<NASA_Adapter.ViewHolder>() {
    class ViewHolder(view: View) :  RecyclerView.ViewHolder(view){
        val nasaImg: ImageView

        init {
            nasaImg = view.findViewById(R.id.nasapic)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.nasa_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount() = nasaList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(nasaList[position])
            .placeholder(R.drawable.nasa_logo)
            .centerCrop()
            .into(holder.nasaImg)
    }

}