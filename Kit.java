package hamood.skypvp;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum Kit {
    DEFAULT("§9Default", Material.LEATHER_CHESTPLATE, "default.perm", new ItemStack[]{
            createLockedItem(Material.IRON_HELMET, "§9Default Helmet", Enchantment.PROTECTION_ENVIRONMENTAL, 1),
            createLockedItem(Material.GOLD_CHESTPLATE, "§9Default Chestplate", Enchantment.PROTECTION_ENVIRONMENTAL, 1),
            createLockedItem(Material.IRON_LEGGINGS, "§9Default Leggings", null, 0),
            createLockedItem(Material.GOLD_BOOTS, "§9Default Boots", Enchantment.PROTECTION_ENVIRONMENTAL, 1),
            createLockedItem(Material.IRON_SWORD, "§9Default Sword", Enchantment.DURABILITY, 1),
            createLockedItem(Material.BOW, null, null, 0),
            new ItemStack(Material.ARROW, 32)
    }),
    VIP("§6VIP", Material.GOLD_CHESTPLATE, "vip.perm", new ItemStack[]{
            createLockedItem(Material.IRON_HELMET, "§6VIP Helmet", Enchantment.PROTECTION_ENVIRONMENTAL, 1),
            createLockedItem(Material.CHAINMAIL_CHESTPLATE, "§6VIP Chestplate", Enchantment.PROTECTION_ENVIRONMENTAL, 1),
            createLockedItem(Material.IRON_LEGGINGS, "§6VIP Leggings", null, 0),
            createLockedItem(Material.CHAINMAIL_BOOTS, "§6VIP Boots", Enchantment.PROTECTION_ENVIRONMENTAL, 1),
            createLockedItem(Material.IRON_SWORD, "§6VIP Sword", Enchantment.DURABILITY, 1),
            createLockedItem(Material.BOW, null, null, 0),
            new ItemStack(Material.ARROW, 40)
    }),
    EPIC("§2Epic", Material.CHAINMAIL_CHESTPLATE, "epic.perm", new ItemStack[]{
            createLockedItem(Material.IRON_HELMET, "§2Epic Helmet", Enchantment.PROTECTION_ENVIRONMENTAL, 1),
            createLockedItem(Material.CHAINMAIL_CHESTPLATE, "§2Epic Chestplate", Enchantment.PROTECTION_ENVIRONMENTAL, 1),
            createLockedItem(Material.IRON_LEGGINGS, "§2Epic Leggings", Enchantment.PROTECTION_ENVIRONMENTAL, 1),
            createLockedItem(Material.IRON_BOOTS, "§2Epic Boots", Enchantment.PROTECTION_ENVIRONMENTAL, 1),
            createLockedItem(Material.IRON_SWORD, "§2Epic Sword", Enchantment.DURABILITY, 1),
            createLockedItem(Material.BOW, null, null, 0),
            new ItemStack(Material.ARROW, 48)
    }),
    SPIRITS("§3Spirits", Material.DIAMOND_CHESTPLATE, "spkit.perm", new ItemStack[]{
            createLockedItem(Material.DIAMOND_HELMET, "§3Spirits Helmet", Enchantment.PROTECTION_ENVIRONMENTAL, 1),
            createLockedItem(Material.IRON_CHESTPLATE, "§3Spirits Chestplate", Enchantment.PROTECTION_ENVIRONMENTAL, 1),
            createLockedItem(Material.IRON_LEGGINGS, "§3Spirits Leggings", Enchantment.DURABILITY, 1),
            createLockedItem(Material.DIAMOND_BOOTS, "§3Spirits Boots", Enchantment.PROTECTION_ENVIRONMENTAL, 1),
            createLockedItem(Material.IRON_SWORD, "§3Spirits Sword", Enchantment.DURABILITY, 1),
            createLockedItem(Material.BOW, null, null, 0),
            new ItemStack(Material.ARROW, 64)
    }),
    ELITE("§bElite", Material.IRON_CHESTPLATE, "elite.perm", new ItemStack[]{
            createLockedItem(Material.IRON_HELMET, "§bElite Helmet", Enchantment.PROTECTION_ENVIRONMENTAL, 1),
            createLockedItem(Material.IRON_CHESTPLATE, "§bElite Chestplate", Enchantment.PROTECTION_ENVIRONMENTAL, 1),
            createLockedItem(Material.IRON_LEGGINGS, "§bElite Leggings", Enchantment.PROTECTION_ENVIRONMENTAL, 1),
            createLockedItem(Material.IRON_BOOTS, "§bElite Boots", Enchantment.PROTECTION_ENVIRONMENTAL, 1),
            createLockedItem(Material.IRON_SWORD, "§bElite Sword", Enchantment.DURABILITY, 1),
            createLockedItem(Material.BOW, null, null, 0),
            new ItemStack(Material.ARROW, 56)
    });

    private static final Map<String, Kit> BY_DISPLAY_NAME = new HashMap<>();
    private final String displayName;
    private final Material icon;
    private final String permission;
    private final ItemStack[] items;

    private Kit(String displayName, Material icon, String permission, ItemStack[] items) {
        this.displayName = displayName;
        this.icon = icon;
        this.permission = permission;
        this.items = items;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public Material getIcon() {
        return this.icon;
    }

    public String getPermission() {
        return this.permission;
    }

    public ItemStack[] getItems() {
        return this.items;
    }

    public static Kit getByDisplayName(String displayName) {
        return BY_DISPLAY_NAME.get(displayName);
    }

    private static ItemStack createLockedItem(Material material, String name, Enchantment enchantment, int level) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            if (name != null) {
                meta.setDisplayName(name);
            }
            if (enchantment != null && level > 0) {
                meta.addEnchant(enchantment, level, true);
            }
            meta.setLore(Arrays.asList(ChatColor.RED + "LOCKED"));
            item.setItemMeta(meta);
        }
        return item;
    }

    public int getSlot() {
        switch (this.ordinal()) {
            case 0:
                return 9;
            case 1:
                return 11;
            case 2:
                return 13;
            case 3:
                return 17;
            case 4:
                return 15;
            default:
                return -1;
        }
    }

    public void giveKit(Player player) {
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        for (ItemStack item : this.getItems()) {
            switch (item.getType()) {
                case DIAMOND_HELMET:
                case GOLD_HELMET:
                case IRON_HELMET:
                    player.getInventory().setHelmet(item);
                    break;
                case DIAMOND_CHESTPLATE:
                case GOLD_CHESTPLATE:
                case IRON_CHESTPLATE:
                case CHAINMAIL_CHESTPLATE:
                    player.getInventory().setChestplate(item);
                    break;
                case DIAMOND_LEGGINGS:
                case GOLD_LEGGINGS:
                case IRON_LEGGINGS:
                case CHAINMAIL_LEGGINGS:
                    player.getInventory().setLeggings(item);
                    break;
                case DIAMOND_BOOTS:
                case GOLD_BOOTS:
                case IRON_BOOTS:
                case CHAINMAIL_BOOTS:
                    player.getInventory().setBoots(item);
                    break;
                default:
                    player.getInventory().addItem(item);
                    break;
            }
        }
        player.sendMessage(ChatColor.GREEN + "You have received the " + this.getDisplayName() + " kit!");
    }
}
