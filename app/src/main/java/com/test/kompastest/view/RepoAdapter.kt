package com.test.kompastest.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.test.kompastest.R
import com.test.kompastest.model.RepoModel
import kotlinx.android.synthetic.main.repo_list.view.*
import kotlinx.android.synthetic.main.search_list.view.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class RepoAdapter(private var listRepo: ArrayList<RepoModel>) : RecyclerView.Adapter<RepoAdapter.ViewHolder>() {

    class ViewHolder(item: View): RecyclerView.ViewHolder(item){
        val name: TextView = item.title
        val desc: TextView = item.repoDesc
        val stargazer: TextView = item.stargazer
        val timeAgo: TextView = item.timeAgo
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.repo_list, parent, false)
        return RepoAdapter.ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RepoAdapter.ViewHolder, position: Int) {
        val currentItem = listRepo[position]
        holder.name.text = currentItem.name
        holder.desc.text = currentItem.description
        holder.stargazer.text = currentItem.stargazers_count.toString()
        timeAgo(currentItem.updated_at, holder.timeAgo)
    }

    override fun getItemCount(): Int {
        return listRepo.size
    }

    private fun timeAgo(time: String, holder: TextView) {
        var format: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        var txnTime: Date = format.parse(time)
        var timeNow: Date = Date()
        var seconds: Long = TimeUnit.MILLISECONDS.toSeconds(timeNow.time - txnTime.time)
        var minutes: Long = TimeUnit.MILLISECONDS.toMinutes(timeNow.time - txnTime.time)
        var hours: Long = TimeUnit.MILLISECONDS.toHours(timeNow.time - txnTime.time)
        var days: Long = TimeUnit.MILLISECONDS.toDays(timeNow.time - txnTime.time)

        if (seconds < 60) {
            holder.text = "Updated Just Now"
        } else if (minutes < 60) {
            holder.text = "Updated " + minutes.toString() + " Minutes Ago"
        } else if (hours < 60) {
            holder.text = "Updated " + hours.toString() + " Hours Ago"
        }else{
            holder.text = "Updated " + days.toString() + " Days Ago"
        }
    }
}