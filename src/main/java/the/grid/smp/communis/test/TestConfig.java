package the.grid.smp.communis.test;

import org.bukkit.plugin.Plugin;
import the.grid.smp.communis.config.Config;

public class TestConfig extends Config {

    @Entry(value = "test")
    private final String test = "hi!";

    public TestConfig(Plugin plugin) {
        super(plugin, "test");

        this.reload();
    }
}
