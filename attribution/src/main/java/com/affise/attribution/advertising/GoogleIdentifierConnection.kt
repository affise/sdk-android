package com.affise.attribution.advertising

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.TimeUnit

class GoogleIdentifierConnection : ServiceConnection {

    /**
     *  Google Advertising ID is retrieved or not
     */
    private var retrieved = false

    /**
     * Queue for retrieved
     */
    private val queue = LinkedBlockingQueue<IBinder>(1)

    /**
     * Called when a connection to the Service has been established
     */
    override fun onServiceConnected(name: ComponentName, service: IBinder) = try {
        queue.put(service)
    } catch (localInterruptedException: InterruptedException) {
    }

    /**
     * Called when a connection to the Service has been lost.
     */
    override fun onServiceDisconnected(name: ComponentName) = Unit

    /**
     * Get IBinder
     */
    fun getBinder(): IBinder {
        //Check retrieved
        check(!retrieved)

        //Change flag of retrieved
        retrieved = true

        return queue.poll(TIME_WAIT, TimeUnit.SECONDS) as IBinder
    }

    companion object {
        private const val TIME_WAIT = 10L
    }
}
