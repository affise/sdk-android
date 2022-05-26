package com.affise.attribution.advertising

import android.os.IBinder
import android.os.IInterface
import android.os.Parcel

class GoogleIdentifierInterface(private val iBinder: IBinder) : IInterface {

    /**
     * Get IBinder
     */
    override fun asBinder() = iBinder

    /**
     * Get Google Advertising ID
     */
    fun getGoogleAdid(): String? {
        val data = Parcel.obtain()
        val reply = Parcel.obtain()

        return try {
            data.writeInterfaceToken(IDENTIFIER_INTERFACE_TOKEN)
            iBinder.transact(1, data, reply, 0)
            reply.readException()
            reply.readString()
        } finally {
            reply.recycle()
            data.recycle()
        }
    }

    companion object {
        private const val IDENTIFIER_INTERFACE_TOKEN = "com.google.android.gms.ads.identifier.internal.IAdvertisingIdService"
    }
}