# Veyra visual system

## Default preset

- Style: modern Glassmorphism
- Density: balanced
- Base palette: deep navy, electric blue and violet
- Surfaces: translucent fills with fine borders
- Typography: high-contrast, readable sans-serif
- Motion: restrained and configurable

This is only the installation default. It is not a restriction on user-created themes.

## User customization goals

Users should eventually be able to customize:

- Theme colors, including RGB and HEX values
- Background images and supported motion backgrounds
- Surface opacity, corner radius and border appearance
- Blur/effect intensity where supported by the device
- Typography and text scaling
- Icon packs and individual toolbar icons
- Button size, visibility and placement
- Navigation bars, menus and shortcut grids
- Animations, transitions and reduced-motion behavior
- Per-profile appearance presets

## Community content

The planned Workshop will support importing and exporting declarative theme packages. Theme packages should contain metadata, assets and a validated layout description. They must not execute native code.

Community packages should declare their author, version, compatible Veyra API version and license. Imported packages should be previewable before activation and removable without losing the user's previous configuration.

## Accessibility and resilience

- Maintain readable contrast.
- Respect Android font scaling.
- Support reduced motion.
- Provide a low-effects mode for slower devices.
- Keep essential controls available even with custom layouts.
- Include a reset-to-default action.
