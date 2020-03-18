package DeathChest.Color_yr;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Event implements Listener {

    private List<Inventory> setBlock(World world, Location location, boolean needDoble) {
        List<Inventory> list = new ArrayList<>();
        while (needDoble) {
            Block temp = world.getBlockAt(location);
            if (temp.getType().equals(Material.AIR)) {
                Location location1 = location.clone();
                location1.setX(location1.getX() + 1);
                Block temp1 = world.getBlockAt(location1);
                if (temp1.getType().equals(Material.AIR)) {
                    temp1.setType(Material.CHEST);
                    temp.setType(Material.CHEST);

                    BlockState state = temp.getState();
                    if (state instanceof Chest)
                        list.add(((Chest) state).getBlockInventory());
                    state = temp1.getState();
                    if (state instanceof Chest)
                        list.add(((Chest) state).getBlockInventory());

                    return list;
                }
                location1.setX(location1.getX() - 2);
                temp1 = world.getBlockAt(location1);
                if (temp1.getType().equals(Material.AIR)) {
                    temp1.setType(Material.CHEST);
                    temp.setType(Material.CHEST);

                    BlockState state = temp.getState();
                    if (state instanceof Chest)
                        list.add(((Chest) state).getBlockInventory());
                    state = temp1.getState();
                    if (state instanceof Chest)
                        list.add(((Chest) state).getBlockInventory());

                    return list;
                }
                location1.setX(location1.getX() + 1);
                location1.setZ(location1.getZ() + 1);
                temp1 = world.getBlockAt(location1);
                if (temp1.getType().equals(Material.AIR)) {
                    temp1.setType(Material.CHEST);
                    temp.setType(Material.CHEST);

                    BlockState state = temp.getState();
                    if (state instanceof Chest)
                        list.add(((Chest) state).getBlockInventory());
                    state = temp1.getState();
                    if (state instanceof Chest)
                        list.add(((Chest) state).getBlockInventory());

                    return list;
                }
                location1.setZ(location1.getZ() - 2);
                temp1 = world.getBlockAt(location1);
                if (temp1.getType().equals(Material.AIR)) {
                    temp1.setType(Material.CHEST);
                    temp.setType(Material.CHEST);

                    BlockState state = temp.getState();
                    if (state instanceof Chest)
                        list.add(((Chest) state).getBlockInventory());
                    state = temp1.getState();
                    if (state instanceof Chest)
                        list.add(((Chest) state).getBlockInventory());

                    return list;
                }
            }
            location.setY(location.getY() + 1);
            if (location.getY() >= world.getMaxHeight()) {
                return null;
            }
        }
        Block temp = world.getBlockAt(location);
        temp.setType(Material.CHEST);
        BlockState state = temp.getState();
        if (state instanceof Chest)
            list.add(((Chest) state).getBlockInventory());
        return list;
    }

    private Inventory getNe(Block block) {
        Block block1 = block.getRelative(BlockFace.NORTH);
        if (block1.getType().equals(Material.CHEST)) {
            BlockState state = block1.getState();
            if (state instanceof Chest)
                return (((Chest) state).getBlockInventory());
        }
        block1 = block.getRelative(BlockFace.EAST);
        if (block1.getType().equals(Material.CHEST)) {
            BlockState state = block1.getState();
            if (state instanceof Chest)
                return (((Chest) state).getBlockInventory());
        }
        block1 = block.getRelative(BlockFace.SOUTH);
        if (block1.getType().equals(Material.CHEST)) {
            BlockState state = block1.getState();
            if (state instanceof Chest)
                return (((Chest) state).getBlockInventory());
        }
        block1 = block.getRelative(BlockFace.WEST);
        if (block1.getType().equals(Material.CHEST)) {
            BlockState state = block1.getState();
            if (state instanceof Chest)
                return (((Chest) state).getBlockInventory());
        }
        return null;
    }

    private List<Inventory> getChest(World world, Location location) {
        Block block;
        List<Inventory> list = new ArrayList<>();
        block = world.getBlockAt(location);
        if (!block.getType().equals(Material.CHEST) && !block.getType().equals(Material.TRAPPED_CHEST)) {
            return null;
        }
        Inventory temp = getNe(block);
        if (temp != null)
            list.add(temp);
        BlockState state = block.getState();
        if (state instanceof Chest)
            list.add(((Chest) state).getBlockInventory());
        return list;
    }

    private boolean genDeathChest(PlayerDeathEvent e) {
        Player player = e.getEntity();
        List<Inventory> inventory = setBlock(player.getWorld(), player.getLocation(), e.getDrops().size() > 27);
        if (inventory == null) {
            return false;
        }
        List<ItemStack> list = new ArrayList<>(e.getDrops());
        for (Inventory item : inventory) {
            for (ItemStack item1 : new ArrayList<>(list)) {
                item.addItem(item1);
                e.getDrops().remove(item1);
                list.remove(item1);
                if (item.firstEmpty() == -1)
                    break;
            }
        }
        if (e.getDrops().size() > 0) {
            player.sendMessage("§c你有一些物品无法放进箱子");
        }
        return true;
    }

    private boolean setChest(PlayerDeathEvent e) {
        Player player = e.getEntity();
        PlaySet set = DeathChest.Config.getPlayerSet(player.getName());
        if (set == null) {
            return false;
        }
        Location location = new Location(player.getWorld(), set.getX(), set.getY(), set.getZ());
        List<Inventory> inventory = getChest(player.getWorld(), location);
        if (inventory == null) {
            return false;
        }
        List<ItemStack> list = new ArrayList<>(e.getDrops());
        for (Inventory item : inventory) {
            for (ItemStack item1 : new ArrayList<>(list)) {
                item.addItem(item1);
                e.getDrops().remove(item1);
                list.remove(item1);
                if (item.firstEmpty() == -1)
                    break;
            }
        }
        if (e.getDrops().size() > 0) {
            player.sendMessage("§c你有一些物品无法放进箱子");
        }
        return true;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onplayerDeath(PlayerDeathEvent e) {
        if(e.getDrops().size() == 0)
            return;
        Player player = e.getEntity();
        PlaySet set = DeathChest.Config.getPlayerSet(player.getName());
        if (set == null)
            return;
        switch (set.getMode()) {
            case 1: {
                if (DeathChest.Hook.Check(player,
                        DeathChest.Config.getCost().getSaveInLocal())) {
                    if (!genDeathChest(e)) {
                        player.sendMessage("§c无法生成箱子");
                    } else {
                        DeathChest.Hook.Cost(player, DeathChest.Config.getCost().getSaveInLocal(),
                                "§e生成死亡箱子花费了" + DeathChest.Config.getCost().getSaveInLocal());
                    }
                } else {
                    player.sendMessage("§c你没有足够的钱生成死亡箱子");
                }
                break;
            }
            case 2:
                if (DeathChest.Hook.Check(player,
                        DeathChest.Config.getCost().getSaveInChest())) {
                    if (!setChest(e)) {
                        player.sendMessage("§c设置的箱子异常");
                    } else {
                        DeathChest.Hook.Cost(player, DeathChest.Config.getCost().getSaveInChest(),
                                "§e保存到设置的箱子花费了" + DeathChest.Config.getCost().getSaveInChest());
                    }
                } else {
                    player.sendMessage("§c你没有足够的钱保存到设置的箱子");
                }
                break;
            case 3: {
                if (DeathChest.Hook.Check(player,
                        DeathChest.Config.getCost().getNoDrop())) {
                    e.setKeepInventory(true);
                    e.getDrops().clear();
                    DeathChest.Hook.Cost(player, DeathChest.Config.getCost().getNoDrop(),
                            "§e死亡不掉落花费了" + DeathChest.Config.getCost().getNoDrop());
                } else {
                    player.sendMessage("§c你没有足够的钱死亡不掉");
                }
                break;
            }
            case 4: {
                if (DeathChest.Hook.Check(player,
                        DeathChest.Config.getCost().getNoDrop())) {
                    e.setKeepInventory(true);
                    e.getDrops().clear();
                    DeathChest.Hook.Cost(player, DeathChest.Config.getCost().getNoDrop(),
                            "§e死亡不掉落花费了" + DeathChest.Config.getCost().getNoDrop());
                    return;
                } else if (DeathChest.Hook.Check(player,
                        DeathChest.Config.getCost().getSaveInChest())) {
                    if (setChest(e)) {
                        DeathChest.Hook.Cost(player, DeathChest.Config.getCost().getSaveInChest(),
                                "§e保存到设置的箱子花费了" + DeathChest.Config.getCost().getSaveInChest());
                        return;
                    }
                } else if (DeathChest.Hook.Check(player,
                        DeathChest.Config.getCost().getSaveInLocal())) {
                    if (!genDeathChest(e)) {
                        player.sendMessage("§c无法生成箱子");
                    } else {
                        DeathChest.Hook.Cost(player, DeathChest.Config.getCost().getSaveInLocal(),
                                "§e生成死亡箱子花费了" + DeathChest.Config.getCost().getSaveInLocal());
                    }
                } else {
                    player.sendMessage("§c你没有足够的钱生成死亡箱子");
                }
                break;
            }
            case 5: {
                if (DeathChest.Hook.Check(player,
                        DeathChest.Config.getCost().getNoDrop())) {
                    e.setKeepInventory(true);
                    e.getDrops().clear();
                    DeathChest.Hook.Cost(player, DeathChest.Config.getCost().getNoDrop(),
                            "§e死亡不掉落花费了" + DeathChest.Config.getCost().getNoDrop());
                    return;
                } else if (DeathChest.Hook.Check(player,
                        DeathChest.Config.getCost().getSaveInChest())) {
                    if (!setChest(e)) {
                        player.sendMessage("§c设置的箱子异常");
                    } else {
                        DeathChest.Hook.Cost(player, DeathChest.Config.getCost().getSaveInChest(),
                                "§e保存到设置的箱子花费了" + DeathChest.Config.getCost().getSaveInChest());
                    }
                } else {
                    player.sendMessage("§c你没有足够的钱保存到设置的箱子");
                }
                break;
            }
            case 6: {
                if (DeathChest.Hook.Check(player,
                        DeathChest.Config.getCost().getNoDrop())) {
                    e.setKeepInventory(true);
                    e.getDrops().clear();
                    DeathChest.Hook.Cost(player, DeathChest.Config.getCost().getNoDrop(),
                            "§e死亡不掉落花费了" + DeathChest.Config.getCost().getNoDrop());
                    return;
                } else if (DeathChest.Hook.Check(player,
                        DeathChest.Config.getCost().getSaveInLocal())) {
                    if (!genDeathChest(e)) {
                        player.sendMessage("§c无法生成箱子");
                    } else {
                        DeathChest.Hook.Cost(player, DeathChest.Config.getCost().getSaveInLocal(),
                                "§e生成死亡箱子花费了" + DeathChest.Config.getCost().getSaveInLocal());
                    }
                } else {
                    player.sendMessage("§c你没有足够的钱生成死亡箱子");
                }
                break;
            }
            case 7: {
                if (DeathChest.Hook.Check(player,
                        DeathChest.Config.getCost().getSaveInChest())) {
                    if (setChest(e)) {
                        DeathChest.Hook.Cost(player, DeathChest.Config.getCost().getSaveInChest(),
                                "§e保存到设置的箱子花费了" + DeathChest.Config.getCost().getSaveInChest());
                        return;
                    }
                } else if (DeathChest.Hook.Check(player,
                        DeathChest.Config.getCost().getSaveInLocal())) {
                    if (!genDeathChest(e)) {
                        player.sendMessage("§c无法生成箱子");
                    } else {
                        DeathChest.Hook.Cost(player, DeathChest.Config.getCost().getSaveInLocal(),
                                "§e生成死亡箱子花费了" + DeathChest.Config.getCost().getSaveInLocal());
                    }
                } else {
                    player.sendMessage("§c你没有足够的钱生成死亡箱子");
                }
                break;
            }
        }
    }

    @EventHandler
    public void PlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        if (!DeathChest.Config.havePlaySet(player.getName())) {
            DeathChest.Config.setPlayerSet(player.getName(), new PlaySet());
        } else {
            player.sendMessage("§d[DeathChest]§e你的死亡掉落保护模式为：" + DeathChest.Config.getPlayerSet(player.getName()).getMode());
        }
    }
}
