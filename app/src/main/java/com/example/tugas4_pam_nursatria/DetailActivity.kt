package com.example.tugas4_pam_nursatria

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tugas4_pam_nursatria.api.ApiConfig
import com.example.tugas4_pam_nursatria.api.ApiService
import com.example.tugas4_pam_nursatria.model.Post
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {

    private lateinit var tvTitle: TextView
    private lateinit var tvBody: TextView
    private lateinit var tvHeader: TextView

    private val api: ApiService = ApiConfig.retrofit.create(ApiService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        tvTitle = findViewById(R.id.tvTitle)
        tvBody = findViewById(R.id.tvBody)
        tvHeader = findViewById(R.id.tvHeader)
        tvHeader.text = getString(R.string.nama)

        val postId = intent.getIntExtra("POST_ID", -1)
        if (postId != -1) {
            fetchPostDetail(postId)
        } else {
            Toast.makeText(this, "ID post tidak ditemukan", Toast.LENGTH_SHORT).show()
        }
    }

    private fun fetchPostDetail(postId: Int) {
        api.getPostById(postId).enqueue(object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                if (response.isSuccessful && response.body() != null) {
                    val post = response.body()!!
                    tvTitle.text = post.title
                    tvBody.text = post.body
                } else {
                    Toast.makeText(this@DetailActivity, "Data tidak ditemukan", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                Toast.makeText(this@DetailActivity, "Gagal mengambil data", Toast.LENGTH_SHORT).show()
            }
        })
    }
}