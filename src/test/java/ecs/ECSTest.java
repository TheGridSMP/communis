package ecs;

import ecs.component.ExampleComponent;
import ecs.event.StartEvent;
import ecs.system.ExampleSystem;
import the.grid.smp.communis.ecs.base.Entity;
import the.grid.smp.communis.ecs.engine.ECS;
import the.grid.smp.communis.ecs.util.EntityTemplate;

public class ECSTest {

    public static void main(String[] args) {
        Entity entity1 = new EntityTemplate<>(
                new ExampleComponent(),
                new ExampleSystem()
        ).register();

        Entity entity2 = new EntityTemplate<>(
                new ExampleComponent(),
                new ExampleSystem()
        ).register();

        ECS.process((entity) -> new StartEvent(new StartEvent.StartEventData(entity, true, "HI, HELLO")));
    }
}
