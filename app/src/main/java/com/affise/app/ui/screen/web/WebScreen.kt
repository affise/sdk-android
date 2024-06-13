package com.affise.app.ui.screen.web

import android.annotation.SuppressLint
import android.view.View
import android.webkit.WebView
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.affise.app.ui.theme.AffiseAttributionLibTheme
import com.affise.attribution.Affise

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebScreen(modifier: Modifier = Modifier) {

    val assetHtml = "file:///android_asset/index.html"

    AndroidView(
        modifier = modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        factory = {
            WebView(it).apply {
                setLayerType(View.LAYER_TYPE_SOFTWARE, null)

                settings.javaScriptEnabled = true
                settings.loadWithOverviewMode = true

                Affise.registerWebView(this)
            }
        },
        update = {
            it.loadUrl(assetHtml)
        }
    )
}

@Preview(showBackground = true, name = "Web Screen Preview")
@Composable
fun WebScreenPreview() {
    AffiseAttributionLibTheme {
        WebScreen()
    }
}