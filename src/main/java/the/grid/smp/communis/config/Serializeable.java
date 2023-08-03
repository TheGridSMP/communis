package the.grid.smp.communis.config;

import org.bukkit.configuration.ConfigurationSection;
import the.grid.smp.communis.reflection.OwnedField;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

public abstract class Serializeable {

    private final Set<ConfigField> fields = new HashSet<>();
    protected final ConfigurationSection data;

    public Serializeable(ConfigurationSection data) {
        this.data = data;
    }

    public void reload() {
        try {
            for (Field field : this.getClass().getDeclaredFields()) {
                Entry entry = field.getAnnotation(Entry.class);

                if (entry != null) {
                    this.fields.add(ConfigField.of(
                            new OwnedField(this, field), entry
                    ));
                }
            }

            for (ConfigField field : this.fields) {
                field.read(this.data);
            }
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            for (ConfigField field : this.fields) {
                field.write(this.data);
            }
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
    }

    public ConfigurationSection getData() {
        return data;
    }

    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Entry {
        String value();
    }
}
