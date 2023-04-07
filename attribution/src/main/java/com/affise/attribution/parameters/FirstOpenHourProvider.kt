package com.affise.attribution.parameters

import com.affise.attribution.parameters.base.LongPropertyProvider
import com.affise.attribution.usecase.FirstAppOpenUseCase
import java.util.Calendar
import java.util.Date

/**
 * Provider for parameter [Parameters.FIRST_OPEN_HOUR]
 *
 * @property useCase to retrieve first open time from
 */
internal class FirstOpenHourProvider(
    private val useCase: FirstAppOpenUseCase
) : LongPropertyProvider() {

    override val order: Float = 9.0f
    override val key: String = Parameters.FIRST_OPEN_HOUR

    override fun provide(): Long? = useCase.getFirstOpenDate()
        ?.time
        ?.takeIf { it != 0L }
        ?.let {
            Calendar.getInstance().apply {
                //Set date of first open
                time = Date(it)

                //Remove millisecond
                set(Calendar.MILLISECOND, 0)

                //Remove second
                set(Calendar.SECOND, 0)

                //Remove minute
                set(Calendar.MINUTE, 0)
            }
        }
        ?.timeInMillis
}