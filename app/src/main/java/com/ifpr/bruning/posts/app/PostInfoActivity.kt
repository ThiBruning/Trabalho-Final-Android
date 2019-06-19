package com.ifpr.bruning.posts.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ifpr.bruning.posts.R
import com.ifpr.bruning.posts.RetrofitInstance
import com.ifpr.bruning.posts.services.PostService

class PostInfoActivity : AppCompatActivity() {

//    private val retrofitClient = RetrofitInstance
//        .getRetrofitInstance("http://192.168.100.81:8000")
//    private val service = retrofitClient.create(PostService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_info)
    }
}
