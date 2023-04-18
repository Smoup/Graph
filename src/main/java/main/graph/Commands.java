package main.graph;

import com.google.common.collect.Lists;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.List;

public class Commands implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (command.getName().equals("graph")) {
            if (args != null) {
                if (args[0].equals("setGraphPoints")) {
                    if (Graph.getCmdGraph().get(sender.getName()) == null || Graph.getCmdGraph().get(sender.getName()).equals(0)) {
                        Graph.getCmdGraph().put(sender.getName(), 1);
                        sender.sendMessage(ChatColor.GRAY +
                                "Выбор блоков панели активирован, выберите блоки используя " +
                                ChatColor.YELLOW + "ПКМ");
                    } else {
                        sender.sendMessage(ChatColor.GRAY +
                                "Выбор блоков панели для графика уже активирован, выберите блоки используя "
                                + ChatColor.YELLOW + "ПКМ");
                    }
                    return true;
                } else if (args[0].equals("start")) {
                    PaintGraph.setNext();
//                    if (Graph.getPanel1() != null && Graph.getPanel2() != null) {
//                        sender.sendMessage(ChatColor.GRAY + "Вы запустили создание графика.");
//                        PaintGraph.start();
//                    } else {
//                        sender.sendMessage(ChatColor.GRAY + "Перед запуском нужно выбрать точки графика.");
//                    }
                    return true;
                } else if (args[0].equals("setGraphMaterial")) {
                    Graph.getCmdGraphSetMaterial().put(sender.getName(), true);
                    sender.sendMessage(ChatColor.GRAY + "Используйте " +
                            ChatColor.YELLOW + "ПКМ " +
                            ChatColor.GRAY + "для выбора материала графика.");
                }
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        if (command.getName().equals("graph")) {
            if (args.length == 1) {
                return Lists.newArrayList("setGraphPoints", "start", "setGraphMaterial");
            }
        }
        return null;
    }
}
