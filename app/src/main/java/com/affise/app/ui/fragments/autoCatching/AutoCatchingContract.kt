package com.affise.app.ui.fragments.autoCatching

import androidx.lifecycle.LiveData
import com.affise.attribution.events.autoCatchingClick.AutoCatchingType

interface AutoCatchingContract {

    interface ViewModel {
        fun setSelected(position: Int, checked: Boolean)

        val checkedItems: LiveData<List<AutoCatchingType>>
    }
}