package lt.bongibau.anvilunenchanter;

import lt.bongibau.anvilunenchanter.listeners.CraftingListeners;
import org.bukkit.plugin.java.JavaPlugin;

public final class AnvilUnenchanterPlugin extends JavaPlugin {

    private static AnvilUnenchanterPlugin instance;

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

    public static AnvilUnenchanterPlugin getInstance() {
        return instance;
    }
}
