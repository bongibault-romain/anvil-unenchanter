package lt.bongibau.anvilunenchanter;

import lt.bongibau.anvilunenchanter.listeners.CraftingListeners;
import org.bukkit.plugin.java.JavaPlugin;

public final class AnvilUnEnchanterPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(new CraftingListeners(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}
