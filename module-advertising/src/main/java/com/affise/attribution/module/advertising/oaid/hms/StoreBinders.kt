package com.affise.attribution.module.advertising.oaid.hms

import android.os.IBinder
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.TimeUnit

class StoreBinders(
    private val onBinderDied: () -> Unit
) : IBinder.DeathRecipient {

    private var binders: LinkedBlockingQueue<IBinder> = LinkedBlockingQueue(1)

    /**
     * Flag of is service connected
     */
    fun isEmpty(): Boolean = binders.isEmpty()

    override fun binderDied() {
        onBinderDied.invoke()
    }

    fun poll(timeOut: Long): OpenDeviceIdentifierService? = try {
        binders.poll(timeOut, TimeUnit.MILLISECONDS)?.let {
            binders.clear()

            binders.add(it)

            it.queryLocalInterface(OpenDeviceIdentifierServiceImpl.DESCRIPTOR)
                ?.let { iInterface ->
                    (iInterface as? OpenDeviceIdentifierService)
                }
                ?: OpenDeviceIdentifierServiceImpl(it)
        }
    } catch (e: InterruptedException) {
        null
    }

    fun add(service: IBinder) {
        clear()

        binders.add(service)
    }

    fun clear() {
        binders.clear()
    }
}