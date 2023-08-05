package the.grid.smp.communis;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import the.grid.smp.communis.listener.ArmorEquipListener;

import java.util.logging.Logger;

public final class Communis extends JavaPlugin implements Listener {

    public static Logger LOGGER;

    @Override
    public void onLoad() {
        LOGGER = this.getLogger();
    }

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(new ArmorEquipListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
