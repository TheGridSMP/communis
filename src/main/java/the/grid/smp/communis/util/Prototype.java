package the.grid.smp.communis.util;

import org.bukkit.plugin.Plugin;
import the.grid.smp.communis.config.Config;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Prototype {

    public static <T extends Config> Collection<T> load(Class<T> clazz, Plugin plugin, String folder) {
        File file = new File(plugin.getDataFolder(), folder);
        file.mkdirs();

        return load(clazz, file);
    }

    public static <T extends Config> Collection<T> load(Class<T> clazz, File folder) {
        if (!folder.isDirectory())
            throw new IllegalArgumentException("File must be a folder");

        Set<T> set = new HashSet<>();
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".yaml") || name.endsWith(".yml"));

        if (files == null)
            return set;

        try {
            Constructor<T> constructor = clazz.getConstructor(
                    File.class
            );

            for (File file : files) {
                set.add(constructor.newInstance(file));
            }
        } catch (ReflectiveOperationException e) {
            // never happens
        }

        return set;
    }
}
