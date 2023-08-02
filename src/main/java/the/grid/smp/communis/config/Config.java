package the.grid.smp.communis.config;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import the.grid.smp.communis.reflection.OwnedField;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

public abstract class Config {

    private final File file;
    private final FileConfiguration config;

    private final Set<ConfigField> fields = new HashSet<>();

    public Config(Plugin plugin, String name) {
        this(new File(plugin.getDataFolder(), name.endsWith(".yml")
                || name.endsWith(".yaml") ? name : name + ".yml")
        );
    }

    public Config(File file) {
        this.file = file;
        this.config = new YamlConfiguration();
    }

    public void reload() {
        if (this.fields.isEmpty()) {
            for (Field field : this.getClass().getDeclaredFields()) {
                Config.Entry entry = field.getAnnotation(Config.Entry.class);

                if (entry != null) {
                    this.fields.add(new ConfigField(
                            new OwnedField(this, field), entry
                    ));
                }
            }
        }

        if (!this.file.exists()) {
            this.save();
        }

        try {
            this.config.load(file);

            for (ConfigField field : this.fields) {
                field.read(this.config);
            }
        } catch (IOException | InvalidConfigurationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            for (ConfigField field : this.fields) {
                field.write(this.config);
            }

            this.config.save(this.file);
        } catch (IOException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Entry {
        String value();
    }
}
