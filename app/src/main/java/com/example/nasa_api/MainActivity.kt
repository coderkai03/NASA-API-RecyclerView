package com.example.nasa_api

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import java.security.MessageDigest

class MainActivity : AppCompatActivity() {
    val apikey = "c4ZzyyrOj83uEMRgyWzvWA3dmoU1RSzDiBTvYIHD"
    val timestamp = System.currentTimeMillis()
    val baseurl = "https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pic = findViewById<ImageView>(R.id.nasapic)
        val text1 = findViewById<TextView>(R.id.text1)
        val text2 = findViewById<TextView>(R.id.text2)
        val next = findViewById<Button>(R.id.next)
    }

    private fun getNextData(pic: ImageView, t1: TextView, t2: TextView, next: Button) {
        next.setOnClickListener{
            val randId = (0..1000).random() //change later
            getURL(pic, t1, t2, randId)
        }
    }

    private fun getURL(pic: ImageView, t1: TextView, t2: TextView, randnum: Int) {
        val client = AsyncHttpClient()

        //need to include timestamp later?
        val url ="$baseurl?sol=$randnum&api_key=$apikey"

        client[url, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers?, json: JsonHttpResponseHandler.JSON) {
                Log.d("NASA", "response successful $json")
//                t1.text =
            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                TODO("Not yet implemented")
            }

        }]
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