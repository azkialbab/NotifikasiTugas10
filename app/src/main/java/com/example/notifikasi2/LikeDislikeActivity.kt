package com.example.notifikasi2

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.notifikasi2.databinding.ActivityLikeDislikeBinding

class LikeDislikeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLikeDislikeBinding
    private var likeCount = 0
    private var dislikeCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLikeDislikeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Menampilkan nilai awal
        updateCounters()

        // Tombol Like
        binding.btnLike.setOnClickListener {
            likeCount++
            updateCounters()
        }

        // Tombol Dislike
        binding.btnDislike.setOnClickListener {
            dislikeCount++
            updateCounters()
        }
    }

    private fun updateCounters() {
        binding.tvLikeCount.text = "Like: $likeCount"
        binding.tvDislikeCount.text = "Dislike: $dislikeCount"
    }
}
