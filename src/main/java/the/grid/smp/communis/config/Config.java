package the.grid.smp.communis.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bukkit.plugin.Plugin;
import the.grid.smp.communis.util.Util;

import java.io.File;
import java.io.IOException;

public abstract class Config {

    private final ObjectMapper mapper;
    private final File file;

    public Config(Plugin plugin, String name) {
        this(new File(plugin.getDataFolder(), name.endsWith(".yml")
                || name.endsWith(".yaml") ? name : name + ".yml")
        );
    }

    public Config(File file) {
        this.file = file;
        this.mapper = Util.createMapper(this.serializers(), this.deserializers());
    }

    public Serializer<?>[] serializers() { return null; }
    public Deserializer<?>[] deserializers() { return null; }

    public void reload() {
        if (!this.file.exists()) {
            this.save();
        }

        try {
            this.mapper.readValue(this.file, this.getClass());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            this.mapper.writeValue(this.file, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
