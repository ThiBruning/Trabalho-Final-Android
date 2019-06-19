package com.ifpr.bruning.posts.app.author

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.ifpr.bruning.posts.R
import com.ifpr.bruning.posts.RetrofitInstance
import com.ifpr.bruning.posts.models.Author
import com.ifpr.bruning.posts.services.AuthorService
import kotlinx.android.synthetic.main.activity_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    lateinit var service: AuthorService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        configureRetrofit()

        bt_register.setOnClickListener {
            register()
        }

        txt_have_account.setOnClickListener {
            finish()
        }
    }

    private fun configureRetrofit() {
        service = RetrofitInstance.retrofit.create<AuthorService>(AuthorService::class.java)
    }

    private fun register() {
        val name = edit_name.text.toString()
        val email = edit_email.text.toString()
        val password = edit_password.text.toString()
        val callback = service.insert(name, email, password)

        callback.enqueue(object : Callback<Author>{
            override fun onFailure(call: Call<Author>, t: Throwable) {
                Toast.makeText(this@RegisterActivity, t.message, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Author>, response: Response<Author>) {
                if (response.body()?.name == null)
                    Log.d("AUTHOR", "usuario nao cadastrado")
                else {
                    val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                    intent.putExtra("email", email)
                    startActivity(intent)
                    Log.d("AUTHOR2", response.body()?.email)
                }
            }
        })

    }
}
