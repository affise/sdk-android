package com.affise.attribution.utils

import java.util.*


private fun get64LeastSignificantBitsForVersion1(): Long {
    val random = Random()
    val random63BitLong = random.nextLong() and 0x3FFFFFFFFFFFFFFFL
    val variant3BitFlag = Long.MIN_VALUE
    return random63BitLong or variant3BitFlag
}

private fun get64MostSignificantBitsForVersion1(): Long {
    val currentTimeMillis = System.currentTimeMillis()
    val timeLow = currentTimeMillis and 0x00000000FFFFFFFFL shl 32
    val timeMid = currentTimeMillis shr 32 and 0xFFFFL shl 16
    val version = (1 shl 12).toLong()
    val timeHi = currentTimeMillis shr 48 and 0x0FFFL
    return timeLow or timeMid or version or timeHi
}

private fun generateType1UUID(): UUID {
    val most64SigBits = get64MostSignificantBitsForVersion1()
    val least64SigBits = get64LeastSignificantBitsForVersion1()
    return UUID(most64SigBits, least64SigBits)
}

fun generateUUID() : UUID {
    return generateType1UUID()
}