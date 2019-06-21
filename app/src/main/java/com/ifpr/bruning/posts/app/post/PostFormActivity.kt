package com.ifpr.bruning.posts.app.post

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.ifpr.bruning.posts.R
import com.ifpr.bruning.posts.RetrofitInstance
import com.ifpr.bruning.posts.models.Post
import com.ifpr.bruning.posts.services.PostService
import kotlinx.android.synthetic.main.activity_post_form.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostFormActivity : AppCompatActivity() {

    val currentAuthor = PostsActivity.currentUser
    lateinit var service: PostService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_form)
        supportActionBar?.title = "New Post"

        configureRetrofit()

        bt_add.setOnClickListener {
            storePost()
        }
    }

    private fun configureRetrofit() {
        service = RetrofitInstance.retrofit.create<PostService>(PostService::class.java)
    }

    private fun storePost() {
        val title = edit_title.text.toString()
        val body = edit_body.text.toString()
        val image = edit_image.text.toString()

        val callback = service.insert(currentAuthor?.id!!, title, image, body)
        callback.enqueue(object : Callback<Post>{
            override fun onFailure(call: Call<Post>, t: Throwable) {
                Log.d("ERRINHO", t.message, t)
            }

            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                val intent = Intent(this@PostFormActivity, PostsActivity::class.java)
                intent.putExtra("author", response.body()?.author_id.toString())
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        })
    }
}
