package the.grid.smp.communis.ecs.engine;

import org.bukkit.Bukkit;
import the.grid.smp.communis.ecs.base.Component;
import the.grid.smp.communis.ecs.base.Entity;
import the.grid.smp.communis.ecs.base.EntitySystem;
import the.grid.smp.communis.ecs.event.EntityEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class InternalEntityManager {

    private static final InternalEntityManager instance = new InternalEntityManager();

    private final Map<Entity, GameObject> objects = new HashMap<>();
    private final Map<Class<? extends EntitySystem>, EntitySystem> systems = new HashMap<>();

    protected void process(Function<Entity, EntityEvent> event) {
        for (Entity entity : this.objects.keySet()) {
            Bukkit.getPluginManager().callEvent(event.apply(entity));
        }
    }

    protected Entity create() {
        Entity entity = new Entity();
        this.objects.put(entity, new GameObject());

        return entity;
    }

    @SuppressWarnings("unchecked")
    protected <T extends Component> T getComponent(Entity entity, Class<T> clazz) {
        return (T) this.objects.get(entity).get(clazz);
    }

    protected <T extends Component> boolean hasComponent(Entity entity, Class<T> clazz) {
        return this.objects.get(entity).get(clazz) != null;
    }

    protected <T extends Component> void addComponent(Entity entity, T t) {
        this.objects.get(entity).assign(t);
    }

    protected void register(EntitySystem system) {
        if (this.systems.containsKey(system.getClass()))
            return;

        system.init();
        this.systems.put(system.getClass(), system);
    }

    protected static InternalEntityManager get() {
        return instance;
    }
}
