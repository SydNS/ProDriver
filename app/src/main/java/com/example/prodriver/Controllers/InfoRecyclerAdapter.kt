package com.example.driversapp.Controllers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.driversapp.Controllers.InfoRecyclerAdapter.ViewHolder
import com.example.driversapp.Models.InfoData
import com.example.prodriver.R

class InfoRecyclerAdapter(private val infolist: List<InfoData>) :
    RecyclerView.Adapter<ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var title= itemView.findViewById<View>(R.id.title) as TextView
            var infoDesc= itemView.findViewById<View>(R.id.infoDesc) as TextView
            var datetime= itemView.findViewById<View>(R.id.datetime) as TextView
            var author= itemView.findViewById<View>(R.id.author) as TextView

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.inforow,
                parent,
                false
            )

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text=infolist[position].title
        holder.infoDesc.text=infolist[position].desc
        holder.datetime.text=infolist[position].dateTime
        holder.author.text=infolist[position].author
    }
    override fun getItemCount(): Int = infolist.size

}