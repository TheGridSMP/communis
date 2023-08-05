package the.grid.smp.communis.config;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class Config extends Serializeable {

    private final File file;

    public Config(Plugin plugin, String name) {
        this(new File(plugin.getDataFolder(), name.endsWith(".yml")
                || name.endsWith(".yaml") ? name : name + ".yml")
        );
    }

    public Config(File file) {
        super(new YamlConfiguration());
        this.file = file;
    }

    @Override
    public void reload() {
        if (!this.file.exists()) {
            this.save();
        }

        try {
            ((YamlConfiguration) this.data).load(this.file);
            super.reload();
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save() {
        try {
            super.save();
            ((YamlConfiguration) this.data).save(this.file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
