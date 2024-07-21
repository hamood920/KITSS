package hamood.skypvp;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

public class InventoryClickListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getClickedInventory() != null && event.getClickedInventory().getType() != null) {
            if (event.getClickedInventory().getType() != InventoryType.PLAYER) {
                if (event.getCurrentItem() != null && event.getCurrentItem().getType() == Material.BARRIER) {
                    event.setCancelled(true);
                }
            }
        }
    }
}
