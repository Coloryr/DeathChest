package DeathChest.Color_yr;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.*;

public class CommandEX implements CommandExecutor, TabExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase(DeathChest.command)) {
            if (args.length == 0) {
                sender.sendMessage("§d[DeathChest]§c错误，请使用/chest help 获取帮助");
                return true;
            }
            if (args[0].equalsIgnoreCase("reload")) {
                if (sender.hasPermission("DeathChest.admin")) {
                    DeathChest.setConfig();
                    sender.sendMessage("§d[SkillUse]§e重载成功");
                } else {
                    sender.sendMessage("§d[SkillUse]§c你没有权限");
                }
                return true;
            }
            if (args[0].equalsIgnoreCase("help")) {
                sender.sendMessage("§d[DeathChest]§e帮助手册");
                sender.sendMessage("§d[DeathChest]§e使用/DeathChest set 来设置存储箱子（需要你看着箱子）");
                sender.sendMessage("§d[DeathChest]§e使用/DeathChest mode [mode] 来设置死亡掉落保护模式");
                sender.sendMessage("§d[DeathChest]§e模式0，不开启死亡掉落保护");
                sender.sendMessage("§d[DeathChest]§e模式1，只保存到死亡箱子");
                sender.sendMessage("§d[DeathChest]§e模式2，只保存到设置的箱子");
                sender.sendMessage("§d[DeathChest]§e模式3，死亡不掉落");
                sender.sendMessage("§d[DeathChest]§e模式4，优先级：不掉落->设置的箱子->死亡箱子");
                sender.sendMessage("§d[DeathChest]§e模式5，优先级：不掉落->设置的箱子");
                sender.sendMessage("§d[DeathChest]§e模式6，优先级：不掉落->死亡箱子");
                sender.sendMessage("§d[DeathChest]§e模式7，优先级：设置的箱子->死亡箱子");
                sender.sendMessage("§d[DeathChest]§e死亡箱子花费：" + DeathChest.Config.getCost().getSaveInLocal());
                sender.sendMessage("§d[DeathChest]§e设置的箱子花费：" + DeathChest.Config.getCost().getSaveInChest());
                sender.sendMessage("§d[DeathChest]§e不掉落花费：" + DeathChest.Config.getCost().getNoDrop());
                if (sender.hasPermission("DeathChest.admin")) {
                    sender.sendMessage("§d[DeathChest]§e使用/DeathChest reload 来重读插件配置文件");
                }
                return true;
            } else if (args[0].equalsIgnoreCase("set")) {
                Player player = (Player) sender;
                Block block = player.getTargetBlockExact(5);
                if (block == null) {
                    sender.sendMessage("§d[DeathChest]§c你必须看向一个箱子");
                    return true;
                }
                if (!block.getType().equals(Material.CHEST) && !block.getType().equals(Material.TRAPPED_CHEST)) {
                    sender.sendMessage("§d[DeathChest]§c你看向的方块必须是箱子");
                    return true;
                }
                PlaySet set = DeathChest.Config.getPlayerSet(sender.getName());
                set.setX(block.getX());
                set.setY(block.getY());
                set.setZ(block.getZ());
                DeathChest.Config.setPlayerSet(sender.getName(), set);
                sender.sendMessage("§d[DeathChest]§e已设置位置x:" + block.getX() + " y:" + block.getY() + " z:" + block.getZ() + "的箱子");
                return true;
            } else if (args[0].equalsIgnoreCase("mode") && args.length == 2) {
                if (!Function.isInteger(args[1])) {
                    sender.sendMessage("§d[DeathChest]§c请输入数字");
                    return true;
                }
                int a = Integer.parseInt(args[1]);
                if (a < 0 || a > 7) {
                    sender.sendMessage("§d[DeathChest]§c指定模式错误");
                    return true;
                }
                PlaySet set = DeathChest.Config.getPlayerSet(sender.getName());
                set.setMode(a);
                DeathChest.Config.setPlayerSet(sender.getName(), set);
                sender.sendMessage("§d[DeathChest]§e已设置你的模式为" + a);
                return true;
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        ArrayList<String> arguments = null;
        if (command.getName().equalsIgnoreCase(DeathChest.command)) {
            if (args.length == 1) {
                arguments = new ArrayList<>();
                arguments.add("help");
                arguments.add("set");
                arguments.add("mode");
                if (sender.hasPermission("DeathChest.admin")) {
                    arguments.add("reload");
                }
            } else if (args.length == 2 && args[0].equalsIgnoreCase("mode")) {
                arguments = new ArrayList<>();
                arguments.add("0");
                arguments.add("1");
                arguments.add("2");
                arguments.add("3");
                arguments.add("4");
                arguments.add("5");
                arguments.add("6");
            }
        }
        return arguments;
    }
}
