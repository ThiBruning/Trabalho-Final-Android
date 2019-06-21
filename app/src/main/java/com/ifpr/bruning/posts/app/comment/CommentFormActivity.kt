package com.ifpr.bruning.posts.app.comment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.ifpr.bruning.posts.R
import com.ifpr.bruning.posts.RetrofitInstance
import com.ifpr.bruning.posts.app.post.PostInfoActivity
import com.ifpr.bruning.posts.app.post.PostsActivity
import com.ifpr.bruning.posts.models.Comment
import com.ifpr.bruning.posts.services.CommentService
import kotlinx.android.synthetic.main.activity_comment_form.*
import kotlinx.android.synthetic.main.comment_item.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommentFormActivity : AppCompatActivity() {

    lateinit var serviceComment: CommentService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment_form)
        configureRetrofit()

        bt_new_comment.setOnClickListener {
            createComment()
        }

        txt_comment_form.text = PostInfoActivity.currentPost?.title
    }

    private fun createComment() {
        val body = edit_comment_body.text.toString()
        val callback = serviceComment.insert(PostInfoActivity.currentPost?.id!!, PostsActivity.currentUser?.id!!, body)
        callback.enqueue(object : Callback<Comment>{
            override fun onFailure(call: Call<Comment>, t: Throwable) {
                Toast.makeText(this@CommentFormActivity, t.message, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Comment>, response: Response<Comment>) {
                finish()
            }
        })
    }

    private fun configureRetrofit() {
        serviceComment = RetrofitInstance.retrofit.create<CommentService>(CommentService::class.java)
    }
}
