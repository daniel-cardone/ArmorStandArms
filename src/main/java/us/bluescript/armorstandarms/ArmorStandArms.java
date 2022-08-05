package us.bluescript.armorstandarms;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class ArmorStandArms extends JavaPlugin {

    @Override
    public void onEnable() {
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerInteractAtEntityListener(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
