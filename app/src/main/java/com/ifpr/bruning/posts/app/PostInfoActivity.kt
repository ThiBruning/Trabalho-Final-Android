package com.ifpr.bruning.posts.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.ifpr.bruning.posts.R
import com.ifpr.bruning.posts.RetrofitInstance
import com.ifpr.bruning.posts.models.Author
import com.ifpr.bruning.posts.models.Comment
import com.ifpr.bruning.posts.models.Post
import com.ifpr.bruning.posts.services.AuthorService
import com.ifpr.bruning.posts.services.PostService
import com.ifpr.bruning.posts.ui.items.CommentItem
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_post_info.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostInfoActivity : AppCompatActivity() {

    companion object {
        var currentUser: Author? = null
        var currentPost: Post? = null
    }

    lateinit var serviceAuthor: AuthorService
    lateinit var servicePost: PostService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_info)

        configureRetrofit()
        getPost()
    }

    private fun configureRetrofit() {
        serviceAuthor = RetrofitInstance.retrofit.create<AuthorService>(AuthorService::class.java)
        servicePost = RetrofitInstance.retrofit.create<PostService>(PostService::class.java)
    }

    private fun getPost() {
        val postId = intent.getStringExtra("post").toLong()
        val callback = servicePost.show(postId)
        callback.enqueue(object : Callback<Post> {
            override fun onFailure(call: Call<Post>, t: Throwable) {}

            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                PostInfoActivity.currentPost = response.body()
                Toast.makeText(this@PostInfoActivity, PostsActivity.currentUser?.name, Toast.LENGTH_LONG).show()
                txt_info_title.text = response.body()?.title
                txt_info_author.text = response.body()?.authorName
                txt_info_body.text = response.body()?.body
                Picasso.get().load(response.body()?.image).into(image_info)
                txt_info_comments.text = response.body()?.comments?.size.toString()
                loadComments(response.body()?.comments)
            }
        })
    }

    private fun loadComments(comments: List<Comment>?) {
        val adapter = GroupAdapter<ViewHolder>()
        comments?.forEach {
            adapter.add(CommentItem(it))
        }
        recycler_comments.adapter = adapter
    }
}
