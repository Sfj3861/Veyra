# Chromium integration decision

## Decision

Veyra will not silently use Android WebView as its browser engine. The project requirement is a browser powered by Chromium itself, with control over the browser embedder and its lifecycle.

The preferred production route is to maintain a Veyra Chromium fork and adapt Chromium's Android browser embedder. The Android app in this repository is currently a Kotlin/Compose prototype; it cannot become a full Chromium embedder merely by adding a Maven dependency.

## Initial Chromium version target

For the first real integration, target the latest security-patched **Stable Android Chromium/Chrome 157.0.8103.50** release published by Google on 9 November 2026. Pin the exact upstream Chromium source revision corresponding to that release before beginning the native port; do not use a moving branch head.

Google also published Chrome 158 as an Early Stable rollout on 5 November 2026, but the 157.0.8103.50 Android Stable update is the more conservative baseline for this integration because it is the later Stable-channel security update. Do not interpret a higher major version in an early rollout or Dev channel as automatically preferable for the initial production engine.

Version policy:
- Initial baseline: Android Stable 157.0.8103.50.
- Keep the Chromium source revision and upstream commit SHA recorded together.
- Before each release, check the current Android Stable channel and move to a newer security-patched Stable revision where practical.
- Do not ship Dev, Canary, or an unpatched older revision as the production engine.
- Record the exact Chromium revision, Android API range, supported ABIs and build configuration in the release metadata.

Google's release record: https://chromereleases.googleblog.com/2026/ (Android Stable update, 9 November 2026).

## Why this is a substantial integration

Chromium's Android build documentation describes a Linux x86-64 build environment, at least 8 GB RAM (more than 16 GB recommended), and at least 100 GB free disk space. It uses depot_tools, GN and Ninja. The documented Android build targets include `chrome_public_apk` and `content_shell_apk`.

For Veyra, the full browser target is the relevant starting point. Content Shell is a test/development shell, not a complete daily-browser product. A full Chrome APK is a standalone Chromium browser application; it is not a small embeddable AAR that can simply be dropped into this Compose project.

## Integration shape in this repository

The `com.sfj3861.veyra.browser` package defines the application-facing engine contract:
- `BrowserEngineProvider` creates an engine instance per tab.
- `BrowserEngine` exposes a native Android View and navigation lifecycle operations.
- `NavigationState` reports URL, loading, history and secure-connection state.
- `BrowserTab` is the UI/session model.
- `UrlInputResolver` converts address-bar input to HTTP(S) navigation or a search URL and rejects dangerous non-web schemes.

These are contracts and supporting models only. There is deliberately no fake engine implementation. A real provider must be supplied by the Chromium fork/native integration.

## Required Chromium-side work

1. Pin the Chromium 157.0.8103.50 source revision and record its upstream commit.
2. Create a Veyra embedder target using Chromium's supported Android build infrastructure.
3. Implement the engine provider and native view lifecycle, including JNI boundaries.
4. Connect navigation, tab creation/destruction, history, permissions, downloads, cookies and renderer process events.
5. Keep Chromium security boundaries and sandboxing intact; never load arbitrary native libraries from theme or extension packages.
6. Add browser integration tests and verify the exact Android API/device matrix.
7. Track upstream security updates and rebase regularly.

## Build and device constraints

The official Chromium Android build guide currently calls for Linux x86-64, at least 8 GB RAM (more than 16 GB recommended), and at least 100 GB free disk space. Building the full Chromium source tree on an Android tablet with 8 GB RAM is not a realistic local workflow. A separate Linux x86-64 build machine is needed for this route.

The source checkout and build tools are free/open source, but compute, storage, power and ongoing security maintenance are real costs. A free CI service should not be assumed capable of building Chromium.

## Explicit non-goals

- Do not claim Android WebView is a custom Chromium embedder.
- Do not ship Content Shell as if it were a complete browser.
- Do not claim the engine is integrated until a real Chromium-backed provider is present, builds, renders pages and passes tests.
- Do not bundle a stale prebuilt Chromium binary without a security-update and licensing plan.
