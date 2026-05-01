<div align="center">
  <img src="https://cdn-icons-png.flaticon.com/512/650/650661.png" width="128" height="128" alt="Notsi Logo"/>
  <h1>Notsi</h1>
  <p>A reactive, modern Notes Android application built with SQLDelight and Clean Architecture principles.</p>

  [![CI/CD](https://github.com/muazdev26/Notsi/actions/workflows/deploy_to_firebase_app_distribution.yml/badge.svg)](https://github.com/muazdev26/Notsi/actions)
  [![Kotlin](https://img.shields.io/badge/kotlin-2.3.21-blue.svg?logo=kotlin)](http://kotlinlang.org)
  [![Platform](https://img.shields.io/badge/platform-Android-green.svg?logo=android)](https://www.android.com)
  [![AGP](https://img.shields.io/badge/AGP-9.2.0-blue.svg?logo=android)](https://developer.android.com/studio/releases/gradle-plugin)
</div>

---

## 📱 Screenshots

<div align="center">
  <img src="https://github.com/muazdev26/muazdev26.github.io/blob/main/notes_android_app_github.jpeg" width="300" /><br> 
</div>

---

## 🛠 Tech Stack & Architecture

Notsi follows **Modern Android Development (MAD)** practices and uses **MVVM** with **Clean Architecture**.

- **Language:** [Kotlin](https://kotlinlang.org/)
- **Local Database:** [SQLDelight](https://github.com/cashapp/sqldelight) - Type-safe SQL for Kotlin.
- **Dependency Injection:** [Hilt](https://developer.android.com/training/dependency-injection/hilt-android)
- **Asynchronous Programming:** [Coroutines](https://developer.android.com/kotlin/coroutines) & [Flow](https://developer.android.com/kotlin/flow)
- **Jetpack Libraries:**
  - [Navigation Components](https://developer.android.com/guide/navigation)
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
  - [ViewBinding](https://developer.android.com/topic/libraries/view-binding)
  - [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle)

---

## 🧪 Testing Strategy

The project is heavily tested across all layers to ensure stability and correctness:

### 1. Unit Tests (`app/src/test`)
- **ViewModel Tests:** Validates UI logic and state using `InstantTaskExecutorRule`.
- **Data Source Tests:** In-memory integration tests for SQLDelight queries using Robolectric.
- **Mappers:** Ensures data conversion between layers is accurate.
- **Libraries:** 
  - [JUnit4](https://junit.org/junit4/) - The fundamental testing framework.
  - [Truth](https://truth.dev/) - Fluent assertions for Kotlin/Java.
  - [Turbine](https://github.com/cashapp/turbine) - A small testing library for Kotlin Flow.
  - [Robolectric](https://robolectric.org/) - Industry-standard unit testing framework for Android.
  - [Mockito](https://site.mockito.org/) - Mocking framework for unit tests.

### 2. Instrumented Tests (`app/src/androidTest`)
- **Fragment Isolation Tests:** Testing UI components in isolation using a custom Hilt container.
- **End-to-End (E2E) Tests:** Full user flow verification from adding a note to seeing it in the list.
- **Libraries:** 
  - [Espresso](https://developer.android.com/training/testing/espresso) - Native UI testing framework.
  - [Hilt Testing](https://developer.android.com/training/dependency-injection/hilt-testing) - For dependency injection in instrumented tests.
  - [Mockito-Android](https://github.com/mockito/mockito) - Mockito support for Android instrumented tests.

---

## 🚀 CI/CD Pipeline

The project uses **GitHub Actions** for automation:

- **Continuous Integration:** Every pull request/manual trigger triggers a full test suite run (`./gradlew testDebugUnitTest`).
- **Continuous Deployment:** On successful build and test completion, the debug APK is automatically distributed to **Firebase App Distribution** for testers.
