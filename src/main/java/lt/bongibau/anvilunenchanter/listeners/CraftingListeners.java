package lt.bongibau.anvilunenchanter.listeners;

import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

public class CraftingListeners implements Listener {

    @EventHandler
    public void inventoryClick(PrepareAnvilEvent e) {
        AnvilInventory inventory = e.getInventory();

        ItemStack firstItem = inventory.getItem(0);
        ItemStack secondItem = inventory.getItem(1);

        if (firstItem == null || secondItem == null) return;

        HumanEntity humanEntity = e.getView().getPlayer();

        if (!(humanEntity instanceof Player)) return;

        if (secondItem.getType().equals(Material.BOOK) && !firstItem.getEnchantments().isEmpty()) {
            ItemStack book = getEnchantedBook((Player) humanEntity, firstItem);

            if (book != null) {
                EnchantmentStorageMeta meta = (EnchantmentStorageMeta) book.getItemMeta();

                if (meta == null) return;
                if (meta.getStoredEnchants().isEmpty()) return;

                e.setResult(book);
            }
        }
    }

    @EventHandler
    public void onDisenchantmentClickEvent(InventoryClickEvent e) {
        if (e.getRawSlot() != 2) return;

        Inventory inventory = e.getClickedInventory();
        HumanEntity entity = e.getWhoClicked();

        if (!(entity instanceof Player)) return;
        if (!(inventory instanceof AnvilInventory)) return;

        AnvilInventory anvilInventory = (AnvilInventory) e.getInventory();
        Player player = (Player) entity;

        ItemStack firstItem = inventory.getItem(0);
        ItemStack secondItem = inventory.getItem(1);

        if (firstItem == null) return;
        if (secondItem == null) return;
        if (firstItem.getEnchantments().isEmpty()) return;
        if (!secondItem.getType().equals(Material.BOOK)) return;

        ItemStack result = getEnchantedBook(player, firstItem);

        if (!this.hasValidEnchantments(player, result, secondItem, firstItem)) return;
        if (result == null || result.getItemMeta() == null || ((EnchantmentStorageMeta) result.getItemMeta()).getStoredEnchants().isEmpty())
            return;

        ItemStack item = firstItem.clone();
        item.getEnchantments().forEach((en, l) -> {
            if (player.hasPermission("aue." + en.getKey().getKey().toLowerCase()) || player.hasPermission("aue.*")) {
                item.removeEnchantment(en);
            }
        });

        anvilInventory.setItem(0, item);

        if (secondItem.getAmount() == 1) {
            anvilInventory.setItem(1, null);
        } else {
            secondItem.setAmount(secondItem.getAmount() - 1);
        }

        player.setItemOnCursor(result);
        player.updateInventory();
    }

    private ItemStack getEnchantedBook(Player player, ItemStack enchantedItem) {
        ItemStack result = new ItemStack(Material.ENCHANTED_BOOK, 1);

        EnchantmentStorageMeta meta = (EnchantmentStorageMeta) result.getItemMeta();

        if (meta == null) return null;

        enchantedItem.getEnchantments().forEach((enchantment, integer) -> {
            if (player.hasPermission("aue." + enchantment.getKey().getKey().toLowerCase())
                    || player.hasPermission("aue.*")) {
                meta.addStoredEnchant(enchantment, integer, true);
            }
        });

        result.setItemMeta(meta);

        return result;
    }

    private boolean hasValidEnchantments(Player player, ItemStack result, ItemStack bookItem, ItemStack disenchantedItem) {
        return (!disenchantedItem.getEnchantments().isEmpty())
                && result.getEnchantments().entrySet().stream().allMatch(entry -> disenchantedItem.getEnchantments().containsKey(entry.getKey())
                && disenchantedItem.getEnchantments().get(entry.getKey()).equals(entry.getValue())
                && (player.hasPermission("aue." + entry.getKey().getKey().getKey().toLowerCase()) || player.hasPermission("aue.*")))
                && (bookItem != null && bookItem.getType().equals(Material.BOOK));
    }

}
