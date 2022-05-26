package com.affise.attribution.utils

import android.view.View

/**
 * AutoCatching uses its click listeners to call the callback when the view is clicked.
 *
 * @property oldClickListener original click listeners
 * @property afterClickListenerAction action after calling the original click listeners
 */
internal class AutoCatchingOnClickListener(
    private val oldClickListener: View.OnClickListener?,
    private val afterClickListenerAction: (View) -> Unit
) : View.OnClickListener {

    override fun onClick(view: View?) {
        //Call original clickListener
        oldClickListener?.onClick(view)

        //Get send data from view
        view?.also {
            afterClickListenerAction.invoke(it)
        }
    }
}