package com.affise.attribution.module.advertising.oaid.hms

import android.os.Binder
import android.os.IBinder
import android.os.Parcel


class OpenDeviceIdentifierServiceImpl(private val iBinder: IBinder) : OpenDeviceIdentifierService {

    /**
     * Get IBinder
     */
    override fun asBinder() = iBinder

    /**
     * Get Oaid
     */
    override fun getOaid(): String? {
        val data = Parcel.obtain()
        val reply = Parcel.obtain()

        return try {
            data.writeInterfaceToken(DESCRIPTOR)
            iBinder.transact(Binder.FIRST_CALL_TRANSACTION + 0, data, reply, 0)
            reply.readException()
            reply.readString()
        } catch (e: Throwable) {
            null
        } finally {
            reply.recycle()
            data.recycle()
        }
    }

    companion object {
        const val DESCRIPTOR = "com.uodis.opendevice.aidl.OpenDeviceIdentifierService"
    }
}