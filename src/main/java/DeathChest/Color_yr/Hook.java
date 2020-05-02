package DeathChest.Color_yr;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class Hook {
    private Economy econ = null;

    public boolean setupEconomy() {
        RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return true;
    }

    public boolean Check(Player player, int cost) {
        if(!DeathChest.Config.getCost().isEnable())
            return true;
        return econ.has(player, cost);
    }

    public void Cost(Player player, int cost, String message)
    {
        if(!DeathChest.Config.getCost().isEnable())
            return;
        EconomyResponse r = econ.withdrawPlayer(player, cost);
        if(r.transactionSuccess())
        {
            player.sendMessage(message);
        }
    }
}
