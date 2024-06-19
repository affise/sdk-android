package com.affise.app.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.affise.app.App
import com.affise.app.Prefs
import com.affise.app.R
import com.affise.app.ui.components.AffiseDialogsComponent
import com.affise.app.ui.components.AffiseTabPagerComponent
import com.affise.app.ui.components.DialogState
import com.affise.app.ui.screen.api.ApiScreen
import com.affise.app.ui.screen.buttons.ButtonsScreen
import com.affise.app.ui.screen.settings.AffiseSettings
import com.affise.app.ui.screen.settings.SettingsScreen
import com.affise.app.ui.screen.store.StoreScreen
import com.affise.app.ui.screen.web.WebScreen
import com.affise.app.ui.theme.AffiseAttributionLibTheme
import com.affise.attribution.Affise
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebase()
        prefs()
        enableEdgeToEdge()
        setContent {
            AffiseAttributionLibTheme {
                MainView()
            }
        }
        Affise.registerDeeplinkCallback {
            popDialog(
                title = "Deeplink",
                text = "\"${it.deeplink}\"\n\n" +
                        "scheme: \"${it.scheme}\"\n\n" +
                        "host: \"${it.host}\"\n\n" +
                        "path: \"${it.path}\"\n\n" +
                        "parameters: ${it.parameters}"
            )
        }
    }

    private fun firebase() {
        FirebaseMessaging.getInstance().token.apply {
            addOnSuccessListener {
                affiseSettings.pushToken.value = it
            }
            addOnFailureListener {
                affiseSettings.pushToken.value = "Failed to retrieve token: " + it.message
            }
        }
    }

    private fun prefs() {
        affiseSettings.appId.value = Prefs.string(App.AFFISE_APP_ID_KEY, App.DEMO_APP_ID)
        affiseSettings.secretKey.value = Prefs.string(App.SECRET_ID_KEY, App.DEMO_SECRET_KEY)
        affiseSettings.domain.value = Prefs.string(App.DOMAIN_KEY, App.DEMO_DOMAIN)
        affiseSettings.isProduction.value = Prefs.boolean(App.PRODUCTION_KEY)
        affiseSettings.metrics.value = Prefs.boolean(App.ENABLED_METRICS_KEY)
        affiseSettings.debugRequest.value = Prefs.boolean(App.DEBUG_REQUEST_KEY)
        affiseSettings.debugResponse.value = Prefs.boolean(App.DEBUG_RESPONSE_KEY)
    }
}

val dialogs = mutableStateListOf<DialogState>()
val affiseSettings = AffiseSettings()
val settingsScreen = mutableStateOf(false)

@Composable
fun MainView(modifier: Modifier = Modifier) {
    AffiseDialogsComponent(dialogs = dialogs)

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .safeDrawingPadding(),
        floatingActionButton = {
            FloatingActionButton(
                shape = CircleShape,
                containerColor = MaterialTheme.colorScheme.secondary,
                onClick = {
                    settingsScreen.value = !settingsScreen.value
                }
            ) {
                if (settingsScreen.value) {
                    Icon(Icons.Default.Check, contentDescription = "Check")
                } else {
                    Icon(Icons.Default.Settings, contentDescription = "Settings")
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            if (settingsScreen.value) {
                SettingsScreen(modifier)
            } else {
                TabsView(modifier)
            }
        }
    }
}

@Composable
private fun TabsView(modifier: Modifier = Modifier) {
    AffiseTabPagerComponent(
        modifier = modifier,
        titles = listOf(
            stringResource(R.string.api),
            stringResource(R.string.events),
            stringResource(R.string.web_events),
            stringResource(R.string.store),
        ),
        content = listOfNotNull(
            {
                ApiScreen()
            },
            {
                ButtonsScreen()
            },
            {
                WebScreen()
            },
            {
                StoreScreen()
            },
        )
    )
}

@Preview(
    showBackground = true,
    name = "Main Preview",
//    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
fun MainPreview() {
    AffiseAttributionLibTheme {
        MainView()
    }
}

fun popDialog(
    title: String,
    text: String,
    onDismiss: () -> Unit = { },
) {
    dialogs.add(DialogState(title = title, text = text, onDismiss = onDismiss))
}