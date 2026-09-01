# End Vertical — Complete Project Base

Target: Minecraft Java 1.21.11 / Fabric.

Included:
- Void Walker entity: 160 HP, 15 melee damage, 2% hit-block chance.
- Darkness Stone block/item.
- Void Essence and Void Core items.
- Java 21 build setup.
- Fabric 1.21.11 dependency versions.

Important: this is a source project, not a compiled JAR. Run `gradlew.bat build` on Windows after installing Java 21 and allowing Gradle to download dependencies. Fabric documents that the resulting JARs are placed in `build/libs`.

The remaining world-generation, rendering/model assets, loot tables, and the full ranged projectile implementation still require target-version API validation before this can honestly be called production-ready.
