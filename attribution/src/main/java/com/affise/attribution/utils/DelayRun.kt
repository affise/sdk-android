package com.affise.attribution.utils

import java.util.*


internal fun delayRun(delay: Long, run: () -> Unit) {
    Timer().let { it ->
        it.schedule(object : TimerTask() {
            override fun run() {
                //Delay execution
                run.invoke()

                //Stop timer
                it.cancel()
            }
        }, delay)
    }
}