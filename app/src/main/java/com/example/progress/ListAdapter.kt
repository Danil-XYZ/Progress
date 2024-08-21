package com.example.progress

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.progress.database.ListModel

class ListAdapter (private val dataList: MutableList<ListModel>) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list, parent, false)
        return ViewHolder(view)
    }

    fun updateData(uriList: List<ListModel>) {
        dataList.clear()
        dataList.addAll(uriList)
        notifyDataSetChanged()
    }

    override fun getItemCount() = dataList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imageView: ImageView = itemView.findViewById(R.id.imageView)

        fun bind(data: ListModel) {
            Glide
                .with(itemView.context)
                .load(data.image)
                .apply(RequestOptions().override(600, 800))
                .into(imageView)

            imageView.setOnClickListener {

            }
        }
    }

}