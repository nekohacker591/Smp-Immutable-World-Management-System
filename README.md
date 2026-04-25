# SMP Immutable World Management System

Initial Fabric foundation for the Instance-Based Social Survival System.

## Implemented (Phase 0 bootstrap)

- Modular package layout (`config`, `instance`, `player`, `persistence`)
- Fabric mod entrypoint and join/leave lifecycle hooks
- `InstanceManager` for per-player instance identity bootstrap
- `PlayerDataManager` for player lifecycle orchestration
- JSON-based `PersistenceLayer` for player data save/load

## Data location

Player state is written to Fabric config path:

`config/iwms/players/<uuid>.json`
