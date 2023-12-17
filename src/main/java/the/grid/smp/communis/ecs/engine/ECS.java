package the.grid.smp.communis.ecs.engine;

import the.grid.smp.communis.ecs.base.Component;
import the.grid.smp.communis.ecs.base.Entity;
import the.grid.smp.communis.ecs.base.EntitySystem;
import the.grid.smp.communis.ecs.event.EntityEvent;

import java.util.function.Function;

public class ECS {

    private static final InternalEntityManager manager = InternalEntityManager.get();

    public static void process(Function<Entity, EntityEvent> event) {
        manager.process(event);
    }

    public static <T extends Component> T getComponent(Entity entity, Class<T> clazz) {
        return manager.getComponent(entity, clazz);
    }

    public static <T extends Component> T getComponent(EntityEvent event, Class<T> clazz) {
        return ECS.getComponent(event.entity(), clazz);
    }

    public static <T extends Component> boolean hasComponent(Entity entity, Class<T> clazz) {
        return manager.hasComponent(entity, clazz);
    }

    public static <T extends Component> boolean hasComponent(EntityEvent event, Class<T> clazz) {
        return manager.hasComponent(event.entity(), clazz);
    }

    public static <T extends Component> void addComponent(Entity entity, T t) {
        manager.addComponent(entity, t);
    }

    public static Entity create() {
        return manager.create();
    }

    public static void register(EntitySystem system) {
        manager.register(system);
    }
}
