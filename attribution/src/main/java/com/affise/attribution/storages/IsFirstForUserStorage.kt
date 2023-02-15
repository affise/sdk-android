package com.affise.attribution.storages


interface IsFirstForUserStorage {
    fun add(eventClass: String)
    fun getEventsNames(): List<String>
}