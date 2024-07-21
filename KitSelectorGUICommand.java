package hamood.skypvp;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class KitSelectorGUICommand implements CommandExecutor, Listener {
    private final Skypvp plugin;

    public KitSelectorGUICommand(Skypvp plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        } else {
            Player player = (Player) sender;
            this.openKitSelector(player);
            return true;
        }
    }

    private void openKitSelector(Player player) {
        Inventory inv = Bukkit.createInventory(null, 27, "Kit Selector");
        Kit[] kits = Kit.values();
        for (Kit kit : kits) {
            if (player.hasPermission(kit.getPermission())) {
                ItemStack item = new ItemStack(kit.getIcon());
                ItemMeta meta = item.getItemMeta();
                if (meta != null) {
                    meta.setDisplayName(kit.getDisplayName());
                    meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
                    meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    meta.setLore(Arrays.asList("§e➥ §7Left click to select kit", "§e➥ §7Right click to Preview kit"));
                    item.setItemMeta(meta);
                }
                inv.setItem(kit.getSlot(), item);
            }
        }
        player.openInventory(inv);
        player.playSound(player.getLocation(), Sound.CLICK, 0.3F, 0.5F);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getWhoClicked();
        if (event.getView().getTitle().equals("Kit Selector")) {
            event.setCancelled(true);
            if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) {
                return;
            }

            ItemStack clickedItem = event.getCurrentItem();
            Kit selectedKit = Kit.getByDisplayName(clickedItem.getItemMeta().getDisplayName());
            if (selectedKit == null) {
                return;
            }

            if (event.getClick().isLeftClick()) {
                if (player.hasPermission(selectedKit.getPermission())) {
                    this.plugin.setLastSelectedKit(player, selectedKit);
                    this.giveKitToPlayer(player, selectedKit);
                    player.playSound(player.getLocation(), Sound.LEVEL_UP, 0.4F, 0.5F);
                    this.updateKitLore(player, selectedKit, clickedItem);
                } else {
                    player.sendMessage(ChatColor.RED + "You do not have permission to use this kit.");
                }
            } else if (event.getClick().isRightClick()) {
                this.openKitPreview(player, selectedKit);
            }
        } else if (event.getView().getTitle().equals("Kit Preview")) {
            event.setCancelled(true);
            if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) {
                return;
            }

            if (event.getCurrentItem().getType() == Material.BARRIER) {
                player.performCommand("kitselectorgui");
            }
        }
    }

    private void giveKitToPlayer(Player player, Kit kit) {
        player.getInventory().clear();
        kit.giveKit(player);
    }

    private void openKitPreview(Player player, Kit kit) {
        Inventory previewInv = Bukkit.createInventory(null, 9, "Kit Preview");
        ItemStack[] items = kit.getItems();
        for (int i = 0; i < items.length && i < 8; ++i) {
            previewInv.setItem(i, items[i]);
        }

        ItemStack backItem = new ItemStack(Material.BARRIER);
        ItemMeta backMeta = backItem.getItemMeta();
        if (backMeta != null) {
            backMeta.setDisplayName(ChatColor.RED + "Back");
            backItem.setItemMeta(backMeta);
        }
        previewInv.setItem(8, backItem);
        player.openInventory(previewInv);
        player.playSound(player.getLocation(), Sound.CLICK, 0.5F, 0.9F);
    }

    private void updateKitLore(Player player, Kit kit, ItemStack kitItem) {
        ItemMeta meta = kitItem.getItemMeta();
        if (meta != null) {
            meta.setLore(Arrays.asList("§e➥ §7Left click to select kit", "§e➥ §7Right click to Preview kit", "", "§a§l➝ Selected Kit"));
            kitItem.setItemMeta(meta);
        }
    }
}
