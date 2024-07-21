package hamood.skypvp;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class FirstJoinListener implements Listener {
    private final Skypvp plugin;

    public FirstJoinListener(Skypvp plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onFirstJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!player.hasPlayedBefore()) {
            giveHighestPermissionKit(player);
        }
    }

    private void giveHighestPermissionKit(Player player) {
        Kit highestKit = null;
        for (Kit kit : Kit.values()) {
            if (player.hasPermission(kit.getPermission())) {
                highestKit = kit;
                break;
            }
        }
        if (highestKit != null) {
            this.plugin.setLastSelectedKit(player, highestKit);
            highestKit.giveKit(player);
        }
    }
}
