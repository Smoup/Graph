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
        System.out.println("top");
        List<Block> setBlocks = Graph.getGraphBlocks();
        World world = Graph.getPanel1().getBlock().getWorld();
        Random random = new Random();

        Location firstP = Graph.getPanel1();
        Location secondP = Graph.getPanel2();

        int y = firstP.getBlock().getY();
        int minX = firstP.getBlock().getX();
        int minZ = firstP.getBlock().getZ();
        int maxX = secondP.getBlock().getX();
        int maxZ = secondP.getBlock().getZ();
        int sizeZ = Math.abs(maxZ - minZ);

        int maxStep = Graph.getStepMaxHigh();

        Material graphMaterial = Graph.getGraphMaterial();
        Material air = Material.AIR;

        if (setBlocks.isEmpty()) {
            Block block = world.getBlockAt(new Location(world, minX + 1, y, random.nextInt(sizeZ)));
            block.setType(graphMaterial);
            Graph.getGraphBlocks().add(block);

        } else if (setBlocks.size() <= sizeZ - Graph.getDistToBorder()) {
            int step = random.nextInt(maxStep + 1);
            while (isInGraph(minX, maxX, setBlocks.get(setBlocks.size() - 1).getX(), step)) {
                step = random.nextInt(maxStep + 1);
            }
            if (random.nextBoolean()) {
                step = step * -1;
            }

            if (step == 0) {
                Block block = world.getBlockAt(new Location(world, minX + 1, y, step));
                block.setType(graphMaterial);
                Graph.getGraphBlocks().add(block);
            } else {
                int lastZ = setBlocks.get(setBlocks.size() - 1).getZ();
                if (step < 0) {
                    for (int i = 0; i < Math.abs(step); i++) {
                        Block block = world.getBlockAt(new Location(world, minX + setBlocks.size() + 1, y, lastZ - i));
                        block.setType(graphMaterial);
                    }
                    Block block = world.getBlockAt(new Location(world, minX + setBlocks.size() + 1, y, lastZ - step));
                    block.setType(graphMaterial);
                    Graph.getGraphBlocks().add(block);
                } else {
                    for (int i = 0; i < Math.abs(step); i++) {
                        Block block = world.getBlockAt(new Location(world, minX + setBlocks.size() + 1, y, lastZ + i));
                        block.setType(graphMaterial);
                    }
                    Block block = world.getBlockAt(new Location(world, minX + setBlocks.size() + 1, y, lastZ + step));
                    block.setType(graphMaterial);
                    Graph.getGraphBlocks().add(block);
                }
            }
            System.out.println("Set 2");
        } else {
            Block block = setBlocks.get(0);
            block.setType(air);
            Graph.getGraphBlocks().remove(0);
            int previousZ = block.getZ();
            for (int i = 0; i < Graph.getGraphBlocks().size(); i++) {
                Block block1 = Graph.getGraphBlocks().get(i);
                if (block1.getZ() == previousZ) {
                    Block block2 = world.getBlockAt(new Location(world, block1.getX() - 1, y, previousZ));
                    block2.setType(graphMaterial);
                    block1.setType(air);
                } else if (previousZ != block1.getZ()) {
                    int difference = previousZ - block1.getZ();
                    if (difference < 0) {
                        for (int i1 = 0; i < Math.abs(difference); i++) {
                            Block block2 = world.getBlockAt(new Location(world, minX + setBlocks.size() + 1, y, previousZ - i1));
                            block2.setType(graphMaterial);
                        }
                    } else {
                        for (int i1 = 0; i < Math.abs(difference); i++) {
                            Block block2 = world.getBlockAt(new Location(world, minX + setBlocks.size() + 1, y, previousZ + i1));
                            block2.setType(graphMaterial);
                        }
                    }
                    previousZ = block1.getZ();
                }
            }
            System.out.println("Set 3");
        }
    }

    private static boolean isInGraph(int minX, int maxX, int previousX, int step) {
        return minX > previousX + step && previousX + step > maxX;
    }
}
