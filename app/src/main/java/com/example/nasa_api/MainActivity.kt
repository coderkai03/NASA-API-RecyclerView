package com.example.nasa_api

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import java.security.MessageDigest

class MainActivity : AppCompatActivity() {
    val apikey = "9iQlN2XMftiNNSFuReywkEMZLHxZGoH0E5uBxffL"
    val timestamp = System.currentTimeMillis()
    val baseurl = "https://api.nasa.gov/planetary/apod"

    private lateinit var NASAList: MutableList<NASA_data>
    private lateinit var rv_NASA: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv_NASA = findViewById(R.id.nasa_list)
        NASAList = mutableListOf()

        getNASAData()
    }


    private fun getNASAData() {
        val client = AsyncHttpClient()

        //need to include timestamp later?
        val url ="$baseurl?api_key=$apikey&count=10" //also try without count

        Log.d("getURL()", "function called")

        client[url, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers?, json: JsonHttpResponseHandler.JSON) {
                Log.d("NASA", "response successful $json")

                if (json != null && json.jsonArray != null) {
                    val jsonArray = json.jsonArray
                    for (i in 0 until jsonArray.length()){
                        val obj = jsonArray.getJSONObject(i)
                        val img = obj.getString("url")
                        val title = obj.getString("title")
                        val explanation = obj.getString("explanation")

                        NASAList.add(NASA_data(img, title, explanation))
                    }

                    val adapter = NASA_Adapter(NASAList)
                    rv_NASA.adapter = adapter
                    rv_NASA.layoutManager = LinearLayoutManager(this@MainActivity)

                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String,
                throwable: Throwable?
            ) {
                Log.d("NASA Error", response)
            }

        }]
    }

//    private fun loadImage(imageUrl: String, imageView: ImageView) {
//        Glide.with(this)
//            .load(imageUrl)
//            .fitCenter()
//            .into(imageView)
//    }
//
//    private fun generateHash(timestamp: Long): String {
//        val input = "$timestamp$apikey"
//        val md = MessageDigest.getInstance("MD5")
//        val digest = md.digest(input.toByteArray())
//        val result = StringBuilder()
//
//        for (byte in digest) {
//            result.append(String.format("%02x", byte))
//        }
//
//        return result.toString()
//    }
}