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

    class RE
    {
        public boolean ok;
        public Location location;
    }
    private List<Inventory> setBlock(World world, Location location, boolean needDoble) {
        boolean up = true;
        if (location.getY() >= world.getMaxHeight()) {
            up = false;
            location.setY(world.getMaxHeight() - 1);
        }
        List<Inventory> list = new ArrayList<>();
        Block temp;
        int max = world.getMaxHeight();
        while (needDoble) {
            temp = world.getBlockAt(location);
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
            if (up)
                location.setY(location.getBlockY() + 1);
            else
                location.setY(location.getBlockY() - 1);
            if (location.getBlockY() >= max) {
                up = false;
            }
            if (location.getBlockY() <= 0) {
                return null;
            }
        }
        temp = world.getBlockAt(location);
        while (!temp.getType().equals(Material.AIR)) {
            temp = world.getBlockAt(location);
            if (up)
                location.setY(location.getBlockY() + 1);
            else
                location.setY(location.getBlockY() - 1);
            if (location.getBlockY() >= max) {
                up = false;
            }
            if (location.getBlockY() <= 0) {
                return null;
            }
        }
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

    private RE genDeathChest(PlayerDeathEvent e) {
        Player player = e.getEntity();
        List<Inventory> inventory = setBlock(player.getWorld(), player.getLocation(), e.getDrops().size() > 27);
        RE re = new RE();
        re.ok = Check(inventory, e, player);
        if (re.ok)
            re.location = inventory.get(0).getLocation();
        return re;
    }

    private boolean setChest(PlayerDeathEvent e) {
        Player player = e.getEntity();
        PlaySet set = DeathChest.Config.getPlayerSet(player.getName());
        if (set == null) {
            return false;
        }
        Location location = new Location(player.getWorld(), set.getX(), set.getY(), set.getZ());
        List<Inventory> inventory = getChest(player.getWorld(), location);
        return Check(inventory, e, player);
    }

    private boolean Check(List<Inventory> inventory, PlayerDeathEvent e, Player player) {
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
            player.sendMessage(DeathChest.Config.getMessage().getCantPlace());
        }
        return true;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onplayerDeath(PlayerDeathEvent e) {
        if (e.getDrops().size() == 0)
            return;
        Player player = e.getEntity();
        PlaySet set = DeathChest.Config.getPlayerSet(player.getName());
        if (set == null)
            return;
        switch (set.getMode()) {
            case 1: {
                if (DeathChest.Hook.Check(player,
                        DeathChest.Config.getCost().getSaveInLocal())) {
                    RE re = genDeathChest(e);
                    if (!re.ok) {
                        player.sendMessage(DeathChest.Config.getMessage().getCantGen());
                    } else {
                        GenChest(player, re.location);
                    }
                } else {
                    player.sendMessage(DeathChest.Config.getMessage().getNoMoney());
                }
                break;
            }
            case 2:
                if (DeathChest.Hook.Check(player,
                        DeathChest.Config.getCost().getSaveInChest())) {
                    if (!setChest(e)) {
                        player.sendMessage(DeathChest.Config.getMessage().getErrorChest());
                    } else {
                        GenLocalChest(player);
                    }
                } else {
                    player.sendMessage(DeathChest.Config.getMessage().getNoMoney1());
                }
                break;
            case 3: {
                if (DeathChest.Hook.Check(player,
                        DeathChest.Config.getCost().getNoDrop())) {
                    e.setKeepInventory(true);
                    e.getDrops().clear();

                } else {
                    player.sendMessage(DeathChest.Config.getMessage().getNoMoney2());
                }
                break;
            }
            case 4: {
                if (DeathChest.Hook.Check(player,
                        DeathChest.Config.getCost().getNoDrop())) {
                    e.setKeepInventory(true);
                    e.getDrops().clear();
                    NoDrop(player);
                    return;
                } else if (DeathChest.Hook.Check(player,
                        DeathChest.Config.getCost().getSaveInChest())) {
                    if (setChest(e)) {
                        GenLocalChest(player);
                        return;
                    }
                } else if (DeathChest.Hook.Check(player,
                        DeathChest.Config.getCost().getSaveInLocal())) {
                    RE re = genDeathChest(e);
                    if (!re.ok) {
                        player.sendMessage(DeathChest.Config.getMessage().getCantGen());
                    } else {
                        GenChest(player, re.location);
                    }
                } else {
                    player.sendMessage(DeathChest.Config.getMessage().getNoMoney());
                }
                break;
            }
            case 5: {
                if (DeathChest.Hook.Check(player,
                        DeathChest.Config.getCost().getNoDrop())) {
                    e.setKeepInventory(true);
                    e.getDrops().clear();
                    NoDrop(player);
                    return;
                } else if (DeathChest.Hook.Check(player,
                        DeathChest.Config.getCost().getSaveInChest())) {
                    if (!setChest(e)) {
                        player.sendMessage(DeathChest.Config.getMessage().getErrorChest());
                    } else {
                        GenLocalChest(player);
                    }
                } else {
                    player.sendMessage(DeathChest.Config.getMessage().getNoMoney1());
                }
                break;
            }
            case 6: {
                if (DeathChest.Hook.Check(player,
                        DeathChest.Config.getCost().getNoDrop())) {
                    e.setKeepInventory(true);
                    e.getDrops().clear();
                    NoDrop(player);
                    return;
                } else if (DeathChest.Hook.Check(player,
                        DeathChest.Config.getCost().getSaveInLocal())) {
                    RE re = genDeathChest(e);
                    if (!re.ok) {
                        player.sendMessage(DeathChest.Config.getMessage().getCantGen());
                    } else {
                        GenChest(player, re.location);
                    }
                } else {
                    player.sendMessage(DeathChest.Config.getMessage().getNoMoney());
                }
                break;
            }
            case 7: {
                if (DeathChest.Hook.Check(player,
                        DeathChest.Config.getCost().getSaveInChest())) {
                    if (setChest(e)) {
                        GenLocalChest(player);
                        return;
                    }
                } else if (DeathChest.Hook.Check(player,
                        DeathChest.Config.getCost().getSaveInLocal())) {
                    RE re = genDeathChest(e);
                    if (!re.ok) {
                        player.sendMessage(DeathChest.Config.getMessage().getCantGen());
                    } else {
                        GenChest(player, re.location);
                    }
                } else {
                    player.sendMessage(DeathChest.Config.getMessage().getNoMoney());
                }
                break;
            }
        }
    }

    private void GenChest(Player player, Location pos) {
        String temp = DeathChest.Config.getMessage().getCost();
        temp = temp.replace("%Money%", "" + DeathChest.Config.getCost().getSaveInLocal());
        DeathChest.Hook.Cost(player, DeathChest.Config.getCost().getSaveInLocal(), temp);
        temp = DeathChest.Config.getMessage().getGen();
        temp = temp.replace("%x%", "" + pos.getBlockX())
                .replace("%y%", "" + pos.getBlockY())
                .replace("%z%", "" + pos.getBlockZ());
        player.sendMessage(temp);
    }

    private void GenLocalChest(Player player) {
        String temp = DeathChest.Config.getMessage().getCost1();
        temp = temp.replace("%Money%", "" + DeathChest.Config.getCost().getSaveInChest());
        DeathChest.Hook.Cost(player, DeathChest.Config.getCost().getSaveInChest(), temp);
    }

    private void NoDrop(Player player) {
        String temp = DeathChest.Config.getMessage().getCost2();
        temp = temp.replace("%Money%", "" + DeathChest.Config.getCost().getNoDrop());
        DeathChest.Hook.Cost(player, DeathChest.Config.getCost().getNoDrop(), temp);
    }

    @EventHandler
    public void PlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        if (!DeathChest.Config.havePlaySet(player.getName())) {
            DeathChest.Config.setPlayerSet(player.getName(), new PlaySet());
        } else {
            String temp = DeathChest.Config.getMessage().getMode();
            int b = 0;
            int mode = DeathChest.Config.getPlayerSet(player.getName()).getMode();
            if(DeathChest.Config.getDisable().contains(mode)) {
                for (int a : DeathChest.Config.getDisable()) {
                    if (a == b)
                        b++;
                    else {
                        DeathChest.Config.getPlayerSet(player.getName()).setMode(b);
                        break;
                    }
                }
            }
            temp = temp.replace("%Mode%", "" + mode);
            player.sendMessage(temp);
        }
    }
}
