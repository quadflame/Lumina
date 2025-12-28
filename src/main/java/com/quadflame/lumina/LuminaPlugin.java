package com.quadflame.lumina;

import com.quadflame.lumina.listeners.ChunkListener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Primary entry point for the Lumina plugin.
 *
 * <p>This class is responsible for initializing core systems and
 * registering all runtime listeners during the plugin enable phase.</p>
 */
public final class LuminaPlugin extends JavaPlugin {

    /**
     * Called by the server when the plugin is enabled.
     *
     * <p>Registers event listeners and performs any required startup
     * initialization. This method is guaranteed to run on the main
     * server thread.</p>
     */
    @Override
    public void onEnable() {
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new ChunkListener(), this);
    }
}
