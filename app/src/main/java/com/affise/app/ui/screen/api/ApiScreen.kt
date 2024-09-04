package com.affise.app.ui.screen.api

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.affise.app.R
import com.affise.app.ui.affiseSettings
import com.affise.app.ui.components.AffiseButton
import com.affise.app.ui.components.AffiseTextField
import com.affise.app.ui.theme.AffiseAttributionLibTheme
import com.affise.app.ui.utils.toNormalCase
import com.affise.attribution.Affise
import com.affise.attribution.modules.AffiseModules
import com.affise.attribution.parameters.ProviderType
import com.affise.attribution.referrer.ReferrerKey

private fun demoApi(): List<Pair<String, () -> Unit>> {
    return listOf(
        Pair("Debug: validate") {
            output("validate: requesting...")
            Affise.Debug.validate {
                output("validate: $it")
            }
        },
//        Pair("Debug: network") {
//            Affise.Debug.network { req, res ->
//
//            }
//        },
        Pair("addPushToken") {
            Affise.addPushToken("new_token")
        },
        Pair("setOfflineModeEnabled") {
            Affise.setOfflineModeEnabled(!Affise.isOfflineModeEnabled())
            affiseSettings.isOfflineModeEnabled.value = Affise.isOfflineModeEnabled()
            output("isOfflineModeEnabled: ${affiseSettings.isOfflineModeEnabled.value}")
        },
        Pair("isOfflineModeEnabled") {
            val value = Affise.isOfflineModeEnabled()
            output("isOfflineModeEnabled: $value")
        },
        Pair("setBackgroundTrackingEnabled") {
            Affise.setBackgroundTrackingEnabled(!Affise.isBackgroundTrackingEnabled())
            affiseSettings.isBackgroundTrackingEnabled.value = Affise.isBackgroundTrackingEnabled()
            output("isBackgroundTrackingEnabled: ${affiseSettings.isBackgroundTrackingEnabled.value}")
        },
        Pair("isBackgroundTrackingEnabled") {
            val value = Affise.isBackgroundTrackingEnabled()
            output("isBackgroundTrackingEnabled: $value")
        },
        Pair("setTrackingEnabled") {
            Affise.setTrackingEnabled(!Affise.isTrackingEnabled())
            affiseSettings.isBackgroundTrackingEnabled.value = Affise.isTrackingEnabled()
            output("isTrackingEnabled: ${affiseSettings.isBackgroundTrackingEnabled.value}")
        },
        Pair("isTrackingEnabled") {
            val value = Affise.isTrackingEnabled()
            output("isTrackingEnabled: $value")
        },
        Pair("getReferrer") {
            Affise.getReferrerUrl {
                output("getReferrer: $it")
            }
        },
        Pair("getReferrerValue") {
            Affise.getReferrerUrlValue(ReferrerKey.AD_ID) {
                output("getReferrerValue: $it")
            }
        },
        Pair("getStatus") {
            output("getStatus: requesting...")
            Affise.Module.getStatus(AffiseModules.Status) { keyValue ->
                output(
                    "getStatus: ${
                        keyValue.joinToString("\n") { "key = ${it.key}; value = ${it.value}" }
                    }"
                )
            }
        },
        Pair("getModulesInstalled") {
            val value = Affise.Module.getModulesInstalled()
            output("getModulesInstalled: [ ${value.joinToString(", ")}]")
        },
        Pair("getRandomUserId") {
            val value = Affise.getRandomUserId()
            output("getRandomUserId: $value")
        },
        Pair("getRandomDeviceId") {
            val value = Affise.getRandomDeviceId()
            output("getRandomDeviceId: $value")
        },
        Pair("getProviders") {
            val providerType = ProviderType.GAID_ADID
            val value = Affise.getProviders()
            output("getProviders: ${providerType.provider} = ${value[providerType] as? String}")
        },
        Pair("isFirstRun") {
            val value = Affise.isFirstRun()
            output("isFirstRun: $value")
        },
    ).map { Pair(it.first.toNormalCase().uppercase(), it.second) }
}

private val output = mutableStateOf("")

private val apis: List<Pair<String, () -> Unit>> = demoApi()

@Composable
fun ApiScreen(modifier: Modifier = Modifier) {
    Column {
        AffiseTextField(
            output,
            label = stringResource(R.string.api_output),
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 8.dp),
            modifier = modifier.fillMaxWidth()
        ) {
            items(apis) { api ->
                AffiseButton(
                    text = api.first,
                    onClick = api.second
                )
            }
        }
    }
}

fun output(text: String) {
    if (output.value.isNotEmpty()) {
        output.value += "\n"
    }
    output.value += text
}


@Preview(showBackground = true, name = "Api Screen Preview")
@Composable
fun ApiScreenPreview() {
    AffiseAttributionLibTheme {
        ApiScreen()
    }
}