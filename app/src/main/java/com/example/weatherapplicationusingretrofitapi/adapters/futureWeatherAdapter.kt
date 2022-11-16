package com.example.weatherapplicationusingretrofitapi.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapplicationusingretrofitapi.R
import com.squareup.picasso.Picasso

class futureWeatherAdapter(val context:Context, val futureList:ArrayList<futureParams>):RecyclerView.Adapter<futureWeatherAdapter.futureWeatherAdapterViewHolder>(){

    class futureWeatherAdapterViewHolder(itemview: View) :RecyclerView.ViewHolder(itemview){
        val temp = itemview.findViewById<TextView>(R.id.futureTemperature)
        val icon  = itemview.findViewById<ImageView>(R.id.futureWeatherIcon)
        val cond = itemview.findViewById<TextView>(R.id.futureCondition)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): futureWeatherAdapterViewHolder {
        val view:View = LayoutInflater.from(context).inflate(R.layout.futurerecyclerviewelement, parent,false)
        return futureWeatherAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: futureWeatherAdapterViewHolder, position: Int) {
        val curr_temp = futureList[position].temp
        val curr_icon = futureList[position].icon
        val curr_cond = futureList[position].cond

        holder.temp.text = curr_temp
        holder.cond.text = curr_cond
        Picasso
            .get()
            .load("http:$curr_icon")
            .into(holder.icon)
    }

    override fun getItemCount(): Int {
        return futureList.size
    }


}