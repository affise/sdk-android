package com.affise.attribution.webBridge

import android.annotation.SuppressLint
import android.webkit.JavascriptInterface
import android.webkit.WebView
import com.affise.attribution.events.StoreEventUseCase

@SuppressLint("JavascriptInterface")
internal class WebBridgeManager(
    private val storeEventUseCase: StoreEventUseCase
) {

    /**
     * WebView
     */
    private var webView: WebView? = null

    /**
     * Register [webView] to WebBridgeManager
     */
    fun registerWebView(webView: WebView) {
        //Save webView
        this.webView = webView

        //Add JavascriptInterface to webView
        webView.addJavascriptInterface(this, WEB_BRIDGE_JAVASCRIPT_INTERFACE_NAME)
    }

    /**
     * Unregister [webView] on WebBridgeManager
     */
    fun unregisterWebView() {
        //Remove JavascriptInterface on webView
        webView?.removeJavascriptInterface(WEB_BRIDGE_JAVASCRIPT_INTERFACE_NAME)

        //Remove webView
        webView = null
    }

    /**
     * Send [event] from webView
     */
    @JavascriptInterface
    fun sendEvent(event: String?) {
        event?.let {
            //Store event
            storeEventUseCase.storeWebEvent(it)
        }
    }

    companion object {
        const val WEB_BRIDGE_JAVASCRIPT_INTERFACE_NAME = "AffiseBridge"
    }
}