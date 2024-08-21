package com.example.progress

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.progress.database.DataModel


class Adapter(
    private val dataList: MutableList<DataModel>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<Adapter.ViewHolder>() { // Сюда передаётся ViewHolder

    fun updateData(newData: List<DataModel>) {
        dataList.clear()
        dataList.addAll(newData)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        dataList.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context) // Создаётся новый view
            .inflate(R.layout.item, parent, false) // который наполняет item.xml
        return ViewHolder(view, listener) // возвращается объект View Holder с параметром view
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataList[position])

    }

    override fun getItemCount() = dataList.size

    override fun getItemId(position: Int) = dataList[position].id


    class ViewHolder(itemView: View, listener: OnItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val weightView: TextView = itemView.findViewById(R.id.textView2)
        val dateView: TextView = itemView.findViewById(R.id.textView3)
        val imageView: ImageView = itemView.findViewById(R.id.imageView)

        var editButton: AppCompatImageButton = itemView.findViewById(R.id.editButton)
        val deleteButton: AppCompatImageButton = itemView.findViewById(R.id.deleteButton)

        val listener = listener

        fun bind(data: DataModel) {
            try {

                weightView.text = data.weight
                dateView.text = data.date

                Glide
                    .with(itemView.context)
                    .load(data.imageUri)
                    .apply(RequestOptions().override(600, 800))
                    .into(imageView)

                editButton.setOnClickListener {
                    listener.onEditClick(adapterPosition)
                }

                deleteButton.setOnClickListener {
                    listener.onDeleteClick(adapterPosition)
                }

            } catch (e: Exception) {
                Log.e("tesst", e.toString())
            }

        }
    }

    interface OnItemClickListener {
        fun onEditClick(position: Int)
        fun onDeleteClick(position: Int)
    }
}