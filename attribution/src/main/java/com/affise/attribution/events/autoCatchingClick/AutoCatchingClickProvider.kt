package com.affise.attribution.events.autoCatchingClick

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.affise.attribution.events.StoreEventUseCase
import com.affise.attribution.utils.ActivityActionsManager
import com.affise.attribution.utils.ActivityClickCallback

/**
 * The provider that handles the interception of clicks on activity.
 * Collects data on pressed views depending on the selected types for auto catching
 *
 * @property storeEventUseCase use case for store events
 * @property activityActionsManager manager for handling events occurring on the activity
 */
internal class AutoCatchingClickProvider(
    private val storeEventUseCase: StoreEventUseCase,
    private val activityActionsManager: ActivityActionsManager
) {

    /**
     * Enabled types of auto catching click
     */
    private var types: List<AutoCatchingType>? = null

    /**
     * Listener to clicks on activities
     */
    private var activityClickCallback: ActivityClickCallback? = null

    /**
     * Start provider
     */
    @Synchronized
    fun init(types: List<AutoCatchingType>?) {
        this.types = types

        //Check calcback created
        if (activityClickCallback == null) {
            //Create calback
            activityClickCallback = ActivityClickCallback { activity, view ->
                //Crate and check data from view
                getDataView(view)?.let { data ->
                    //Store AutoCatchingClick event
                    storeEventUseCase.storeEvent(
                        AutoCatchingClickEvent(
                            view is ViewGroup,
                            data,
                            activity::class.java.simpleName
                        )
                    )
                }
            }.apply {
                //Add calback
                activityActionsManager.addOnActivityClickListener(this)
            }
        }
    }

    /**
     * Set types of auto catching click
     */
    fun setTypes(types: List<AutoCatchingType>?) {
        this.types = types
    }

    /**
     * Get data from [view]
     */
    private fun getDataView(view: View, isRoot: Boolean = true): List<AutoCatchingClickData>? {
        //Check enabled types for AutoCatchingClick
        val types = types ?: return null

        //Get id name from resource
        val idName = view.id
            .takeIf {
                //Take only view if it has name
                it != View.NO_ID
            }
            ?.runCatching {
                //Find name in resources
                view.resources?.getResourceEntryName(this)
            }
            ?.getOrNull()
            ?.ifEmpty { null }

        //Get tag from view
        val tag = view.tag?.toString()

        //View name
        val canonicalName = view::class.java.canonicalName ?: ""

        //View contentDescription
        val description = view.contentDescription?.toString()

        //Get view type and return  data from view
        return when (view) {
            is TextView -> listOf(
                when {
                    //iCheck ib Button add to AutoCatchingClick event
                    canonicalName.contains(KEY_BUTTON) -> if (types.contains(AutoCatchingType.BUTTON)) {
                        AutoCatchingClickData(
                            idName, view.text?.toString(), tag, AutoCatchingType.BUTTON.name
                        )
                    } else return null
                    //iCheck ib Text add to AutoCatchingClick event
                    types.contains(AutoCatchingType.TEXT) -> AutoCatchingClickData(
                        idName, view.text?.toString(), tag, AutoCatchingType.TEXT.name
                    )
                    else -> return null
                }
            )
            is ImageView -> listOf(
                when {
                    //iCheck ib Image Button add to AutoCatchingClick event
                    canonicalName.contains(KEY_BUTTON) -> if (types.contains(AutoCatchingType.IMAGE_BUTTON)) {
                        AutoCatchingClickData(
                            idName, description, tag, AutoCatchingType.IMAGE_BUTTON.name
                        )
                    } else return null
                    //iCheck ib Image add to AutoCatchingClick event
                    types.contains(AutoCatchingType.IMAGE) -> AutoCatchingClickData(
                        idName, description, tag, AutoCatchingType.IMAGE.name
                    )
                    else -> return null
                }
            )
            is ViewGroup -> {
                //iCheck ib View Group add to AutoCatchingClick event
                if (types.contains(AutoCatchingType.GROUP)) {
                    mutableListOf<AutoCatchingClickData>().apply {
                        if (isRoot) {
                            add(
                                AutoCatchingClickData(
                                    idName, description, tag, AutoCatchingType.GROUP.name
                                )
                            )
                        }
                        addAll(
                            (0 until view.childCount).flatMap {
                                //Get data from all child views
                                getDataView(view.getChildAt(it), false) ?: emptyList()
                            }
                        )
                    }
                } else return null
            }
            else -> return null
        }
    }

    companion object {
        private const val KEY_BUTTON = "Button"
    }
}