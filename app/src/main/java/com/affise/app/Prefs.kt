package com.affise.app


object Prefs {

    private val pref = App.pref

    fun applyBoolean(key: String, value: Boolean) {
        pref?.edit()
            ?.putBoolean(key, value)
            ?.apply()
    }

    fun applyString(key: String, value: String) {
        pref?.edit()
            ?.putString(key, value)
            ?.apply()
    }

    fun string(key: String, default: String = ""): String {
        val value = pref?.getString(key, null)
        if (value.isNullOrBlank()) {
            return default
        }
        return value
    }

    fun boolean(key: String, default: Boolean = false): Boolean {
        return pref?.getBoolean(key, default) ?: default
    }
}
