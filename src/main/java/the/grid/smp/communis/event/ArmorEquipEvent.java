package the.grid.smp.communis.event;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import the.grid.smp.communis.util.Util;

public class ArmorEquipEvent extends PlayerEvent {
	
	private static final HandlerList handlers = new HandlerList();

	private final EquipmentSlot slot;
	private final ItemStack item;

	public ArmorEquipEvent(Player player, EquipmentSlot slot, ItemStack item) {
		super(player);

		this.slot = slot;
		this.item = item;
	}

	public ArmorEquipEvent(Player player, EquipmentSlot slot) {
		this(player, slot, player.getEquipment().getItem(slot));
	}

	public ArmorEquipEvent(Player player, ItemStack item) {
		this(player, Util.slotFor(item), item);
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public EquipmentSlot getSlot() {
		return slot;
	}

	public ItemStack getItem() {
		return item;
	}
}