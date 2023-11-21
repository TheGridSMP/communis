package the.grid.smp.communis.ecs.util;

import the.grid.smp.communis.ecs.base.Component;
import the.grid.smp.communis.ecs.base.Entity;
import the.grid.smp.communis.ecs.base.EntitySystem;
import the.grid.smp.communis.ecs.engine.ECS;

public record EntityTemplate<K extends Component, V extends EntitySystem>(K component, V system) {

    public void register(Entity entity) {
        ECS.addComponent(entity, this.component);
        ECS.register(system);
    }

    public Entity register() {
        Entity entity = ECS.create();
        this.register(entity);

        return entity;
    }
}
