package com.example.notifikasi2

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.example.notifikasi2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val channelId = "TEST_NOTIF"
    private val notifId = 90

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Meminta izin notifikasi (Android 13+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 1)
            }
        }

        val notifManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Membuat Notification Channel (Android 8.0+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notifChannel = NotificationChannel(
                channelId,
                "Notifku",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notifManager.createNotificationChannel(notifChannel)
        }

        // Membuat Intent untuk membuka Activity saat tombol diklik
        val intent = Intent(this, LikeDislikeActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Tombol untuk mengirim notifikasi
        binding.btnNotif.setOnClickListener {
            val notifImage = BitmapFactory.decodeResource(resources, R.drawable.img) // Gambar besar

            // Gambar kecil untuk ditempatkan di sebelah kiri
            val smallImage = BitmapFactory.decodeResource(resources, R.drawable.ic_bell)
            val largeIcon = BitmapFactory.decodeResource(resources, R.drawable.icon)

            val builder = NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_bell) // Ikon kecil di status bar
                .setLargeIcon(largeIcon) // Menambahkan ikon besar di sebelah kiri teks
                .setContentTitle("Notifku")
                .setContentText("Click Like or Dislike!")

                // Memuat gambar dari drawable untuk ikon besar
                .setStyle(
                    NotificationCompat.BigPictureStyle()
                        .bigPicture(notifImage) // Menampilkan gambar besar di bawah notifikasi
                )
                .addAction(R.drawable.ic_thumb_up, "Like", pendingIntent) // Tombol Like
                .addAction(R.drawable.ic_thumb_down, "Dislike", pendingIntent) // Tombol Dislike
                .setAutoCancel(true) // Menghapus notifikasi setelah diklik
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            notifManager.notify(notifId, builder.build())
        }
    }
}
