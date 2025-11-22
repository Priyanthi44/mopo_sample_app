# Architecture Overview

**Goal:** Demonstrate an offline-first Android architecture suitable for low-connectivity environments.

## Layers
1. **UI (Activity/Fragment + ViewModel)**  
   - Exposes LiveData/StateFlow to the UI. Shows sync states and local-first content.

2. **Domain / Repository**  
   - Single source of truth: Room DB + network sync.  
   - Provides suspend functions for reads/writes and manages conflict resolution.

3. **Data (Room + Retrofit)**  
   - Room for local persistence.  
   - Retrofit + OkHttp for network calls with short timeouts and retries.

4. **Sync (WorkManager)**  
   - Periodic and one-off sync jobs with constraints (network type, battery)
   - Backoff and retry policies for reliability.

## Key patterns
- Write-to-local-DB-first (optimistic UI) then enqueue sync job.
- Use timestamps/version for conflict resolution.
- UI displays local vs synced state for transparency.
- Keep network payloads minimal (deltas) to reduce bandwidth.
