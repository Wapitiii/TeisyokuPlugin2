package net.jp.minecraft.plugins.Listener;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

/**
 * TeisyokuPlugin2
 *
 * @auther syokkendesuyo
 */
public class Listener_MobGrief implements Listener{
    @EventHandler
    public void creeperGrief(EntityExplodeEvent e) {
        if(!(e.getEntityType().equals(EntityType.CREEPER))){
            return;
        }
        if(!(e.getLocation().getWorld().getName().equals("world"))){
            return;
        }
        e.blockList().clear();
    }

    @EventHandler
    public void endermanGrief(EntityChangeBlockEvent e){
        if(!(e.getEntityType().equals(EntityType.ENDERMAN))){
            return;
        }
        if(!(e.getBlock().getLocation().getWorld().getName().equals("world"))){
            return;
        }
        e.setCancelled(true);
    }
}
