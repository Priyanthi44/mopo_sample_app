# MOPO Android Sample App (Offline-First Demo)

**Author:** Priyanthi Dassanayake  
**Purpose:** Small Kotlin Android demo app showcasing offline-first architecture, MVVM, Room, Retrofit, Coroutines, and WorkManager for background sync.  
**Resume:** /mnt/data/Priyanthi D .docx

> This repository is a compact, self-contained sample intended to demonstrate the core patterns I use when building resilient mobile apps for low-connectivity environments.

## Key features
- Offline-first data model using Room (local persistence)
- Repository pattern with conflict-resolution hints
- Background sync using WorkManager with retry/backoff
- Network layer using Retrofit/OkHttp
- Simple, accessible UI with clear sync status indicators
- Minimal, easy-to-review code ideal for quick assessment

## How to use
1. Open Android Studio and import this folder as a project.
2. Add Android SDK and Gradle settings as required (this repo contains template files and Kotlin classes).
3. Replace `BaseApi`'s `BASE_URL` with a test endpoint or use a local mock server.
4. Run on an emulator or device.

## Files of interest
- `app/src/main/java/com/mopo/sample/MainActivity.kt` — simple UI & ViewModel wiring
- `app/src/main/java/com/mopo/sample/di/ServiceLocator.kt` — provides repository, database, api
- `app/src/main/java/com/mopo/sample/data/` — Room entities, DAO, Repository
- `app/src/main/java/com/mopo/sample/sync/SyncWorker.kt` — WorkManager sync job
- `README.md`, `ARCHITECTURE.md`, `TALKING_POINTS.md` — docs to help you present the project

## License
MIT — feel free to use this demo in your application process.
