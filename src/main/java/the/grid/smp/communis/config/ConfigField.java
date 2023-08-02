package the.grid.smp.communis.config;

import org.bukkit.configuration.file.FileConfiguration;
import the.grid.smp.communis.reflection.OwnedField;

import java.util.Map;

public record ConfigField(OwnedField field, String name) {

    public ConfigField(OwnedField field, String name) {
        this.field = field;
        this.name = name;

        this.field.setAccessible(true);
    }

    public ConfigField(OwnedField field, Config.Entry entry) {
        this(field, entry.value());
    }

    public void read(FileConfiguration config) throws IllegalAccessException {
        if (this.field.getType() == Map.class) {
            this.field.set(config.getConfigurationSection(this.name).getValues(false));
            return;
        }

        this.field.set(config.get(this.name, null));
    }

    public void write(FileConfiguration config) throws IllegalAccessException {
        config.set(this.name, this.field.get());
    }
}
