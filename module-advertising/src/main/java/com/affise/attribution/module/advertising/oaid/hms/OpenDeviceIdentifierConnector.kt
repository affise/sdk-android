package com.affise.attribution.module.advertising.oaid.hms

import android.content.ComponentName
import android.content.Context
import android.content.ServiceConnection
import android.os.IBinder

class OpenDeviceIdentifierConnector(
    private val context: Context,
) : ServiceConnection {
    /**
     * Flag of start service binding with this connection
     */
    private var startBinding = false

    /**
     * Store of binders
     */
    private val storeBinders = StoreBinders {
        unbindAndReset()
    }

    /**
     * Flag of is service connected
     */
    val isServiceConnected: Boolean = !storeBinders.isEmpty()

    /**
     * Get Open device identifier service with timeOut
     */
    fun getOpenDeviceIdentifierService(
        timeOut: Long
    ): OpenDeviceIdentifierService? = storeBinders.poll(timeOut)

    override fun onServiceConnected(name: ComponentName, service: IBinder) {
        storeBinders.add(service)
    }

    override fun onServiceDisconnected(name: ComponentName) {
        storeBinders.clear()
    }

    override fun onBindingDied(name: ComponentName) {
        unbindAndReset()
    }

    override fun onNullBinding(name: ComponentName) {
        unbindAndReset()
    }

    @Synchronized
    fun unbindAndReset() {
        if (startBinding) {
            try {
                startBinding = false

                storeBinders.clear()

                context.unbindService(this)
            } catch (e: Exception) {
            }
        }
    }

    fun startBindings() {
        startBinding = true
    }
}