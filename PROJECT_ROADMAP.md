# Project Roadmap — Instance-Based Social Survival System (Fabric)

## Core Vision Summary (for dev team alignment)

A Fabric-based Minecraft server where:

- Every player lives in an isolated survival instance by default.
- Players appear in other instances as ghost projections (non-interactive NPCs).
- Social interaction is opt-in via controlled systems (`/visit`, `/duel`).
- World changes are local unless explicitly shared via merge systems.
- Building is the primary gameplay focus, supported by progression tools (`fly`, builder ranks, wand tools).

## Architecture Overview (Non-Negotiable Design)

Core systems:

- Instance Manager (World Isolation Layer)
- Player State System
- Ghost Projection System (Cross-instance visualization)
- Interaction Gateway (`/visit`, `/duel`)
- Block Overlay System (Ghost Blocks / World Echo)
- Progression System (Fly, Builder, Trusted Builder)

## Phase 0 — Engine Setup & Foundation

**Goal:** Establish stable Fabric server mod architecture + data pipeline.

**Tasks:**

- Set up Fabric server project (Minecraft version locked).
- Create core mod entrypoint.
- Establish module-based architecture (not monolithic code).
- Implement persistent storage system for:
  - Player instance data
  - World seed reference
  - Chunk/state storage format

**Systems introduced:**

- `InstanceManager`
- `PlayerDataManager`
- `PersistenceLayer`

**Deliverables:**

- Server starts reliably.
- Players join/leave with no feature logic yet.
- Player data is saved/restored.

**Acceptance Criteria:**

- No data loss on restart.
- Player state loads consistently.
- System supports at least 50 test players.

## Phase 1 — Instance Isolation System (Core Foundation)

**Goal:** Each player exists in a fully isolated survival world.

**Tasks:**

- Create per-player world instance generator.
- Implement instance loading/unloading lifecycle:
  - on join → load instance
  - on leave → save + unload
- Ensure world seed consistency layer (base template world).
- Block cross-instance world mutation.

**Systems introduced:**

- `InstanceWorldController`
- `InstanceLifecycleManager`

**Deliverables:**

- Each player has independent survival world.
- No shared block state between players.

**Acceptance Criteria:**

- 10 players can exist in separate worlds simultaneously.
- No cross-instance contamination.
- Performance stable under join/leave cycles.

## Phase 2 — Ghost Player Projection System

**Goal:** Make other players visually present without real-world interaction.

**Tasks:**

- Create fake player entity system (NPC projection layer).
- Sync:
  - position
  - rotation
  - basic animation state
- Apply:
  - transparency (0.5 alpha)
  - no collision
  - no interaction hitbox
- Optimize update rate (not per-tick full sync).

**Systems introduced:**

- `GhostEntityManager`
- `CrossInstancePlayerSyncService`

**Deliverables:**

- Players appear in each other’s worlds as ghost NPCs.

**Acceptance Criteria:**

- No physical interaction possible with ghosts.
- Stable rendering under multiple ghost entities.
- No TPS drop with 20+ visible players.

## Phase 3 — Social Layer (Chat + Visibility)

**Goal:** Enable social presence without world merging.

**Tasks:**

- Global or instance-linked chat system.
- Player discovery system (who is “near” in instance network).
- Basic UI/commands:
  - `/who`
  - `/nearby`
- Optional: ghost interaction indicators (emotes, actions).

**Systems introduced:**

- `SocialManager`
- `ChatRouter`

**Deliverables:**

- Players can communicate regardless of instance.
- Awareness of other players exists.

**Acceptance Criteria:**

- Chat latency < 200ms server-side routing.
- No cross-instance data leaks beyond intended channels.

## Phase 4 — Interaction Gateway System (`/duel`, `/visit`)

**Goal:** Controlled temporary world merging.

**Tasks:**

- Implement instance merge session system:
  - create temporary shared world snapshot
  - load both players into same simulation space
- `/duel` system:
  - PvP enabled
  - keep inventory ON/OFF toggle
  - automatic rollback after session ends
- `/visit` system:
  - spectator or ghost-mode join into instance

**Systems introduced:**

- `InstanceMergeEngine`
- `SessionManager`

**Deliverables:**

- Controlled shared gameplay sessions exist.

**Acceptance Criteria:**

- No permanent world mutation from duel sessions (unless explicitly designed).
- Clean separation after session ends.
- No inventory duplication bugs.

## Phase 5 — Ghost Block / World Echo System

**Goal:** Visual history layer of other players’ actions.

**Tasks:**

- Implement block change tracking per instance.
- Create “ghost block overlay layer”:
  - semi-transparent blocks
  - non-solid
  - 3-minute decay timer
- Add item drop ghost rendering.
- Ensure no interaction with ghost elements.

**Systems introduced:**

- `WorldEchoManager`
- `BlockDiffTracker`

**Deliverables:**

- Players see fading traces of other players’ actions.

**Acceptance Criteria:**

- Ghost blocks never affect physics.
- Fade system stable and performance-safe.
- No visual desync corruption.

## Phase 6 — Progression & Build Systems

**Goal:** Enable structured creativity scaling.

**Tasks:**

- Implement `/fly` system (90 min timer).
- Builder rank system.
- Trusted Builder rank:
  - wand-based block manipulation
  - uses real inventory/chest resources
- Region-limited build tools.

**Systems introduced:**

- `ProgressionManager`
- `BuilderToolEngine`

**Deliverables:**

- Controlled creative acceleration tools.

**Acceptance Criteria:**

- No abuse duplication exploits.
- Tool limits enforced server-side.
- Resource consumption tracked correctly.

## Phase 7 — Optimization & Scaling

**Goal:** Ensure system survives real player load.

**Tasks:**

- Instance memory optimization (auto-unload idle worlds).
- Ghost system batching (reduce sync frequency).
- Async persistence for instance saves.
- Performance profiling (TPS stability targets).

**Systems introduced:**

- `PerformanceManager`

**Deliverables:**

- Stable multiplayer environment under load.

**Acceptance Criteria:**

- 20–50 concurrent players stable TPS (target depends on hardware).
- No memory leaks after long uptime.

## Critical Technical Risks (Team Must Understand)

1. Instance system memory growth  
   **Mitigation:** aggressive unloading, chunk caching strategy.
2. Ghost entity lag  
   **Mitigation:** throttled updates (not per tick full sync), batch packet sending.
3. Merge system bugs (`/duel`)  
   **Mitigation:** snapshot-based rollback system, strict session isolation rules.
4. Data desync risk  
   **Mitigation:** single source of truth per instance, event-based state updates only.

## Final System Definition (for team alignment)

This project is:

- A Fabric-based instance-driven survival simulation where players exist in isolated worlds by default, with optional social visibility and controlled interaction layers.

This project is not:

- A traditional SMP.
- A shared world server.
- A PvP economy server.
