<div align="center">

# Matule Network

**Networking library for the Matule Android app**

![Kotlin](https://img.shields.io/badge/Kotlin-2.3-7F52FF?style=flat-square&logo=kotlin)
![Retrofit](https://img.shields.io/badge/Retrofit-3.0-48A999?style=flat-square)
![OkHttp](https://img.shields.io/badge/OkHttp-5.3-black?style=flat-square)
![Module](https://img.shields.io/badge/Type-Library%20Module-orange?style=flat-square)

</div>

---

## About

**matule-network** is a standalone Android library module that handles all network communication for the Matule application — HTTP client setup, Retrofit API definitions, Kotlinx Serialization converters, and error handling. Fully covered with unit tests via **MockWebServer**, **Truth**, and **kotlinx-coroutines-test**. Consumed by [matule-app](https://github.com/fadyMarty/matule-app) as a git submodule.

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Kotlin 2.3 |
| HTTP Client | OkHttp 5.3 + Logging Interceptor |
| REST | Retrofit 3 |
| Serialization | Kotlinx Serialization JSON |
| Unit Testing | kotlinx-coroutines-test + MockWebServer3 + Truth |
| Build | Gradle Kotlin DSL + Version Catalogs |

## Repository Structure

```
matule-network/
├── network/  # Library module — API client, services, models, interceptors
└── app/      # Demo app for isolated API testing
```

The `app` module serves as a sandbox for testing network calls independently of the main application.

## Testing

The network layer is unit-tested using **MockWebServer3** to simulate real server responses locally, **Truth** for fluent assertions, and **kotlinx-coroutines-test** for testing suspend functions and Flow emissions.

```bash
./gradlew :network:test
```

## Usage

Consumed via git submodule from the main application:

```bash
git submodule add https://github.com/fadyMarty/matule-network matule_network
```

Include in `settings.gradle.kts`:

```kotlin
include(":matule_network:network")
project(":matule_network:network").projectDir = file("matule_network/network")
```

Add as a dependency:

```kotlin
dependencies {
    implementation(project(":matule_network:network"))
}
```

## Development

To work on the network layer in isolation:

```bash
git clone https://github.com/fadyMarty/matule-network.git
```

## Related Repositories

- 📱 [matule-app](https://github.com/fadyMarty/matule-app) — Main application
- 🎨 [matule-ui-kit](https://github.com/fadyMarty/matule-ui-kit) — UI component library
