/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thevoxelbox;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityListener;

/**
 *
 * @author Piotr
 */
public class VoxelSniperEntity extends EntityListener {

    //private boolean checked = false;
    public static boolean devserver = false;

    @Override
    public void onEntityDamage(EntityDamageEvent event) {
        //if (checked) {
            if (devserver) {
                event.setDamage(0);
                event.setCancelled(true);
                return;
            }
        //} else {
        //    checked = true;
        //    if (Bukkit.getServer().getIp().matches("przlabs.dyndns.org")) {
        //        VoxelSniper.log.info("[VoxelSniper] Damage has been disabled!");
        //        devserver = true;
        //    }
        //}

        if (event.getCause().equals(DamageCause.ENTITY_ATTACK)) {
            if (event instanceof EntityDamageByEntityEvent) {
                EntityDamageByEntityEvent eevent = (EntityDamageByEntityEvent) event;
                if (eevent.getEntity() instanceof Wolf && eevent.getDamager() instanceof Player) {
                    if (((Wolf) eevent.getEntity()).getHealth() - eevent.getDamage() <= 0) {
                        eevent.getDamager().getWorld().strikeLightning(eevent.getDamager().getLocation());
                        ((Player) eevent.getDamager()).chat(ChatColor.RED + "Look at me! I killed a VoxelFOX!");
                    } else {
                        eevent.getDamager().getWorld().strikeLightning(eevent.getDamager().getLocation());
                        Wolf wolf = (Wolf) event.getEntity();
                        wolf.setAngry(false);
                        wolf.setSitting(true);
                    }
                }
            }
        }
        if (event.getEntity() instanceof Wolf && (event.getCause().equals(DamageCause.LIGHTNING) || event.getCause().equals(DamageCause.FIRE) || event.getCause().equals(DamageCause.FIRE_TICK))) {
            event.setCancelled(true);
            Wolf wolf = (Wolf) event.getEntity();
            wolf.setAngry(false);
            wolf.setSitting(true);
            wolf.setFireTicks(0);
            wolf.setLastDamage(0);
        }
    }
}
