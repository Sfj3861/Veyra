# Veyra

**Veyra** is an experimental Android browser project focused on deep user customization.

> Current status: early Android UI foundation. The current screen is a visual prototype and is not yet connected to the Chromium browser engine.

## Product direction

- Android-first.
- Chromium-based browser engine planned.
- Modern Glassmorphism as the default appearance, with balanced information density.
- User-created themes and community customization are core goals.
- Customization must remain optional, accessible and performance-conscious.
- The project aims to use free and open-source development tools.

## Repository structure

```text
.
├── app/                  # Android application
├── docs/
│   ├── ARCHITECTURE.md   # Planned technical layers
│   └── VISUAL_SYSTEM.md  # Default appearance and customization goals
├── build.gradle.kts
├── settings.gradle.kts
└── gradle.properties
```

## Current implementation

- Kotlin and Jetpack Compose application entry point.
- Initial dark Glassmorphism home-screen prototype.
- Initial design tokens and Material 3 color scheme.
- Architecture and visual-system documentation.

## Not implemented yet

- Chromium integration and real webpage navigation.
- Tab lifecycle and session restoration.
- Ad/tracker filtering engine.
- Theme editor, theme import/export and community Workshop.
- Extension runtime.
- Download manager and browser settings.

## Build

Open the repository in a compatible Android Studio version, install JDK 17 and Android SDK 36, then sync the Gradle project.

The Gradle wrapper is not included yet. Dependency versions are an initial baseline and may need adjustment to the Android Studio and SDK versions installed on the build machine.

## Project status

This is an early development project. Do not use it as a daily browser or for sensitive browsing until the browser engine, security model and privacy features have been implemented and tested.

## Licensing

The project license has not been selected yet. Chromium and third-party dependencies retain their own licenses; the project will document these before distributing builds.


## Theme Engine milestone

The app now has a first usable theme editor accessible from **Ajustes**:
- Glassmorphism, Minimalista, Futurista and Material You presets.
- Live HEX editing for primary, secondary and background colors.
- Adjustable glass transparency.
- Theme selection and custom values saved locally on the device.

This is an early foundation. Theme export/import, image and video backgrounds, per-component layout editing, typography controls, animation settings and community themes are not implemented yet.

**Build status:** this update has not yet been compiled or tested on a physical Android device. The repository still needs a local Android Studio/Gradle validation pass.
