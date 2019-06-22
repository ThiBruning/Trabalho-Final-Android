package com.ifpr.bruning.posts.app.post

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import com.ifpr.bruning.posts.R
import com.ifpr.bruning.posts.RetrofitInstance
import com.ifpr.bruning.posts.listener.PostListener
import com.ifpr.bruning.posts.models.Post
import com.ifpr.bruning.posts.services.PostService
import com.ifpr.bruning.posts.ui.items.PostItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_posts.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthorPostsActivity : AppCompatActivity(), PostListener {

    lateinit var service: PostService
    val adapter = GroupAdapter<ViewHolder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posts)
        supportActionBar?.title = "My Posts"

        configureRetrofit()
        getPosts()
        bt_new.isVisible = false

    }

    private fun configureRetrofit() {
        service = RetrofitInstance.retrofit.create<PostService>(PostService::class.java)
    }

    private fun getPosts() {
        val callback = service.authorPosts(PostsActivity.currentUser?.id!!)
        callback.enqueue(object : Callback<List<Post>>{
            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                Toast.makeText(this@AuthorPostsActivity, t.message, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                loadRecyclerView(response.body()!!)
            }
        })
    }

    private fun loadRecyclerView(posts: List<Post>) {
        posts.forEach {
            adapter.add(PostItem(it, true, this))
        }
        recycler_posts.adapter = adapter
    }

    override fun removePost(post: Post, position: Int) {
//        Log.d("DADOS", post.id.toString())
        val callback = service.deletePost(post.id)
        callback.enqueue(object : Callback<Post>{
            override fun onFailure(call: Call<Post>, t: Throwable) {
                Toast.makeText(this@AuthorPostsActivity, t.message, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                Toast.makeText(this@AuthorPostsActivity, "Post removido com sucesso!!", Toast.LENGTH_LONG).show()
                adapter.removeGroup(position)
            }
        })
    }
}