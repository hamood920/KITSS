package hamood.skypvp;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KitCommand implements CommandExecutor {
    private final Skypvp plugin;

    public KitCommand(Skypvp plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        Player player = (Player) sender;
        if (args.length != 1) {
            player.sendMessage(ChatColor.RED + "Usage: /kit <kit_name>");
            return true;
        }

        Kit kit = Kit.getByDisplayName(args[0]);
        if (kit == null) {
            player.sendMessage(ChatColor.RED + "Kit not found.");
            return true;
        }

        if (!player.hasPermission(kit.getPermission())) {
            player.sendMessage(ChatColor.RED + "You do not have permission to use this kit.");
            return true;
        }

        kit.giveKit(player);
        this.plugin.setLastSelectedKit(player, kit);

        return true;
    }
}
