package com.example.nasa_api

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import java.security.MessageDigest

class MainActivity : AppCompatActivity() {
    val apikey = "9iQlN2XMftiNNSFuReywkEMZLHxZGoH0E5uBxffL"
    val timestamp = System.currentTimeMillis()
    val baseurl = "https://api.nasa.gov/planetary/apod"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pic = findViewById<ImageView>(R.id.nasapic)
        val text1 = findViewById<TextView>(R.id.text1)
        val text2 = findViewById<TextView>(R.id.text2)
        val next = findViewById<Button>(R.id.next)

        getNextData(pic, text1, text2, next)
    }

    private fun getNextData(pic: ImageView, t1: TextView, t2: TextView, next: Button) {
        next.setOnClickListener{
            val randId = (0..9).random() //change later
            getURL(pic, t1, t2, randId)
        }
    }

    private fun getURL(pic: ImageView, t1: TextView, t2: TextView, randnum: Int) {
        val client = AsyncHttpClient()

        //need to include timestamp later?
        val url ="$baseurl?api_key=$apikey&count=10" //also try without count

        Log.d("getURL()", "function called")

        client[url, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers?, json: JsonHttpResponseHandler.JSON) {
                Log.d("NASA", "response successful $json")

                if (json != null && json.jsonArray != null) {
                    val jsonArray = json.jsonArray
                    val obj = jsonArray.getJSONObject(randnum)
                    val img = obj.getString("url")
                    val title = obj.getString("title")
                    val explanation = obj.getString("explanation")

                    t1.text = title
                    t2.text = explanation

                    loadImage(img, pic)
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

    private fun loadImage(imageUrl: String, imageView: ImageView) {
        Glide.with(this)
            .load(imageUrl)
            .fitCenter()
            .into(imageView)
    }

    private fun generateHash(timestamp: Long): String {
        val input = "$timestamp$apikey"
        val md = MessageDigest.getInstance("MD5")
        val digest = md.digest(input.toByteArray())
        val result = StringBuilder()

        for (byte in digest) {
            result.append(String.format("%02x", byte))
        }

        return result.toString()
    }
}