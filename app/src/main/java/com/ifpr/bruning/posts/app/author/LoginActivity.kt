package com.ifpr.bruning.posts.app.author

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ifpr.bruning.posts.R
import com.ifpr.bruning.posts.RetrofitInstance
import com.ifpr.bruning.posts.app.PostsActivity
import com.ifpr.bruning.posts.models.Author
import com.ifpr.bruning.posts.services.AuthorService
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var service: AuthorService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        configureRetrofit()

        bt_login.setOnClickListener {
            login()
        }

        txt_dont_have_account.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        edit_login_email.setText(intent.getStringExtra("email"))
    }

    private fun configureRetrofit() {
        service = RetrofitInstance.retrofit.create<AuthorService>(AuthorService::class.java)
    }

    private fun login() {
        val email = edit_login_email.text.toString()
        val password = edit_login_password.text.toString()
        val callback = service.login(email, password)

        callback.enqueue(object : Callback<Author>{
            override fun onFailure(call: Call<Author>, t: Throwable) {
                Toast.makeText(this@LoginActivity, t.message, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Author>, response: Response<Author>) {
                if (response.body()?.name == null)
                    Toast.makeText(this@LoginActivity, "Incorrect credentials", Toast.LENGTH_LONG).show()
                else {
                    val intent = Intent(this@LoginActivity, PostsActivity::class.java)
                    intent.putExtra("author", response.body()?.id.toString())
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
            }
        })
    }
}
