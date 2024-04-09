package ro.pub.cs.systems.eim.practicaltest01var03

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat

class PracticalTest01Var03Service : Service() {
    private var processingThread: ProcessingThread? = null

    override fun onCreate() {
        super.onCreate()

        val channel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel(
                Constants.CHANNEL_ID,
                "Running Notifications",
                NotificationManager.IMPORTANCE_HIGH
            )
        } else {
            null
        }

        val notificationManager = getSystemService(NotificationManager::class.java) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (channel != null) {
                notificationManager.createNotificationChannel(channel)
            }
        }

        val notification = NotificationCompat.Builder(this, Constants.CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("PracticalTest01Var03Service")
            .setContentText("Service is running")
            .build()

        startForeground(1, notification)
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("PracticalTest01Var03Service",
            "onStartCommand() callback method was invoked")

//        val input1 = intent?.getIntExtra(Constants.INPUT1, 0) ?: 0
//        val input2 = intent?.getIntExtra(Constants.INPUT2, 0) ?: 0

        val input1 = 0
        val input2 = 0

        Log.d("[Service]", "input1 is $input1")
        Log.d("[Service]", "input2 is $input2")

        processingThread = ProcessingThread(this, input1, input2)
        processingThread?.start()

        return START_REDELIVER_INTENT
    }
}