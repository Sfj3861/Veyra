package com.sfj3861.veyra.browser

import android.content.Context
import android.view.View

/**
 * Boundary between Veyra's Android/Compose UI and the real browser engine.
 *
 * The production implementation must be backed by Chromium's Android Content
 * embedder (or a maintained Chromium browser fork). Android WebView is not an
 * acceptable implementation for the project's chosen engine requirement.
 */
interface BrowserEngineProvider {
    fun create(context: Context, tabId: String): BrowserEngine
}

interface BrowserEngine {
    val view: View

    fun loadUrl(url: String)
    fun reload()
    fun stopLoading()
    fun goBack()
    fun goForward()
    fun canGoBack(): Boolean
    fun canGoForward(): Boolean
    fun destroy()

    fun setObserver(observer: Observer?)

    interface Observer {
        fun onNavigationChanged(state: NavigationState)
        fun onPageTitleChanged(title: String)
        fun onProgressChanged(progress: Int)
        fun onLoadError(url: String, errorCode: Int, description: String)
    }
}

data class NavigationState(
    val url: String = "",
    val isLoading: Boolean = false,
    val canGoBack: Boolean = false,
    val canGoForward: Boolean = false,
    val isSecureConnection: Boolean = false
)
