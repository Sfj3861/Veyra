package com.sfj3861.veyra.browser

import android.content.Context
import java.util.UUID

/**
 * Owns browser-engine instances and maps their events to UI-friendly tab state.
 *
 * All public methods and engine observer callbacks must be invoked on the main
 * thread. The native Chromium provider is injected so this class never silently
 * falls back to Android WebView.
 */
class BrowserSession(
    context: Context,
    private val engineProvider: BrowserEngineProvider,
    private val onTabsChanged: (tabs: List<BrowserTab>, selectedTabId: String?) -> Unit,
    private val searchEndpoint: String = "https://duckduckgo.com/?q="
) {
    private data class Entry(
        var tab: BrowserTab,
        val engine: BrowserEngine
    )

    private val appContext = context.applicationContext
    private val entries = linkedMapOf<String, Entry>()
    private var selectedId: String? = null

    fun tabs(): List<BrowserTab> = entries.values.map { it.tab.withSelection(it.tab.id == selectedId) }

    fun selectedTabId(): String? = selectedId

    fun openTab(initialInput: String? = null): String {
        val id = UUID.randomUUID().toString()
        val engine = engineProvider.create(appContext, id)
        val entry = Entry(BrowserTab(id = id), engine)
        entries[id] = entry
        selectedId = id

        engine.setObserver(object : BrowserEngine.Observer {
            override fun onNavigationChanged(state: NavigationState) {
                update(id) {
                    it.copy(
                        url = state.url,
                        isLoading = state.isLoading,
                        canGoBack = state.canGoBack,
                        canGoForward = state.canGoForward,
                        progress = if (state.isLoading) it.progress else 100
                    )
                }
            }

            override fun onPageTitleChanged(title: String) {
                update(id) { it.copy(title = title.ifBlank { "Nueva pestaña" }) }
            }

            override fun onProgressChanged(progress: Int) {
                update(id) { it.copy(progress = progress.coerceIn(0, 100)) }
            }

            override fun onLoadError(url: String, errorCode: Int, description: String) {
                update(id) { it.copy(url = url, isLoading = false, progress = 100) }
            }
        })

        publish()
        if (!initialInput.isNullOrBlank()) navigate(id, initialInput)
        return id
    }

    fun selectTab(tabId: String): Boolean {
        if (tabId !in entries) return false
        selectedId = tabId
        publish()
        return true
    }

    fun closeTab(tabId: String): Boolean {
        val removed = entries.remove(tabId) ?: return false
        removed.engine.setObserver(null)
        removed.engine.destroy()

        if (selectedId == tabId) selectedId = entries.keys.lastOrNull()
        publish()
        return true
    }

    fun closeAll() {
        entries.values.forEach {
            it.engine.setObserver(null)
            it.engine.destroy()
        }
        entries.clear()
        selectedId = null
        publish()
    }

    fun navigate(tabId: String, input: String): UrlInputResolver.Result {
        val entry = entries[tabId]
            ?: return UrlInputResolver.Result.Invalid("La pestaña ya no existe.")
        val result = UrlInputResolver.resolve(input, searchEndpoint)
        if (result is UrlInputResolver.Result.Destination) {
            entry.tab = entry.tab.copy(isLoading = true, progress = 0)
            publish()
            entry.engine.loadUrl(result.url)
        }
        return result
    }

    fun reload(tabId: String = selectedId.orEmpty()) {
        entries[tabId]?.engine?.reload()
    }

    fun stopLoading(tabId: String = selectedId.orEmpty()) {
        entries[tabId]?.engine?.stopLoading()
    }

    fun goBack(tabId: String = selectedId.orEmpty()) {
        entries[tabId]?.engine?.takeIf { it.canGoBack() }?.goBack()
    }

    fun goForward(tabId: String = selectedId.orEmpty()) {
        entries[tabId]?.engine?.takeIf { it.canGoForward() }?.goForward()
    }

    fun selectedEngine(): BrowserEngine? = selectedId?.let { entries[it]?.engine }

    private fun update(tabId: String, transform: (BrowserTab) -> BrowserTab) {
        val entry = entries[tabId] ?: return
        entry.tab = transform(entry.tab)
        publish()
    }

    private fun publish() {
        onTabsChanged(tabs(), selectedId)
    }

    private fun BrowserTab.withSelection(selected: Boolean) = copy(isSelected = selected)
}
