package net.jp.minecraft.plugins;

import net.jp.minecraft.plugins.Utility.Msg;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * TeisyokuPlugin2
 *
 * @auther syokkendesuyo
 */
public class Listener_Horse implements Listener{
    @EventHandler
    public void HorseClick (PlayerInteractEntityEvent event){
        if(event.getRightClicked().getType().equals(EntityType.HORSE)) {
            Player player = event.getPlayer();
            UUID entityUUID = event.getRightClicked().getUniqueId();

            //棒以外は無視
            if(!(event.getPlayer().getItemInHand().getType() == Material.STICK)){
                return;
            }
            if(player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD + "馬保護ツール")){
                HorseRegister(player,entityUUID);
                event.setCancelled(true);
                return;
            }
            if(player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD + "馬保護解除ツール")){
                HorseRemove(player,entityUUID);
                event.setCancelled(true);
                return;
            }
            if(player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD + "馬保護情報確認ツール")){
                if(isRegister(entityUUID) == true){
                    Msg.info(player, "この馬は保護されています");
                    getStatus(player,entityUUID);
                }
                else {
                    Msg.info(player, "この馬は保護されていません");
                }
                event.setCancelled(true);
                return;
            }
        }
    }

    /*
    馬に乗る時
     */
    @EventHandler
    public static void HorseRide(VehicleEnterEvent event){
        if(event.getVehicle() instanceof Horse){
            Player player = (Player)event.getEntered();
            UUID entityUUID = event.getVehicle().getUniqueId();
            UUID playerUUID =  player.getUniqueId();

            if(! player.getItemInHand().getType().equals(Material.AIR)){
                if(player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD + "馬保護ツール") || player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD + "馬保護解除ツール") || player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD + "馬保護情報確認ツール")){
                    event.setCancelled(true);
                    return;
                }
            }

            int temp = isEqual(player,playerUUID,entityUUID);
            if(temp == 1){
                Msg.success(player, "ロックされた馬に乗りました");
                return;
            }
            else if(temp == 2){
                Msg.info(player, "登録情報の無い馬です");
                return;
            }
            else {
                Msg.warning(player, "この馬はロックされています");
                event.setCancelled(true);
                getStatus(player,entityUUID);
                return;
            }
        }
    }

    /*
    馬をロックする
     */
    public static void HorseRegister (Player player , UUID uuid){
        if(isRegister(uuid) == true){
            Msg.warning(player,"この馬は既にロックされた馬です");
            getStatus(player,uuid);
            return;
        }
        try{
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH時mm分");
            String strDate = sdf.format(date.getTime());

            TeisyokuPlugin2.getInstance().HorseConfig.set(uuid.toString(), player.toString());
            TeisyokuPlugin2.getInstance().HorseConfig.set(uuid.toString() + ".data",strDate);
            TeisyokuPlugin2.getInstance().HorseConfig.set(uuid.toString() + ".player",player.getName().toString());
            TeisyokuPlugin2.getInstance().HorseConfig.set(uuid.toString() + ".uuid",player.getUniqueId().toString());
            TeisyokuPlugin2.getInstance().HorseConfig.set(uuid.toString() + ".mode","private");

            TeisyokuPlugin2.getInstance().saveHorseConfig();
            Msg.success(player, "馬をロックしました");

        }
        catch (Exception e){
            Msg.warning(player, "不明なエラーが発生しました");
            e.printStackTrace();
        }
    }

    /*
    馬のロックを解除する
     */
    public static void HorseRemove (Player player , UUID entityUUID){
        if(isEqual(player, player.getUniqueId(), entityUUID) == 2){
            Msg.warning(player,"この馬は保護されていません");
            return;
        }
        else if(isEqual(player,player.getUniqueId(),entityUUID) == 0){
            Msg.warning(player,"登録者以外馬のロックは解除できません");
            getStatus(player,entityUUID);
            return;
        }

        try{
            TeisyokuPlugin2.getInstance().HorseConfig.set(entityUUID.toString(), null);
            TeisyokuPlugin2.getInstance().saveHorseConfig();
            Msg.success(player, "馬のロックを解除しました");
        }
        catch (Exception e){
            Msg.warning(player, "不明なエラーが発生しました");
            e.printStackTrace();
        }
    }

    public static int isEqual(Player player , UUID playerUUID , UUID entityUUID){
        if(playerUUID.toString().equals(TeisyokuPlugin2.getInstance().HorseConfig.get(entityUUID + ".uuid"))){
            return 1;
        }
        else{
            if(TeisyokuPlugin2.getInstance().HorseConfig.getString(entityUUID + ".uuid") == null){
                return 2;
            }
            return 0;
        }
    }

    public static void getStatus(Player player , UUID entityUUID){
        Msg.info(player,"登録者名 : " + TeisyokuPlugin2.getInstance().HorseConfig.getString(entityUUID + ".player"));
        Msg.info(player,"登録日 : " + TeisyokuPlugin2.getInstance().HorseConfig.getString(entityUUID + ".data"));
    }

    public static boolean isRegister(UUID entityUUID){
        if(TeisyokuPlugin2.getInstance().HorseConfig.getString(entityUUID + ".uuid") == null){
            return false;
        }
        else{
            return true;
        }
    }
}