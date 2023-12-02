package the.grid.smp.communis;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class Communis extends JavaPlugin implements Listener {

    public static Logger LOGGER;
    private static Communis instance;

    @Override
    public void onLoad() {
        LOGGER = this.getLogger();
        instance = this;
    }

    public static Communis getInstance() {
        return instance;
    }
}
