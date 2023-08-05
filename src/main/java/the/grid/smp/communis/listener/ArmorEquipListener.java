package the.grid.smp.communis.listener;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import the.grid.smp.communis.event.ArmorEquipEvent;

public class ArmorEquipListener implements Listener {

    @EventHandler
    public void onArmorChange(PlayerArmorChangeEvent event) {
        Bukkit.getServer().getPluginManager().callEvent(
                new ArmorEquipEvent(event.getPlayer(), event.getNewItem())
        );
    }
}
