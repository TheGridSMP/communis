package ecs.event;

import the.grid.smp.communis.ecs.base.Entity;
import the.grid.smp.communis.ecs.event.EntityEvent;

public class StartEvent extends EntityEvent<StartEvent.StartEventData> {

    public StartEvent(StartEventData data) {
        super(data);
    }

    public record StartEventData(Entity entity, boolean success, String welcome) implements EntityEvent.Data { }
}
