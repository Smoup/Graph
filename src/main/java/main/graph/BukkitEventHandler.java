package main.graph;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;
import java.util.Map;


public class BukkitEventHandler implements Listener {


    private final Map<String, Boolean> interactFlag = new HashMap<>();

    @EventHandler
    public void selectPanelBlocks(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();
        int cmdEnabled = Graph.getCmdGraph().get(player.getName());
        assert block != null;
        if (interactFlag.getOrDefault(player.getName(), false)) {
            event.setCancelled(true);
            return;
        }
        interactFlag.put(player.getName(), true);
        if (cmdEnabled == 1) {
            Graph.getCmdGraph().put(player.getName(), 2);
            Graph.setPanel1(block.getLocation());
            player.sendMessage(ChatColor.GRAY +
                    "Первый блок панели графика установлен, для выбора второго используйте " +
                    ChatColor.YELLOW + "ПКМ");
        } else if (cmdEnabled == 2) {
            if (block.getLocation().getY() == Graph.getPanel1().getY()) {
                Graph.getCmdGraph().put(player.getName(), 0);
                Graph.setPanel2(block.getLocation());
                player.sendMessage(ChatColor.GRAY + "Успешно, блоки для панели графика установлены.");
            } else {
                player.sendMessage(ChatColor.GRAY + "Блоки должны быть на одной высоте.");
            }
        } else if (Graph.getCmdGraphSetMaterial().get(player.getName())) {
            Graph.setGraphMaterial(event.getMaterial());
            player.sendMessage(ChatColor.GRAY + "Вы установили материал графика на " +
                    ChatColor.YELLOW + event.getMaterial());
        }
        Bukkit.getScheduler().runTaskLater(Graph.getInstance(), () -> interactFlag.put(player.getName(), false), 1);
    }
}
