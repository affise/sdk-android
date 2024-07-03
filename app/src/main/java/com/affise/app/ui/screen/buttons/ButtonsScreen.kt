package com.affise.app.ui.screen.buttons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.affise.app.ui.components.AffiseButton
import com.affise.app.ui.screen.buttons.factories.DefaultEventsFactory
import com.affise.app.ui.theme.AffiseAttributionLibTheme
import com.affise.app.ui.utils.toNormalCase
import com.affise.attribution.events.Event
import com.affise.attribution.events.subscription.BaseSubscriptionEvent


private val events: List<Event> = DefaultEventsFactory().createEvents()

@Composable
fun ButtonsScreen(
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        items(events) { event ->
            ButtonEvent(event)
        }
    }
}


@Composable
fun ButtonEvent(event: Event, modifier: Modifier = Modifier) {
    val color = when (event) {
        is BaseSubscriptionEvent -> MaterialTheme.colorScheme.error
        else -> MaterialTheme.colorScheme.primary
    }

    val name = when (event) {
        is BaseSubscriptionEvent -> event.subtype.replace("_", " ")
        else -> event.getName().toNormalCase()
    }.uppercase()

    AffiseButton(
        modifier = modifier,
        text = name,
        color = color,
        onClick = {
            // Send event
            event.send()
//            // or Send event now
//            event.sendNow({
//                // handle event send success
//            }) { errorResponse ->
//                // handle event send failed
//                // 🟥Warning:🟥 event is NOT cached for later send
//            }
        }
    )
}

@Preview(showBackground = true, name = "Buttons Screen Preview")
@Composable
fun ButtonsScreenPreview() {
    AffiseAttributionLibTheme {
        ButtonsScreen()
    }
}