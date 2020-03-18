package DeathChest.Color_yr;

import com.google.gson.Gson;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.logging.Logger;

public class DeathChest extends JavaPlugin {

    public static final String Version = "1.0.0";
    public static ConfigOBJ Config;
    public static File ConfigFile;
    public static Logger log;
    public static Plugin plugin;
    public static Hook Hook;

    public static final String command = "deathchest";
    private static boolean run = false;

    public static void save() {
        try {
            String data = new Gson().toJson(Config);
            if (ConfigFile.exists()) {
                Writer out = new FileWriter(ConfigFile);
                out.write(data);
                out.close();
            }
        } catch (Exception e) {
            log.warning("§d[DeathChest]§c配置文件错误");
            e.printStackTrace();
        }
    }

    public static void LoadConfig() throws FileNotFoundException {
        InputStreamReader reader = new InputStreamReader(new FileInputStream(ConfigFile), StandardCharsets.UTF_8);
        BufferedReader bf = new BufferedReader(reader);
        Config = new Gson().fromJson(bf, ConfigOBJ.class);
        if (Config == null) {
            log.warning("配置文件错误");
            Config = new ConfigOBJ();
        }
        log.info("§d[DeathChest]§e当前插件版本为：" + Version
                + "，你的配置文件版本为：" + Config.getVersion());
        initV();
    }

    public static void setConfig() {
        try {
            ConfigFile = new File(plugin.getDataFolder(), "config.json");
            if (!plugin.getDataFolder().exists())
                plugin.getDataFolder().mkdir();
            if (!ConfigFile.exists()) {
                InputStream in = plugin.getResource("config.json");
                Files.copy(in, ConfigFile.toPath());
            }
            LoadConfig();
        } catch (IOException e) {
            log.warning("§d[DeathChest]§c配置文件错误");
            e.printStackTrace();
        }
    }

    private Economy econ = null;

    public boolean setupEconomy() {
        if (Bukkit.getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return true;
    }

    public boolean Check(String name, int cost) {
        Player player = Bukkit.getPlayer(name);
        if (player == null)
            return false;
        return econ.has(player, cost);
    }

    public void Cost(String name, int cost) {
        Player player = Bukkit.getPlayer(name);
        if (player == null)
            return;
        EconomyResponse r = econ.depositPlayer(player, cost);
        if (r.transactionSuccess()) {
            player.sendMessage("");
        }
    }

    public static void initV() {
        Hook = new Hook();
        if (Hook.setupEconomy()) {
            log.info("§d[DeathChest]§e已启用Vault支持");
            run = true;
        } else {
            log.info("§d[DeathChest]§c无法启用Vault支持");
            log.info("§d[DeathChest]§c插件将禁用");
            Bukkit.getPluginManager().disablePlugin(plugin);
        }
    }

    @Override
    public void onEnable() {
        plugin = this;
        log = getLogger();
        log.info("§d[DeathChest]§e正在启动，感谢使用，本插件交流群：571239090");
        setConfig();
        if (run) {
            Bukkit.getPluginCommand(command).setExecutor(new CommandEX());
            Bukkit.getPluginCommand(command).setTabCompleter(new CommandEX());
            Bukkit.getPluginManager().registerEvents(new Event(), this);
            log.info("§d[DeathChest]§e已启动-" + Version);
        }
    }

    @Override
    public void onDisable() {
        save();
        log.info("§d[DeathChest]§e已停止，感谢使用");
    }
}
