package ecs.system;

import ecs.component.ExampleComponent;
import ecs.event.StartEvent;
import org.bukkit.event.EventHandler;
import the.grid.smp.communis.ecs.base.EntitySystem;
import the.grid.smp.communis.ecs.engine.ECS;

public class ExampleSystem extends EntitySystem {

    @EventHandler
    public void onInternalEvent(StartEvent event) {
        System.out.println("[INFO] " + event.data().welcome());
        System.out.println("RUNNING EXAMPLE!!1 " + ECS.getComponent(event.entity(), ExampleComponent.class));
        System.out.println("I AM " + this + " BTW");
    }
}
