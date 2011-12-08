/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thevoxelbox.brush;

import com.thevoxelbox.vMessage;
import com.thevoxelbox.vSniper;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.CreatureType;

/**
 *THIS BRUSH SHOULD NOT USE PERFORMERS
 * @author Piotr
 */
public class Monster extends Brush {

    protected CreatureType ct = CreatureType.ZOMBIE;
    
    public Monster() {
        name = "Monster";
    }

    @Override
    protected void arrow(vSniper v) {
        spawn(v);
    }

    @Override
    protected void powder(vSniper v) {
        spawn(v);
    }

    @Override
    public void info(vMessage vm) {
        vm.brushMessage(ChatColor.LIGHT_PURPLE + "Monster brush" + " (" + ct.getName() + ")");
        vm.size();
    }

    @Override
    public void parameters(String[] par, vSniper v) {
        if(par[1].equalsIgnoreCase("info")) {
            v.p.sendMessage(ChatColor.BLUE + "The aviable creature types are as follows:");
            String names = "";
            for(CreatureType cre : CreatureType.values()) {
                names += ChatColor.AQUA + " | " + ChatColor.DARK_GREEN + cre.getName();
            }
            names += ChatColor.AQUA + " |";
            v.p.sendMessage(names);
            return;
        } else {
            CreatureType cre = CreatureType.fromName(par[1]);
            if(cre != null) {
                ct = cre;
                v.p.sendMessage(ChatColor.GREEN + "Creature type set to " + ct.getName());
            } else {
                v.p.sendMessage(ChatColor.RED + "This is not a valid creature!");
            }
        }
    }

    protected void spawn(vSniper v) {
        for(int x = 0; x < v.brushSize; x++) {
            s.spawnCreature(lb.getLocation(), ct);
        }
    }
}
