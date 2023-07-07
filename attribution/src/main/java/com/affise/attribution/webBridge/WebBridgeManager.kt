package com.affise.attribution.webBridge

import android.annotation.SuppressLint
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebView
import com.affise.attribution.converter.StringToKeyValueConverter
import com.affise.attribution.events.StoreEventUseCase
import com.affise.attribution.modules.AffiseModuleManager
import com.affise.attribution.modules.AffiseModules
import org.json.JSONArray
import org.json.JSONObject

@SuppressLint("JavascriptInterface")
internal class WebBridgeManager(
    private val storeEventUseCase: StoreEventUseCase,
    private val moduleManager: AffiseModuleManager
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

    @JavascriptInterface
    fun getStatus(module: String?, id: String?) {
        val affiseModules = module?.let {
            AffiseModules.valueOf(it)
        } ?: return

        moduleManager.status(affiseModules) { data ->
            val json = data.map { JSONObject().apply {
                put(StringToKeyValueConverter.KEY, it.key)
                put(StringToKeyValueConverter.VALUE, it.value)
            } }.apply {
                JSONArray(this)
            }
            post("getStatus", id, json.toString())
        }
    }

    @JavascriptInterface
    fun log(level: String?, message: String?) {
        when(level) {
            "d" -> Log.d(this.javaClass.simpleName, message ?: "no debug details")
            "e" -> Log.e(this.javaClass.simpleName, message ?: "no error details")
            "w" -> Log.w(this.javaClass.simpleName, message ?: "no warning details")
            else -> Log.i(this.javaClass.simpleName, message ?: "no info details")
        }
    }

    private fun post(name: String, id: String?, data: String) {
        webView?.post {
            run {
                webView?.evaluateJavascript("AffiseEventHandler.dispatchEvent('$name','$id','$data');", null)
            }
        }
    }

    companion object {
        const val WEB_BRIDGE_JAVASCRIPT_INTERFACE_NAME = "AffiseBridge"
    }
}