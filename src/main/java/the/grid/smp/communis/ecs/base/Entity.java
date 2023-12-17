package the.grid.smp.communis.ecs.base;

import java.util.UUID;

public record Entity(UUID uuid) {

    public Entity() {
        this(UUID.randomUUID());
    }
}
