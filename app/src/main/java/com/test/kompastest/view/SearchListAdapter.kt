package com.test.kompastest.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.test.kompastest.R
import com.test.kompastest.model.UserData
import kotlinx.android.synthetic.main.search_list.view.*

class SearchListAdapter(private var searchList : ArrayList<UserData>, private var context : Context) : RecyclerView.Adapter<SearchListAdapter.ViewHolder>() {

    var onItemClick : ((UserData) -> Unit)? = null

    class ViewHolder(item: View): RecyclerView.ViewHolder(item){
        val image: ImageView = item.pfpSearch
        val name: TextView = item.username
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchListAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.search_list, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SearchListAdapter.ViewHolder, position: Int) {
        val currentItem = searchList[position]
        holder.name.text = currentItem.login
        Glide.with(context).load(currentItem.avatar_url).apply(RequestOptions().circleCrop()).into(holder.image)

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(currentItem)
        }

    }

    override fun getItemCount(): Int {
        return searchList.size
    }
}