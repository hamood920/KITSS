package hamood.skypvp;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerDeathListener implements Listener {
    private final Skypvp plugin;

    public PlayerDeathListener(Skypvp plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        clearInventory(player);
        event.getDrops().removeIf(item -> item.getItemMeta() != null && item.getItemMeta().hasLore() && item.getItemMeta().getLore().contains("&cLOCKED"));
    }

    private void clearInventory(Player player) {
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
    }
}
