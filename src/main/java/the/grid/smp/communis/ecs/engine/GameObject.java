package the.grid.smp.communis.ecs.engine;

import the.grid.smp.communis.ecs.base.Component;

import java.util.HashMap;
import java.util.Map;

public class GameObject {

    private final Map<Class<? extends Component>, Component> contents = new HashMap<>();

    public void assign(Component component) {
        this.assign(component.getClass(), component);
    }

    public void assign(Class<? extends Component> clazz, Component component) {
        this.contents.put(clazz, component);
    }

    public Component get(Class<? extends Component> clazz) {
        return this.contents.get(clazz);
    }
}
