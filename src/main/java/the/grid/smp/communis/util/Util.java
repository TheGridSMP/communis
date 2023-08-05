package the.grid.smp.communis.util;

import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import the.grid.smp.communis.config.Config;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Util {

    public static EquipmentSlot slotFor(ItemStack stack) {
        String type = stack.getType().toString();

        if (type.endsWith("_chestplate")){
            return EquipmentSlot.CHEST;
        } else if (type.endsWith("_leggings")){
            return EquipmentSlot.LEGS;
        } else if (type.endsWith("_boots")){
            return EquipmentSlot.FEET;
        }

        return EquipmentSlot.HEAD;
    }

    public static <T extends Config> Collection<T> loadAll(Class<T> serializeable, File folder) {
        if (!folder.isDirectory())
            throw new IllegalArgumentException("File must be a folder");

        Set<T> set = new HashSet<>();
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".yaml") || name.endsWith(".yml"));

        if (files == null)
            return set;

        try {
            Constructor<T> constructor = serializeable.getConstructor(
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
