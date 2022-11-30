package com.example.task2


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso


class ListAdaptor(private val productList : ArrayList<product_details>) : RecyclerView.Adapter<ListAdaptor.MyViewHolder>() {

    private lateinit var listner : onItemClickListener

    interface onItemClickListener{
        fun onItemClicked(position: Int)
    }

    fun setOnItemClickListener(list:onItemClickListener){
        listner = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemview = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item,parent,false)
        return MyViewHolder(itemview,listner)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val currentItem = productList[position]
            Picasso.get().load(currentItem.image).into(holder.item_image)
            holder.item_name.text = currentItem.name
            holder.item_price.text = currentItem.price
            holder.item_link.text = currentItem.link

    }

    override fun getItemCount(): Int {
        return productList.size
    }
    class MyViewHolder(itemview : View , listener: onItemClickListener) : RecyclerView.ViewHolder(itemview){
        val item_image : ImageView = itemview.findViewById(R.id.item_image)
        val item_name : TextView = itemview.findViewById(R.id.item_name)
        val item_price :  TextView = itemview.findViewById(R.id.item_price)
        val item_link : TextView = itemview.findViewById(R.id.brand_name)

        init{
            itemview.setOnClickListener{
                listener.onItemClicked(adapterPosition)
            }
        }

    }
}