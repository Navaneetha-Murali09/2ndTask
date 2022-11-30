package com.example.task2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.SearchView
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList
import kotlin.reflect.typeOf

class MainActivity : AppCompatActivity(), View.OnClickListener {


    lateinit var product : product_details
    lateinit var product_list: RecyclerView
    lateinit var arraylistproduct : ArrayList<product_details>
    lateinit var tvSearchbar : SearchView
    var item_image: ArrayList<String> = arrayListOf()
    var item_name: ArrayList<String> = arrayListOf()
    var item_price: ArrayList<String> = arrayListOf()
    var brand_link: ArrayList<String> = arrayListOf()
    var product_col : ArrayList<ProductColors> = arrayListOf()
    var ProductList = ArrayList<product_details>()
    var tempArray = ArrayList<product_details>()
    val item_color : ArrayList<String> = arrayListOf()
    val item_hex : ArrayList<String> = arrayListOf()
    lateinit var price: String




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvSearchbar= findViewById(R.id.tvSearchbar)
        getSupportActionBar()?.hide()
        product_list = findViewById(R.id.product_list)
        product_list.layoutManager = LinearLayoutManager(this)
        product_list.setHasFixedSize(true)
        tempArray = arrayListOf()
        getProductDetails()
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun getProductDetails() {
        arraylistproduct = arrayListOf()
        val apiCall = APIClientRequest.getInstance().create(APIInterface::class.java)
        GlobalScope.launch {
            var postResponseData = apiCall.getProductInfo() //interface
            postResponseData.enqueue(object : Callback<List<ResponseData>>{

                override fun onResponse(call: Call<List<ResponseData>>, response: Response<List<ResponseData>>) {
                    if (response.isSuccessful){
                       var responseData = response.body()!!
                        for(product_response in responseData){
                            item_image+=product_response.imageLink.toString()
                            item_name+=product_response.name.toString()

                            brand_link+=product_response.productLink.toString()
                            product_col+=product_response.productColors
                            price = product_response.priceSign.toString() + " "+ product_response.price.toString()
                            //item_price+=product_response.price.toString()
                            item_price+=price
                        }

                        for( i in item_image.indices){
                            product = product_details(item_image[i],item_name[i],item_price[i],brand_link[i])
                            ProductList.add(product)
                            tempArray.add(product)

                        }
                        var adaptor = ListAdaptor(tempArray)
                        product_list.adapter = adaptor



                        adaptor.setOnItemClickListener(object : ListAdaptor.onItemClickListener{
                            override fun onItemClicked(position: Int) {

                                item_color.clear()
                                item_hex.clear()
                                val intent = Intent(this@MainActivity,ProductDetailsActivity::class.java)
                                intent.putExtra("product_name",responseData[position].name)
                                intent.putExtra("product_image",responseData[position].imageLink)
                                intent.putExtra("product_details",responseData[position].description)
                                intent.putExtra("product_id",responseData[position].id)
                                for(k in responseData[position].productColors.indices){

                                    item_hex+=responseData[position].productColors[k].hexValue.toString()
                                    item_color+=responseData[position].productColors[k].colourName.toString()
                                }
                                intent.putStringArrayListExtra("hexvals",item_hex)
                                intent.putStringArrayListExtra("colname",item_color)
                                startActivity(intent)
                            }
                        })
                    }

                    tvSearchbar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                        override fun onQueryTextSubmit(query: String?): Boolean {
                            TODO("Not yet implemented")
                        }

                        override fun onQueryTextChange(newText: String?): Boolean {
                            tempArray.clear()
                            val searchText = newText!!.toLowerCase(Locale.getDefault())
                            if (searchText.isNotEmpty()) {
                                ProductList.forEach {
                                    if (it.name.toLowerCase(Locale.getDefault()).contains(searchText)) {
                                        tempArray.add(it)
                                    }
                                }
                                product_list.adapter!!.notifyDataSetChanged()
                            }else{
                                tempArray.clear()
                                tempArray.addAll(ProductList)
                                product_list.adapter!!.notifyDataSetChanged()
                            }
                            return false
                        }
                    })

                }
                override fun onFailure(call: Call<List<ResponseData>>, t: Throwable) {
                    Log.i("Error in loading API", "${t.message}")
                }
            })
        }
    }

    override fun onClick(p0: View?) {

    }



}
