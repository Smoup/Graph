package main.graph;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class Graph extends JavaPlugin {

    @Getter
    private static Graph instance;

    @Override
    public void onEnable() {
        instance = this;

        Objects.requireNonNull(getCommand("graph")).setExecutor(new Commands());
        Bukkit.getPluginManager().registerEvents(new BukkitEventHandler(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Getter @Setter
    private static Map<String, Integer> cmdGraph = new HashMap<>();
    @Getter @Setter
    private static Map<String, Boolean> cmdGraphSetMaterial = new HashMap<>();

    @Getter @Setter
    private static Location panel1;
    @Getter @Setter
    private static Location panel2;
    @Getter @Setter
    private static List<Block> graphBlocks = new ArrayList<>();
    @Getter @Setter
    private static Material graphMaterial;
    @Getter @Setter
    private static int stepMaxHigh;
    @Getter @Setter
    private static int distToBorder;
    @Getter @Setter
    private static long updateTime;
    @Getter @Setter
    private static boolean isGraphStart;

}
