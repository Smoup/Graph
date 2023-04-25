package main.graph;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.List;
import java.util.Random;

public class PaintGraph {

    static BukkitScheduler scheduler = Bukkit.getServer().getScheduler();

    @Getter @Setter
    private static int taskId;

    public static void start() {
        if (!Graph.isGraphStart()) {
            int taskID = scheduler.scheduleSyncRepeatingTask(Graph.getInstance(), () -> {
                System.out.println("Update graph...");
                setNext();
            }, 20L, Graph.getUpdateTime());
            setTaskId(taskID);
            Graph.setGraphStart(true);
        }
    }

    public static void stop() {
       if (Graph.isGraphStart()) {
           scheduler.cancelTask(getTaskId());
           Graph.setGraphStart(false);
       }
    }
    public static void setNext() {
        List<Block> setBlocks = Graph.getGraphBlocks();
        Random random = new Random();
        World w = Graph.getPanel1().getBlock().getWorld();

        Location firstP = Graph.getPanel1();
        Location secondP = Graph.getPanel2();

        int y = firstP.getBlockY();
        int x1 = firstP.getBlockX();
        int z1 = firstP.getBlockZ();
        int x2 = secondP.getBlockX();
        int z2 = secondP.getBlockZ();

        int sizeX = Math.abs(x1 - x2);
        int sizeZ = Math.abs(z1 - z2);

        Material gM = Graph.getGraphMaterial();

        int maxStep = Graph.getStepMaxHigh();

        if (x1 > x2 && z1 < z2) {
            if (setBlocks.isEmpty()) {
                int rInt = random.nextInt(sizeZ);
                while (!(rInt + z1 <= z2)) {
                    rInt = random.nextInt(sizeZ);
                }
                Location targetLoc = new Location(w, x1, y, z1 + rInt);
                setBlock(targetLoc, gM);
                Graph.getGraphBlocks().add(targetLoc.getBlock());
            } else if (x1 - setBlocks.size() < x2 + Graph.getDistToBorder()) {
                int rInt = random.nextInt(maxStep);
                int lastZ = setBlocks.get(setBlocks.size() - 1).getZ();
                while (!(rInt + lastZ <= z2)) {
                    rInt = random.nextInt(maxStep);
                }
                //TODO
                //Сделать установку на все блоки от прошлого до нынешнего
                Location targetLoc = new Location(w, x1 + setBlocks.size(), y, rInt + lastZ);
                setBlock(targetLoc, gM);
                Graph.getGraphBlocks().add(targetLoc.getBlock());
            } else {
                for (int i = 0; i < setBlocks.size() - 1; i++) {
                    Block block = setBlocks.get(i);
                    if (i == 0) {
                        block.setType(Material.AIR);
                        continue;
                    }
                    Block firstB = setBlocks.get(i - 1);

                    if (i == 1){

                    } else if (i < setBlocks.size() - 2){

                    } else {

                    }
                }
                Graph.getGraphBlocks().remove(0);
            }

        } else if (x1 < x2 && z1 < z2) {

        } else if (x1 < x2 && z1 > z2) {

        } else if (x1 > x2 && z1 > z2) {

        }
    }

    public static void setBlock(Location location, Material material) {
        location.getBlock().setType(material);
    }
}
