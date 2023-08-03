package the.grid.smp.communis.config;

import org.bukkit.configuration.ConfigurationSection;
import the.grid.smp.communis.reflection.OwnedField;

import java.lang.reflect.Constructor;
import java.util.Map;

public class ConfigField {

    public static ConfigField of(OwnedField field, String name) throws ReflectiveOperationException {
        Class<?> type = field.getType();
        if (type.isAssignableFrom(Map.class)) {
            return new Raw(field, name);
        }

        if (type.isAssignableFrom(Serializeable.class)) {
            return new Struct(field, name);
        }

        return new ConfigField(field, name);
    }

    public static ConfigField of(OwnedField field, Serializeable.Entry entry) throws ReflectiveOperationException {
        return of(field, entry.value());
    }

    protected final OwnedField field;
    protected final String name;

    private ConfigField(OwnedField field, String name) {
        this.field = field;
        this.name = name;

        this.field.setAccessible(true);
    }

    public void read(ConfigurationSection data) throws ReflectiveOperationException {
        this.field.set(data.get(this.name, null));
    }

    public void write(ConfigurationSection data) throws ReflectiveOperationException {
        data.set(this.name, this.field.get());
    }

    static class Raw extends ConfigField {

        private Raw(OwnedField field, String name) {
            super(field, name);
        }

        @Override
        public void read(ConfigurationSection data) throws ReflectiveOperationException {
            this.field.set(data.getConfigurationSection(this.name).getValues(false));
        }
    }

    static class Struct extends ConfigField {

        private final Constructor<?> constructor;

        private Struct(OwnedField field, String name) throws ReflectiveOperationException {
            super(field, name);

            this.constructor = field.getType().getConstructor(
                    ConfigurationSection.class
            );
        }

        @Override
        public void read(ConfigurationSection data) throws ReflectiveOperationException {
            this.field.set(
                    this.constructor.newInstance(data.getConfigurationSection(this.name))
            );
        }

        @Override
        public void write(ConfigurationSection data) throws ReflectiveOperationException {
            Serializeable serializeable = (Serializeable) this.field.get();
            serializeable.save();

            data.set(this.name, serializeable.getData());
        }
    }
}
