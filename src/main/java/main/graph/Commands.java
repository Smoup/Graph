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
                switch (args[0]) {
                    case "setGraphPoints":
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
                    case "start":
                        if (!Graph.isGraphStart()) {
                            if (Graph.getPanel1() != null && Graph.getPanel2() != null) {
                                sender.sendMessage(ChatColor.GRAY + "Вы запустили создание графика.");
                                PaintGraph.start();
                                Graph.setGraphStart(true);
                            } else {
                                sender.sendMessage(ChatColor.GRAY + "Перед запуском нужно выбрать точки графика.");
                            }
                        } else {
                            sender.sendMessage(ChatColor.GRAY + "График уже запущен.");
                        }
                        return true;
                    case "stop":
                        if (Graph.isGraphStart()) {
                            if (Graph.getPanel1() != null && Graph.getPanel2() != null) {
                                sender.sendMessage(ChatColor.GRAY + "Вы остановили создание графика.");
                                PaintGraph.stop();
                                Graph.setGraphStart(false);
                            } else {
                                sender.sendMessage(ChatColor.GRAY + "Перед запуском нужно выбрать точки графика.");
                            }
                        } else {
                            sender.sendMessage(ChatColor.GRAY + "График уже остановлен.");
                        }
                        return true;
                    case "setGraphMaterial":
                        Graph.getCmdGraphSetMaterial().put(sender.getName(), true);
                        sender.sendMessage(ChatColor.GRAY + "Используйте " +
                                ChatColor.YELLOW + "ПКМ " +
                                ChatColor.GRAY + "для выбора материала графика.");
                        return true;
                    case "distToBorder":
                        if (args.length > 1) {
                            Graph.setDistToBorder(Integer.parseInt(args[1]));
                            sender.sendMessage(ChatColor.GRAY + "Вы установили дистанцию до гранницы графика " +
                                    ChatColor.YELLOW + Integer.parseInt(args[1]) + ChatColor.GRAY + ".");
                        } else {
                            sender.sendMessage(ChatColor.GRAY +
                                    "Укажите дистанцию до гранницы графика которую вы хотите установить.");
                        }
                        return true;
                    case "stepMaxHigh":
                        if (args.length > 1) {
                            Graph.setStepMaxHigh(Integer.parseInt(args[1]));
                            sender.sendMessage(ChatColor.GRAY + "Вы установили значение максимального шага за раз " +
                                    ChatColor.YELLOW + Integer.parseInt(args[1]) + ChatColor.GRAY + ".");
                        } else {
                            sender.sendMessage(ChatColor.GRAY +
                                    "Укажите дистанцию максимального шага за раз.");
                        }
                        return true;
                    case "updateTime":
                        if (args.length > 1) {
                            Graph.setUpdateTime(Long.parseLong(args[1]));
                            sender.sendMessage(ChatColor.GRAY + "Вы установили значение обновления графика на " +
                                    ChatColor.YELLOW + Long.parseLong(args[1]) + ChatColor.GRAY + " тиков.");
                        } else {
                            sender.sendMessage(ChatColor.GRAY +
                                    "Укажите значение обновления графика в тиках.");
                        }
                        return true;
                }
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        if (command.getName().equals("graph")) {
            if (args.length == 1) {
                return Lists.newArrayList("setGraphPoints", "start", "setGraphMaterial", "distToBorder", "stepMaxHigh", "updateTime", "stop");
            } else if (args.length == 2) {
                return Lists.newArrayList("1");
            }
        }
        return null;
    }
}
