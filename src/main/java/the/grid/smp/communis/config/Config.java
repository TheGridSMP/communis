package the.grid.smp.communis.config;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public abstract class Config {

    private final File file;
    private FileConfiguration config;

    public Config(Plugin plugin, String name) {
        this(new File(plugin.getDataFolder(), name.endsWith(".yml")
                || name.endsWith(".yaml") ? name : name + ".yml")
        );
    }

    public Config(File file) {
        this.file = file;
        this.reload();
    }

    public abstract void read(ConfigurationSection section);
    public abstract void write(ConfigurationSection section);

    public void reload() {
        try {
            if (!this.file.exists())
                this.save();

            this.config = new YamlConfiguration();
            this.config.load(this.file);

            this.read(this.config);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            this.file.getParentFile().mkdirs();
            this.write(this.config);

            this.config.save(this.file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
