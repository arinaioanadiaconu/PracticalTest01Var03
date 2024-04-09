package ro.pub.cs.systems.eim.practicaltest01var03

import android.content.Context
import android.content.Intent
import android.util.Log

class ProcessingThread
    (
    private val context: Context,
    input1: Int,
    input2: Int
): Thread() {

    private val sum = input1 + input2
    private val dif = input1 - input2

    override fun run() {
        sendSumBroadcastMessage()
        sleep()
        sendDifBroadcastMessage()
    }

    private fun sleep() {
        try {
            sleep(Constants.SLEEP)
        } catch (interruptedException: InterruptedException) {
            Log.d("ProcessThread", "Thread has stopped!")
        }
    }

    private fun sendSumBroadcastMessage() {
        val intent = Intent()
        intent.action = Constants.ACTION1
        intent.putExtra(
            Constants.BROADCAST_RECEIVER_DATA,
            "Sum is: $sum"
        )
        context.sendBroadcast(intent)
    }

    private fun sendDifBroadcastMessage() {
        val intent = Intent()
        intent.action = Constants.ACTION2
        intent.putExtra(
            Constants.BROADCAST_RECEIVER_DATA,
            "Dif is: $dif"
        )
        context.sendBroadcast(intent)
    }
}