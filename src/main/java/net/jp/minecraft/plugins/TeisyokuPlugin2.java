package net.jp.minecraft.plugins;

import net.jp.minecraft.plugins.API.API_Trash;
import net.jp.minecraft.plugins.Commands.*;
import net.jp.minecraft.plugins.Config.CustomConfig;
import net.jp.minecraft.plugins.Config.SaveUUID;
import net.jp.minecraft.plugins.GUI.GUI_ClickEvent;
import net.jp.minecraft.plugins.GUI.GUI_YesNo;
import net.jp.minecraft.plugins.Listener.*;
import net.jp.minecraft.plugins.TPoint.TPointBuyGUI;
import net.jp.minecraft.plugins.TPoint.TPointIndexGUI;
import net.jp.minecraft.plugins.Utility.Color;
import net.jp.minecraft.plugins.Utility.Msg;
import net.jp.minecraft.plugins.Utility.PlayerFile;
import net.jp.minecraft.plugins.Utility.Sounds;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.io.File;
import java.util.List;

public class TeisyokuPlugin2 extends JavaPlugin implements Listener {

    /**
     * Teisyoku.yml
     */
    public CustomConfig configTeisyoku;

    /**
     * Gift.yml
     */
    public CustomConfig configGift;

    public File newConfig_last;
    public File newConfig_cart;
    public File newConfig_tpoint;
    public File newConfig_tpoint_settings;
    public File newConfig_horse;
    public FileConfiguration LastJoinPlayerConfig;
    public FileConfiguration CartConfig;
    public FileConfiguration TPointConfig;
    public FileConfiguration TPointSettingsConfig;
    public FileConfiguration HorseConfig;
    private static TeisyokuPlugin2 instance;

    private String supportVersion = "1.13.2-R0.1-SNAPSHOT";

    public String ZombieTicket = ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "ゾンビホース変換チケット";
    public String SkeletonTicket = ChatColor.GRAY + "" + ChatColor.BOLD + "スケルトンホース変換チケット";

