package the.grid.smp.communis.config;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public abstract class Config {

    private final Plugin plugin;
    private final File file;
    private FileConfiguration config;

    public Config(Plugin plugin, String name) {
        if (!name.endsWith(".yml") && !name.endsWith(".yaml"))
            name += ".yml";

        this.plugin = plugin;
        this.file = new File(plugin.getDataFolder(), name);
        this.reload();
    }

    public abstract void read(ConfigurationSection section);
    public abstract void write(ConfigurationSection section);

    public void reload() {
        if (!this.file.exists())
            this.save();

        this.config = YamlConfiguration.loadConfiguration(this.file);
        this.read(this.config);
    }

    public void save() {
        try {
            this.file.getParentFile().mkdirs();
            this.plugin.saveResource(this.file.getName(), false);

            if (this.config == null)
                this.reload();

            this.write(this.config);
            this.config.save(this.file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
