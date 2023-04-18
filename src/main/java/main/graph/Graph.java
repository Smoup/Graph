package main.graph;

import org.bukkit.plugin.java.JavaPlugin;

public final class Graph extends JavaPlugin {

    private static Graph instance;

    @Override
    public void onEnable() {
        instance = this;
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Graph getInstance() {
        return instance;
    }
}
