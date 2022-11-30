package com.example.task2

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ColorAdaptor(private val colordetails :ArrayList<color_details>) : RecyclerView.Adapter<ColorAdaptor.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorAdaptor.MyViewHolder {
        val itemview = LayoutInflater.from(parent.context).inflate(R.layout.colorview_item,parent,false)
        return MyViewHolder(itemview)
    }


    override fun getItemCount(): Int {
        return colordetails.size
    }

    class MyViewHolder(itemview : View) : RecyclerView.ViewHolder(itemview){
        val item_color : View = itemview.findViewById(R.id.item_color)
        val color_name : TextView = itemview.findViewById(R.id.color_name)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = colordetails[position]
        holder.item_color.background.setTint(Color.parseColor(currentItem.hexValue)) //= currentItem.hexValue
        holder.color_name.text = currentItem.colourName
    }
}