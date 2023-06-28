package lt.bongibau.grindstoneunenchanter;

import lt.bongibau.grindstoneunenchanter.listeners.CraftingListeners;
import org.bukkit.plugin.java.JavaPlugin;

public final class GrindstoneUnenchanterPlugin extends JavaPlugin {

    private static GrindstoneUnenchanterPlugin instance;

    @Override
    public void onEnable() {
        instance = this;

        this.getServer().getPluginManager().registerEvents(new CraftingListeners(), this);
    }

    @Override
    public void onDisable() {
        instance = null;
        // Plugin shutdown logic
    }

    public static GrindstoneUnenchanterPlugin getInstance() {
        return instance;
    }
}
