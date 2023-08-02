package the.grid.smp.communis;

import org.bukkit.plugin.java.JavaPlugin;
import the.grid.smp.communis.test.TestConfig;

public final class Communis extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        new TestConfig(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
