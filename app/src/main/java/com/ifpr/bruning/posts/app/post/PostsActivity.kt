package com.ifpr.bruning.posts.app.post

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.ifpr.bruning.posts.R
import com.ifpr.bruning.posts.RetrofitInstance
import com.ifpr.bruning.posts.app.author.LoginActivity
import com.ifpr.bruning.posts.models.Author
import com.ifpr.bruning.posts.models.Post
import com.ifpr.bruning.posts.services.AuthorService
import com.ifpr.bruning.posts.services.PostService
import com.ifpr.bruning.posts.ui.items.PostItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_posts.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostsActivity : AppCompatActivity() {

    companion object {
        var currentUser: Author? = null
    }

    lateinit var serviceAuthor: AuthorService
    lateinit var servicePost: PostService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posts)
        supportActionBar?.title = "All Posts"

        configureRetrofit()
        getCurrentUser()

        swipePosts.setOnRefreshListener {
            getPosts()
        }

        bt_new.setOnClickListener {
            intent = Intent(this, PostFormActivity::class.java)
            startActivity(intent)
        }
    }

    private fun configureRetrofit() {
        serviceAuthor = RetrofitInstance.retrofit.create<AuthorService>(AuthorService::class.java)
        servicePost = RetrofitInstance.retrofit.create<PostService>(PostService::class.java)
    }

    private fun getCurrentUser() {
        val idAuthor = intent.getStringExtra("author").toLong()
        val callback = serviceAuthor.find(idAuthor)

        callback.enqueue(object : Callback<Author>{
            override fun onFailure(call: Call<Author>, t: Throwable) {}

            override fun onResponse(call: Call<Author>, response: Response<Author>) {
                currentUser = response.body()
                getPosts()
            }
        })
    }

    private fun getPosts() {
        swipePosts.isRefreshing = true
        val callback = servicePost.allPosts()
        callback.enqueue(object : Callback<List<Post>>{
            override fun onFailure(call: Call<List<Post>>, t: Throwable) {}

            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                swipePosts.isRefreshing = false
                loadRecyclerView(response.body()!!)
            }
        })
    }

    private fun loadRecyclerView(posts: List<Post>) {
        val adapter = GroupAdapter<ViewHolder>()
        adapter.setOnItemClickListener { item, view ->
            item as PostItem
            val intent = Intent(this, PostInfoActivity::class.java)
            intent.putExtra("post", item.post.id.toString())
            startActivity(intent)
        }
        posts.forEach {
            adapter.add(PostItem(it))
        }
        recycler_posts.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.my_posts -> {
                Toast.makeText(this, "Listando meus posts", Toast.LENGTH_LONG).show()
            }

            R.id.sign_out -> {
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
