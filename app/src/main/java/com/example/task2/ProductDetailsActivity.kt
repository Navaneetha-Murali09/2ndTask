package com.example.task2

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso


class ProductDetailsActivity : AppCompatActivity() {

    var ColorList = ArrayList<color_details>()
    lateinit var Color_list: RecyclerView
    lateinit var ProductName: TextView
    lateinit var product_image: ImageView
    lateinit var product_description: TextView

    override fun onCreate(savedInstanceState: Bundle?) {

        getSupportActionBar()?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.product_details)



        ProductName = findViewById(R.id.ProductName)
        product_image = findViewById(R.id.product_image)
        product_description = findViewById(R.id.product_description)

        val bundle: Bundle? = intent.extras
        val product_name = bundle?.getString("product_name")
        val product_image_url = bundle?.getString("product_image")
        val product_details = bundle?.getString("product_details")
        val product_colors = bundle?.getStringArrayList("colname")
        val color_hex = bundle?.getStringArrayList("hexvals")


        ProductName.text = product_name
        Picasso.get().load(product_image_url).into(product_image)
        product_description.text = product_details

        Color_list = findViewById(R.id.ColorView)
        Color_list.layoutManager = GridLayoutManager(this, 2)
        Color_list.setHasFixedSize(true)



        if (product_colors == null) {
            Log.i("color#", "${product_colors}")
        }
        else{
            for (colors in product_colors!!.indices) {

                val add_color = color_details(color_hex!![colors], product_colors[colors])
                ColorList.add(add_color)

            }
            Color_list.adapter = ColorAdaptor(ColorList)
        }
    }

}