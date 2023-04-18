package main.graph;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;

import java.util.List;
import java.util.Random;

public class PaintGraph {

    public static void start() {

    }
    public static void setNext() {
        List<Block> setBlocks = Graph.getGraphBlocks();
        Random random = new Random();
        World world = Graph.getPanel1().getBlock().getWorld();

        Location firstP = Graph.getPanel1();
        Location secondP = Graph.getPanel2();
        double maxX = Math.abs(secondP.getX() - firstP.getX());
        double maxZ = Math.abs(secondP.getZ() - firstP.getZ());

        Material graphMaterial = Graph.getGraphMaterial();

        Block block = world.getBlockAt(firstP);
        block.setType(graphMaterial);
        if (setBlocks.isEmpty()) {

        }
    }
}
