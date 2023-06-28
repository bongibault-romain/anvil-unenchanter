package lt.bongibau.grindstoneunenchanter.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;

public class CraftingListeners implements Listener {

    @EventHandler
    public void onPrepare(PrepareItemCraftEvent e) {
        System.out.println("cc");
    }

}
