# Legends of Sword and Wand - Deliverable 2 Update

This project is a Deliverable 2 implementation update for **Legends of Sword and Wand**.

## What is included
- All use-case flows implemented in service/controller form
- 6 design patterns integrated into the codebase
- 12 JUnit tests matching the Deliverable 2 test table
- Simple runnable module mains for auth, battle, campaign, inn, PvP, and DB smoke test

## Patterns used
1. Factory - hero creation
2. Strategy - damage calculation
3. State - battle phase handling
4. Observer - battle state notifications
5. Decorator - temporary hero buffs
6. Singleton - DB connection provider

## Import into Eclipse
1. File -> Import -> Maven -> Existing Maven Projects
2. Select this folder
3. Finish
4. Run tests or any `*Main` class

## Main classes
- `lowsw.ProfileModuleMain`
- `lowsw.BattleModuleMain`
- `lowsw.CampaignModuleMain`
- `lowsw.InnModuleMain`
- `lowsw.PvPModuleMain`
- `lowsw.DatabaseSmokeTestMain`
