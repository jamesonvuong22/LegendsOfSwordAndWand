# Legends of Sword and Wand (Starter – Deliverable 1)

## Import (easy)
### IntelliJ IDEA
1. File → Open → select this folder (`java_starter`)
2. It will auto-detect Maven. Wait for dependencies to download.
3. Run any `*Main` class under `lowsw` packages.

### Eclipse
1. File → Import → Maven → Existing Maven Projects
2. Select this folder (`java_starter`)
3. Right-click project → Run As → Java Application (choose a `*Main` class)

## Run (CLI)
- `mvn test`
- `mvn -q exec:java` (optional if you add exec plugin)
- Or run the main classes from your IDE.

## Database
1. Create a MySQL schema (e.g., `lowsw`)
2. Run `schema.sql` (in the root pack)
3. Edit `src/main/resources/db.properties`

## Modules (each has a runnable main as required)
- `ProfileModuleMain`
- `BattleModuleMain`
- `CampaignModuleMain`
- `DatabaseSmokeTestMain`

