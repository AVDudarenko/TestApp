package com.example.testapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import com.example.testapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
	private val binding by lazy {
		ActivityMainBinding.inflate(layoutInflater)
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
//		startService(MyService.newIntent(this))
		findViewById<Button>(R.id.btn_foreground_service).setOnClickListener {
			showNotification()
		}
		findViewById<Button>(R.id.btn_work_manager).setOnClickListener {
			val workManager = WorkManager.getInstance(applicationContext)
			workManager.enqueueUniqueWork(
				MyWorker.WORK_NAME,
				ExistingWorkPolicy.APPEND,
				MyWorker.makeRequest(page = 1)
			)
		}
	}

	private fun showNotification() {
		val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			val notificationChannel = NotificationChannel(
				CHANNEL_ID,
				CHANNEL_NAME,
				NotificationManager.IMPORTANCE_HIGH
			)
			notificationManager.createNotificationChannel(notificationChannel)
		}
		val notification = NotificationCompat.Builder(this, CHANNEL_ID)
			.setContentTitle("Title Test App")
			.setContentText("Text")
			.setSmallIcon(R.drawable.ic_launcher_background)
			.build()

		notificationManager.notify(1, notification)
	}

	companion object {
		private const val CHANNEL_ID = "channel_id"
		private const val CHANNEL_NAME = "channel_name"
	}
}