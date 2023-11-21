package the.grid.smp.communis.config;

import de.tr7zw.changeme.nbtapi.NBT;
import de.tr7zw.changeme.nbtapi.iface.ReadWriteNBT;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import the.grid.smp.communis.Communis;
import the.grid.smp.communis.reflection.OwnedField;

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.logging.Level;

public class ConfigField {

    public static ConfigField of(OwnedField field, String name) throws ReflectiveOperationException {
        Class<?> type = field.getType();
        if (type.isAssignableFrom(Map.class)) {
            return new Raw(field, name);
        }

        if (type.isAssignableFrom(Serializeable.class)) {
            return new Struct(field, name);
        }

        if (type.isAssignableFrom(ItemStack.class)) {
            return new Item(field, name);
        }

        if (type.isAssignableFrom(ReadWriteNBT.class)) {
            return new Nbt(field, name);
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
            ConfigurationSection section = data.getConfigurationSection(this.name);

            if (section == null) {
                Communis.LOGGER.log(Level.SEVERE, "Can't read " + this.name + " in " + data);
                return;
            }

            this.field.set(section.getValues(false));
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
            ConfigurationSection section = data.getConfigurationSection(this.name);

            if (section == null) {
                Communis.LOGGER.log(Level.SEVERE, "Can't read " + this.name + " in " + data);
                return;
            }

            this.field.set(this.constructor.newInstance(section));
        }

        @Override
        public void write(ConfigurationSection data) throws ReflectiveOperationException {
            Serializeable serializeable = (Serializeable) this.field.get();
            serializeable.save();

            data.set(this.name, serializeable.getData());
        }
    }

    static class Item extends ConfigField {

        private Item(OwnedField field, String name) {
            super(field, name);
        }

        @Override
        public void read(ConfigurationSection data) throws ReflectiveOperationException {
            String raw = data.getString(this.name);

            if (raw == null) {
                Communis.LOGGER.log(Level.SEVERE, "Can't read " + this.name + " in " + data);
                return;
            }

            this.field.set(NBT.itemStackFromNBT(NBT.parseNBT(raw)));
        }

        @Override
        public void write(ConfigurationSection data) throws ReflectiveOperationException {
            data.set(this.name, NBT.itemStackToNBT((ItemStack) this.field.get()));
        }
    }

    static class Nbt extends ConfigField {

        private Nbt(OwnedField field, String name) {
            super(field, name);
        }

        @Override
        public void read(ConfigurationSection data) throws ReflectiveOperationException {
            String raw = data.getString(this.name);

            if (raw == null) {
                Communis.LOGGER.log(Level.SEVERE, "Can't read " + this.name + " in " + data);
                return;
            }

            this.field.set(NBT.parseNBT(raw));
        }

        @Override
        public void write(ConfigurationSection data) throws ReflectiveOperationException {
            data.set(this.name, this.field.get().toString());
        }
    }
}
