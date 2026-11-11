package com.sfj3861.veyra.browser

import android.net.Uri
import java.util.Locale

/**
 * Converts address-bar input into an HTTP(S) destination or a search URL.
 * Only web schemes are accepted here; external intents are handled separately.
 */
object UrlInputResolver {
    private const val DEFAULT_SEARCH_ENDPOINT = "https://duckduckgo.com/?q="

    sealed interface Result {
        data class Destination(val url: String) : Result
        data class Invalid(val reason: String) : Result
    }

    fun resolve(input: String, searchEndpoint: String = DEFAULT_SEARCH_ENDPOINT): Result {
        val value = input.trim()
        if (value.isEmpty()) return Result.Invalid("Escribe una dirección o una búsqueda.")

        val lower = value.lowercase(Locale.ROOT)
        if (lower.startsWith("javascript:") ||
            lower.startsWith("file:") ||
            lower.startsWith("content:") ||
            lower.startsWith("intent:")
        ) {
            return Result.Invalid("Ese tipo de dirección no está permitido.")
        }

        val hasWebScheme = lower.startsWith("https://") || lower.startsWith("http://")
        if (hasWebScheme) {
            val uri = runCatching { Uri.parse(value) }.getOrNull()
            val scheme = uri?.scheme?.lowercase(Locale.ROOT)
            if (uri == null || uri.host.isNullOrBlank() || scheme !in setOf("http", "https")) {
                return Result.Invalid("La dirección web no parece válida.")
            }
            return Result.Destination(uri.toString())
        }

        val looksLikeHost = value == "localhost" ||
            value.startsWith("localhost:") ||
            value.matches(Regex("^(\\d{1,3}\\.){3}\\d{1,3}(:\\d+)?(/.*)?$")) ||
            (value.contains('.') && !value.contains(' '))

        if (looksLikeHost) {
            val candidate = "https://$value"
            val uri = runCatching { Uri.parse(candidate) }.getOrNull()
            if (uri?.host.isNullOrBlank()) {
                return Result.Invalid("La dirección web no parece válida.")
            }
            return Result.Destination(candidate)
        }

        return Result.Destination(searchEndpoint + Uri.encode(value))
    }
}
