<div align="center">

# Matule Network

**Networking library for the Matule Android app**

![Kotlin](https://img.shields.io/badge/Kotlin-2.x-7F52FF?style=flat-square&logo=kotlin)
![Android](https://img.shields.io/badge/Android-API%2024+-3DDC84?style=flat-square&logo=android)
![Module](https://img.shields.io/badge/Type-Library%20Module-orange?style=flat-square)

</div>

---

## About

**matule-network** is a standalone Android library module that handles all network communication for the Matule application — HTTP client setup, API definitions, request/response models, and error handling. It is consumed by [matule-app](https://github.com/fadyMarty/matule-app) as a git submodule.

## Repository Structure

```
matule-network/
├── network/  # Library module — API client, models, interceptors
└── app/      # Demo app for isolated testing
```

The `app` module exists as a sandbox for testing API calls and network behaviour independently of the main application.

## Usage

This library is intended to be consumed via git submodule:

```bash
# From your root project
git submodule add https://github.com/fadyMarty/matule-network matule_network
```

Then include the module in your `settings.gradle.kts`:

```kotlin
include(":matule_network:network")
project(":matule_network:network").projectDir = file("matule_network/network")
```

And add the dependency in your `app/build.gradle.kts`:

```kotlin
dependencies {
    implementation(project(":matule_network:network"))
}
```

## Development

To work on the network layer in isolation, open this repository directly in Android Studio and run the `app` module.

```bash
git clone https://github.com/fadyMarty/matule-network.git
```

## ✦ Related Repositories

- 📱 [matule-app](https://github.com/fadyMarty/matule-app) — Main application
- 🎨 [matule-ui-kit](https://github.com/fadyMarty/matule-ui-kit) — UI component library