    public void onEnable() {
        String version = Bukkit.getBukkitVersion();
        Msg.info(Bukkit.getConsoleSender(), "Running on " + version);

        PluginManager pm = Bukkit.getServer().getPluginManager();

        instance = this;

        pm.registerEvents(new Listener_JoinEvent(), this);
        pm.registerEvents(new Listener_NetherGateEvent(), this);
        pm.registerEvents(new Listener_DeathEvent(), this);
        pm.registerEvents(new Listener_EntityDamage(), this);
        pm.registerEvents(new Listener_MinecartEvent(), this);
        pm.registerEvents(new API_Trash(), this);
        pm.registerEvents(new Listener_LastJoin(), this);
        pm.registerEvents(new Listener_Chat(), this);
        pm.registerEvents(new Listener_Sign(), this);
        pm.registerEvents(new GUI_YesNo(), this);
        pm.registerEvents(new GUI_ClickEvent(), this);
        pm.registerEvents(new Listener_SignColor(), this);
        pm.registerEvents(new Listener_SignEdit(), this);
        pm.registerEvents(new Listener_MobGrief(), this);
        pm.registerEvents(new Listener_Horse(), this);
        pm.registerEvents(new Listener_SpawnEgg(), this);
        pm.registerEvents(new Listener_SkeletonHorse(), this);
        pm.registerEvents(new Listener_Fly(), this);

        pm.registerEvents(new Sounds(), this);

        pm.registerEvents(new TPointIndexGUI(), this);
        pm.registerEvents(new TPointBuyGUI(), this);
        //pm.registerEvents(new GUI_Anvil(), this);

        pm.registerEvents(new PlayerFile(), this);

        pm.registerEvents(new SaveUUID(), this);


        getCommand("help").setExecutor(new Command_Help());

        getCommand("fly").setExecutor(new Command_Fly());

        getCommand("t").setExecutor(new Command_Teisyoku());
        getCommand("teisyoku").setExecutor(new Command_Teisyoku());

        getCommand("p").setExecutor(new Command_Players());
        getCommand("players").setExecutor(new Command_Players());

        getCommand("last").setExecutor(new Command_Last());

        getCommand("ad").setExecutor(new Command_Ad());
        getCommand("notice").setExecutor(new Command_Ad());

        getCommand("call").setExecutor(new Command_Call());

        getCommand("cart").setExecutor(new Command_Cart());
        getCommand("minecart").setExecutor(new Command_Cart());

        getCommand("nick").setExecutor(new Command_Nick());
        getCommand("nickname").setExecutor(new Command_Nick());

        getCommand("tt").setExecutor(new Command_TPoint());
        getCommand("point").setExecutor(new Command_TPoint());
        getCommand("tpoint").setExecutor(new Command_TPoint());

        getCommand("ri").setExecutor(new Command_RailwayInfo());
        getCommand("railwayinfo").setExecutor(new Command_RailwayInfo());

        getCommand("tabname").setExecutor(new Command_TabName());

        getCommand("horse").setExecutor(new Command_Horse());

        getCommand("carthelp").setExecutor(new Command_CartHelp());
        getCommand("ch").setExecutor(new Command_CartHelp());

        getCommand("color").setExecutor(new Command_Color());

        getCommand("gg").setExecutor(new Command_GoodGame());

        getCommand("bp").setExecutor(new Command_BaiPoint());

        getCommand("se").setExecutor(new Command_SignEdit());

        getCommand("horseticket").setExecutor(new Command_HorseTicket());
        getCommand("ht").setExecutor(new Command_HorseTicket());

        getCommand("sign").setExecutor(new Command_SignEdit());

        getCommand("tflag").setExecutor(new Command_TFlag());

        getCommand("trash").setExecutor(new Command_Trash());
        getCommand("gomi").setExecutor(new Command_Trash());
        getCommand("gomibako").setExecutor(new Command_Trash());

        //サポートバージョンを確認
        if (version.equals(supportVersion)) {

            //events
            pm.registerEvents(new Listener_Tab(), this);
            pm.registerEvents(new Listener_Daunii_1_13(), this);

            //commands
            getCommand("daunii").setExecutor(new Command_Daunii());
            getCommand("tps").setExecutor(new Command_TPS());
            getCommand("status").setExecutor(new Command_TPS());
            getCommand("s").setExecutor(new Command_TPS());

            //schedule
            BukkitScheduler scheduler_tps = Bukkit.getServer().getScheduler();
            scheduler_tps.scheduleSyncRepeatingTask(this, new Runnable() {
                public void run() {
                    Double tps = Listener_TicksPerSecond_1_13.getTps(1);
                    if (Listener_TicksPerSecond_1_13.getTps(1) < 16) {
                        Msg.warning(Bukkit.getConsoleSender(), "現在TPSが低下しています：" + ChatColor.YELLOW + Listener_TicksPerSecond_1_13.doubleToString(tps), true);
                    }
                }
            }, 0L, 6000L);

            //message
            Msg.success(Bukkit.getConsoleSender(), version + "用に作成された一部機能が開放されました");
        } else {
            Msg.warning(Bukkit.getConsoleSender(), version + "はサポート対象外のバージョンです");
        }

        // コンフィグを作成
        // TODO: 全てのコンフィグに関する項目をCustomConfigに置き換える
        configTeisyoku = new CustomConfig(this, "Teisyoku.yml");
        configTeisyoku.saveDefaultConfig();

        configGift = new CustomConfig(this, "Gift.yml");
        configGift.saveDefaultConfig();

        LastJoinPlayerConfig();
        saveLastPlayerJoinConfig();

        TPointConfig();
        saveTPointConfig();

        CartConfig();
        saveCartConfig();

        HorseConfig();
        saveHorseConfig();

        TPointSettingsConfig();
        saveTPointSettingsConfig();

        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
            public void run() {
                List<String> ad = configTeisyoku.getConfig().getStringList("ad");
                for (String s : ad) {
                    Msg.info(Bukkit.getConsoleSender(), Color.convert(s), true);
                }
            }
        }, 0L, 54000L);
    }

    public void LastJoinPlayerConfig() {
        this.newConfig_last = new File(getDataFolder(), "LastJoinPlayersData.yml");
        this.LastJoinPlayerConfig = YamlConfiguration.loadConfiguration(this.newConfig_last);
        saveLastPlayerJoinConfig();
    }

    public void saveLastPlayerJoinConfig() {
        try {
            this.LastJoinPlayerConfig.save(this.newConfig_last);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reloadLastPlayerJoinConfig() {
        try {
            this.LastJoinPlayerConfig.load(this.newConfig_last);
            this.LastJoinPlayerConfig.save(this.newConfig_last);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void TPointSettingsConfig() {
        this.newConfig_tpoint_settings = new File(getDataFolder(), "TPoint_Settings.yml");
        this.TPointSettingsConfig = YamlConfiguration.loadConfiguration(this.newConfig_tpoint_settings);
        saveTPointSettingsConfig();
    }

    public void saveTPointSettingsConfig() {
        try {
            this.TPointSettingsConfig.save(this.newConfig_tpoint_settings);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reloadTPointSettingsConfig() {
        try {
            this.TPointSettingsConfig.load(this.newConfig_tpoint_settings);
            this.TPointSettingsConfig.save(this.newConfig_tpoint_settings);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void TPointConfig() {
        this.newConfig_tpoint = new File(getDataFolder(), "TPoint.yml");
        this.TPointConfig = YamlConfiguration.loadConfiguration(this.newConfig_tpoint);
        saveTPointConfig();
    }

    public void saveTPointConfig() {
        try {
            this.TPointConfig.save(this.newConfig_tpoint);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reloadTPointConfig() {
        try {
            this.TPointConfig.load(this.newConfig_tpoint);
            this.TPointConfig.save(this.newConfig_tpoint);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void CartConfig() {
        this.newConfig_cart = new File(getDataFolder(), "Cart.yml");
        this.CartConfig = YamlConfiguration.loadConfiguration(this.newConfig_cart);
        saveCartConfig();
    }

    public void saveCartConfig() {
        try {
            this.CartConfig.save(this.newConfig_cart);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reloadCartConfig() {
        try {
            this.CartConfig.load(this.newConfig_cart);
            this.CartConfig.save(this.newConfig_cart);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void HorseConfig() {
        this.newConfig_horse = new File(getDataFolder(), "Horses.yml");
        this.HorseConfig = YamlConfiguration.loadConfiguration(this.newConfig_horse);
        saveHorseConfig();
    }

    public void saveHorseConfig() {
        try {
            this.HorseConfig.save(this.newConfig_horse);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reloadHorseConfig() {
        try {
            this.HorseConfig.load(this.newConfig_horse);
            this.HorseConfig.save(this.newConfig_horse);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static TeisyokuPlugin2 getInstance() {
        return instance;
    }
}