package com.example.tugas4_pam_nursatria

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tugas4_pam_nursatria.adapter.PostAdapter
import com.example.tugas4_pam_nursatria.api.ApiConfig
import com.example.tugas4_pam_nursatria.api.ApiService
import com.example.tugas4_pam_nursatria.model.Post
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var tvHeader: TextView
    private val api: ApiService = ApiConfig.retrofit.create(ApiService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        tvHeader = findViewById(R.id.tvHeader)
        tvHeader.text = "NIM: 235150700111034\nNama: Nur Satria Jatikusumah"

        fetchPosts()
    }

    private fun fetchPosts() {
        api.getPosts().enqueue(object : Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                if (response.isSuccessful) {
                    val posts = response.body()
                    if (posts != null) {
                        val adapter = PostAdapter(posts) { post ->
                            val intent = Intent(this@MainActivity, DetailActivity::class.java)
                            intent.putExtra("POST_ID", post.id)
                            startActivity(intent)
                        }
                        recyclerView.adapter = adapter
                    }
                } else {
                    Toast.makeText(this@MainActivity, "Gagal memuat data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Terjadi kesalahan jaringan", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
