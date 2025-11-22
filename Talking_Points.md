# Talking Points for Interview

- **Offline-first approach:** Local writes to Room; SyncWorker enqueues uploads with retry/backoff.
- **Resilience:** WorkManager constraints + exponential backoff to handle unreliable networks.
- **User feedback:** UI shows 'Saved locally' vs 'Synced' badges; avoids user confusion.
- **Conflict resolution:** Use timestamps and server-side merge strategies; present easy user reconciliation when needed.
- **Performance:** Avoid large allocations, lazy-load lists (Paging), compress images, and minimize APK size.
- **Testing:** Unit tests for Repository, instrumentation for DB behaviour, and end-to-end tests for sync flows.
- **Migration-friendly:** Room migrations and versioned sync contracts.
