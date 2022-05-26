package com.affise.attribution.webBridge

import android.webkit.WebView
import com.affise.attribution.events.StoreEventUseCase
import io.mockk.*
import org.junit.Test

class WebBridgeManagerTest {

    private val testEvent = "testEvent"

    private val webView = mockk<WebView>()

    private val storeEventUseCase = mockk<StoreEventUseCase>()

    private val webBridgeManager = WebBridgeManager(storeEventUseCase)

    @Test
    fun registerWebView() {
        every {
            webView.addJavascriptInterface(webBridgeManager, WEB_BRIDGE_INTERFACE)
        } just Runs

        webBridgeManager.registerWebView(webView)

        verifyAll {
            webView.addJavascriptInterface(webBridgeManager, WEB_BRIDGE_INTERFACE)
            storeEventUseCase wasNot Called
        }
    }

    @Test
    fun unregisterWithWebView() {
        every {
            webView.addJavascriptInterface(webBridgeManager, WEB_BRIDGE_INTERFACE)
        } just Runs

        every {
            webView.removeJavascriptInterface(WEB_BRIDGE_INTERFACE)
        } just Runs

        webBridgeManager.registerWebView(webView)
        webBridgeManager.unregisterWebView()

        verifyAll {
            webView.addJavascriptInterface(webBridgeManager, WEB_BRIDGE_INTERFACE)
            webView.removeJavascriptInterface(WEB_BRIDGE_INTERFACE)
            storeEventUseCase wasNot Called
        }
    }

    @Test
    fun unregisterWithOutWebView() {
        webBridgeManager.unregisterWebView()

        verifyAll {
            webView wasNot Called
            storeEventUseCase wasNot Called
        }
    }

    @Test
    fun sendEvent() {
        every {
            storeEventUseCase.storeWebEvent(testEvent)
        } just Runs

        webBridgeManager.sendEvent(testEvent)

        verifyAll {
            storeEventUseCase.storeWebEvent(testEvent)
            webView wasNot Called
        }
    }

    companion object {
        const val WEB_BRIDGE_INTERFACE = "AffiseBridge"
    }

}