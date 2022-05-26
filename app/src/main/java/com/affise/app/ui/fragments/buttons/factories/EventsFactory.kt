package com.affise.app.ui.fragments.buttons.factories

import com.affise.attribution.events.Event

interface EventsFactory {
    fun createEvents(): List<Event>
}