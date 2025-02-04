package com.affise.attribution.usecase

import android.content.SharedPreferences

internal class IndexUseCaseImpl(
    private val preferences: SharedPreferences,
) : IndexUseCase {

    private var uuidIndexValue: Long = preferences.getLong(UUID_INDEX_KEY, 0)
    private var affiseEventIdIndexValue: Long = preferences.getLong(AFFISE_EVENT_ID_INDEX_KEY, 0)

    @Synchronized
    override fun getUuidIndex(): Long {
        uuidIndexValue++

        //Save index to preferences
        prefSave(UUID_INDEX_KEY, uuidIndexValue)

        return uuidIndexValue
    }

    @Synchronized
    override fun getAffiseEventIdIndex(): Long {
        affiseEventIdIndexValue++

        //Save index to preferences
        prefSave(AFFISE_EVENT_ID_INDEX_KEY, uuidIndexValue)

        return affiseEventIdIndexValue
    }

    private fun prefSave(key: String, value: Long) {
        preferences.edit().apply {
            putLong(key, value)
        }.commit()
    }

    companion object {
        private const val UUID_INDEX_KEY = "com.affise.attribution.index.UUID_INDEX_KEY"
        private const val AFFISE_EVENT_ID_INDEX_KEY =
            "com.affise.attribution.index.AFFISE_EVENT_ID_INDEX_KEY"
    }
}