package net.jp.minecraft.plugins.Listener;

import net.jp.minecraft.plugins.TeisyokuPlugin2;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * TeisyokuPlugin2
 *
 * @author syokkendesuyo
 */
@Deprecated
public class Listener_LastJoin implements Listener {

    @EventHandler
    public void join(PlayerJoinEvent event) {

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH時mm分");
        String strDate = sdf.format(date.getTime());

        //プレイヤー名
        TeisyokuPlugin2.getInstance().LastJoinPlayerConfig.set(event.getPlayer().getUniqueId().toString() + ".Name", event.getPlayer().getName());

        //ログイン時の日時
        TeisyokuPlugin2.getInstance().LastJoinPlayerConfig.set(event.getPlayer().getUniqueId().toString() + ".JoinDate", strDate);
        TeisyokuPlugin2.getInstance().LastJoinPlayerConfig.set(event.getPlayer().getUniqueId().toString() + ".JoinTimestamp", System.currentTimeMillis());

        TeisyokuPlugin2.getInstance().saveLastPlayerJoinConfig();
    }

    @EventHandler
    public void quit(PlayerQuitEvent event) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH時mm分");
        String strDate = sdf.format(date.getTime());

        //プレイヤー名
        TeisyokuPlugin2.getInstance().LastJoinPlayerConfig.set(event.getPlayer().getUniqueId().toString() + ".Name", event.getPlayer().getName());

        //ログアウト時の日時
        TeisyokuPlugin2.getInstance().LastJoinPlayerConfig.set(event.getPlayer().getUniqueId().toString() + ".QuitDate", strDate);
        TeisyokuPlugin2.getInstance().LastJoinPlayerConfig.set(event.getPlayer().getUniqueId().toString() + ".QuitTimestamp", System.currentTimeMillis());

        TeisyokuPlugin2.getInstance().saveLastPlayerJoinConfig();
    }
}