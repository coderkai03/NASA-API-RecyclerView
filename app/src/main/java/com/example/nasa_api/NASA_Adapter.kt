package com.example.nasa_api

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NASA_data(url: String, name: String, explanation: String) {
    var url: String
    var name: String
    var explanation: String

    init {
        this.url = url
        this.name = name
        this.explanation = explanation
    }
}

class NASA_Adapter (private val nasaList: MutableList<NASA_data>) : RecyclerView.Adapter<NASA_Adapter.ViewHolder>() {
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
            .load(nasaList[position].url)
            .placeholder(R.drawable.nasa_logo)
            .centerCrop()
            .into(holder.nasaImg)
    }

}