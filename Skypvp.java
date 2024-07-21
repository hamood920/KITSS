package hamood.skypvp;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.HashMap;
import java.util.Map;

public class Skypvp extends JavaPlugin {
    private final Map<Player, Kit> lastSelectedKit = new HashMap<>();

    @Override
    public void onEnable() {
        this.getCommand("kitselectorgui").setExecutor(new KitSelectorGUICommand(this));
        this.getServer().getPluginManager().registerEvents(new KitSelectorGUICommand(this), this);
        this.getServer().getPluginManager().registerEvents(new PlayerDeathListener(this), this);
        this.getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        this.getServer().getPluginManager().registerEvents(new OnDropListener(), this);
        this.getServer().getPluginManager().registerEvents(new FirstJoinListener(this), this);
    }

    @Override
    public void onDisable() {
        lastSelectedKit.clear();
    }

    public void setLastSelectedKit(Player player, Kit kit) {
        lastSelectedKit.put(player, kit);
    }

    public Kit getLastSelectedKit(Player player) {
        return lastSelectedKit.get(player);
    }
}
