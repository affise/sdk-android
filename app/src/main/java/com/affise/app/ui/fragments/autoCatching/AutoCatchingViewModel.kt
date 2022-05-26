package com.affise.app.ui.fragments.autoCatching

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.affise.app.application.App
import com.affise.attribution.Affise
import com.affise.attribution.events.autoCatchingClick.AutoCatchingType
import javax.inject.Inject

class AutoCatchingViewModel @Inject constructor(
    private val preferences: SharedPreferences
) : ViewModel(), AutoCatchingContract.ViewModel {

    override val checkedItems = MutableLiveData(
        preferences.getStringSet(App.AUTO_CATCHING_TYPES_KEY, null)
            ?.map { AutoCatchingType.valueOf(it) }
            ?: emptyList()
    )

    override fun setSelected(position: Int, checked: Boolean) {
        checkedItems.value = checkedItems.value?.toMutableList()?.apply {
            val item = AutoCatchingType.values()[position]

            if (checked) add(item) else remove(item)

            preferences.edit()
                .putStringSet(App.AUTO_CATCHING_TYPES_KEY, this.map { it.name }.toSet())
                .apply()

            Affise.setAutoCatchingTypes(this)
        }
    }
}