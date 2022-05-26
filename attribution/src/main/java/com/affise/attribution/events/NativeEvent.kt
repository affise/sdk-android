package com.affise.attribution.events

abstract class NativeEvent : Event() {
    override fun getCategory(): String = "native"
}