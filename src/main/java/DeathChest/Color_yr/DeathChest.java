package DeathChest.Color_yr;

import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.logging.Logger;

public class DeathChest extends JavaPlugin {

    public static final String Version = "1.0.0";
    public static ConfigOBJ Config;
    private static File ConfigFile;
    public static Logger log;
    public static Plugin plugin;
    public static Hook Hook;

    public static final String command = "deathchest";
    private static boolean run = false;

    public static void save() {
        try {
            String data = new Gson().toJson(Config);
            if (ConfigFile.exists()) {
                Writer out = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(ConfigFile), StandardCharsets.UTF_8));

                out.flush();
                out.write(data);
                out.close();
            }
        } catch (Exception e) {
            log.warning("§d[DeathChest]§c配置文件错误");
            e.printStackTrace();
        }
    }

    private static void LoadConfig() throws FileNotFoundException {
        InputStreamReader reader = new InputStreamReader(new FileInputStream(ConfigFile), StandardCharsets.UTF_8);
        BufferedReader bf = new BufferedReader(reader);
        Config = new Gson().fromJson(bf, ConfigOBJ.class);
        try {
            reader.close();
            bf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Config == null) {
            log.warning("配置文件错误");
            Config = new ConfigOBJ();
        }
        log.info("§d[DeathChest]§e当前插件版本为：" + Version
                + "，你的配置文件版本为：" + Config.getVersion());
        CommandEX.GenHelp();
        Hook = new Hook();
        if (Config.getCost().isEnable())
            initV();
        else {
            run = true;
        }
    }

    public static void setConfig() {
        try {
            ConfigFile = new File(plugin.getDataFolder(), "config.json");
            if (!plugin.getDataFolder().exists())
                plugin.getDataFolder().mkdir();
            if (!ConfigFile.exists()) {
                InputStream in = plugin.getResource("config.json");
                Files.copy(in, ConfigFile.toPath());
                in.close();
            }
            LoadConfig();
        } catch (IOException e) {
            log.warning("§d[DeathChest]§c配置文件错误");
            e.printStackTrace();
        }
    }

    public static void initV() {
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
