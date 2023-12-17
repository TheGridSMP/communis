package the.grid.smp.communis.ecs.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import the.grid.smp.communis.ecs.base.Entity;

public class EntityEvent<T extends EntityEvent.Data> extends Event {

    private final T data;

    public EntityEvent(T data) {
        this.data = data;
    }

    public T data() {
        return this.data;
    }

    public Entity entity() {
        return this.data.entity();
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return null;
    }

    public interface Data {
        Entity entity();
    }
}
