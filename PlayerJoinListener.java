package hamood.skypvp;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    private final Skypvp plugin;

    public PlayerJoinListener(Skypvp plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Kit lastKit = this.plugin.getLastSelectedKit(player);
        if (lastKit != null) {
            lastKit.giveKit(player);
        } else {
            giveHighestPermissionKit(player);
        }
    }

    private void giveHighestPermissionKit(Player player) {
        Kit highestKit = null;
        for (Kit kit : Kit.values()) {
            if (player.hasPermission(kit.getPermission())) {
                highestKit = kit;
            }
        }
        if (highestKit != null) {
            this.plugin.setLastSelectedKit(player, highestKit);
            highestKit.giveKit(player);
        }
    }
}
