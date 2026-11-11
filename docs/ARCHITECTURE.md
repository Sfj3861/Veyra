# Veyra — Initial architecture

## Current milestone

The repository starts with an Android application shell using Kotlin and Jetpack Compose. The first screen is a visual prototype; it is not yet connected to Chromium and does not perform web navigation.

## Planned layers

- **Android app:** activity lifecycle, permissions, storage integration and Android system APIs.
- **UI:** Jetpack Compose screens and reusable components.
- **Theme Engine:** user-editable design tokens, layouts, typography, icon packs and motion preferences.
- **Customization system:** import/export of declarative theme packages and a visual layout editor.
- **Browser integration:** Chromium-based content rendering and browser controls. This layer is not implemented yet.
- **Privacy and protection:** configurable content filtering, site permissions and transparent per-site controls.
- **Extension host:** isolated extension APIs with explicit permissions. Native extensions must never receive unrestricted access by default.

## Design principles

1. Customization is a first-class feature, not an afterthought.
2. The default appearance is modern Glassmorphism with balanced density.
3. Themes must not be able to execute arbitrary native code.
4. Core navigation and safety controls must remain recoverable.
5. Performance and accessibility take priority over decorative effects.
6. The app must remain usable when blur, animation or transparency is disabled.

## Important implementation note

A translucent Compose surface is not automatically a true backdrop blur. The initial UI uses translucent fills, gradients and subtle borders. A real backdrop-blur implementation will be evaluated separately because platform support and GPU cost vary by Android version and device.

## Build environment

- Android Studio
- JDK 17
- Android SDK / Build Tools compatible with compileSdk 36
- Gradle wrapper (to be added after validating the project toolchain)

Dependency versions are an initial baseline and should be checked against the installed Android Studio/SDK before the first local build.


## Theme Engine — first implementation

The first working Theme Engine layer now includes:
- Four built-in presets: Glassmorphism, Minimal, Futuristic and Material You.
- A typed theme configuration with HEX colors and glass/border opacity.
- A Compose palette provided through a CompositionLocal, so existing UI components react to theme changes.
- Local persistence through Android SharedPreferences.
- A settings screen reachable from the home screen's Settings item, with live preset switching, editable primary/secondary/background HEX colors, opacity control and reset.

This is an initial editor, not the final visual layout editor. Custom image/video backgrounds, per-component positioning, typography packs, drag-and-drop editing, theme import/export and community publishing remain future milestones. HEX input currently accepts six-digit RGB values in the editor.
