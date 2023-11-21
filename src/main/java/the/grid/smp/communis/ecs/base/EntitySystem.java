package the.grid.smp.communis.ecs.base;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import the.grid.smp.communis.Communis;

public abstract class EntitySystem implements Listener {

    public void init() {
        Bukkit.getPluginManager().registerEvents(this, Communis.getInstance());
    }
}
