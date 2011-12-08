/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thevoxelbox.brush;

import com.thevoxelbox.vMessage;
import com.thevoxelbox.vSniper;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

/**
 *
 * @author Voxel
 */
public class EntityRemoval extends Brush {

    private int entcount = 0;
    private int chunkcount = 0;

    public EntityRemoval() {
        name = "Entity Removal";
    }

    @Override
    public void info(vMessage vm) {
        vm.brushName(name);
        vm.size();
    }

    @Override
    protected void arrow(vSniper v) {
        radialRemoval(v);
    }

    @Override
    protected void powder(vSniper v) {
        radialRemoval(v);
    }

    protected void radialRemoval(vSniper v) {
        entcount = 0;
        chunkcount = 0;
        Chunk ch = tb.getChunk();
        removeEntities(ch);
        for (int x = ch.getX() - v.brushSize; x <= ch.getX() + v.brushSize; x++) {
            for (int z = ch.getZ() - v.brushSize; z <= ch.getZ() + v.brushSize; z++) {
                removeEntities(s.getChunkAt(x, z));
                chunkcount++;
            }
        }
        v.p.sendMessage(ChatColor.GREEN + "Removed " + ChatColor.RED + entcount + ChatColor.GREEN + " entities out of " + ChatColor.BLUE + chunkcount + ChatColor.GREEN + " chunks.");
    }

    protected void removeEntities(Chunk c) {
        for (Entity e : c.getEntities()) {
            if (e instanceof Player) {
                continue;
            } else if (e instanceof org.bukkit.entity.Painting) {
                continue;
            } else {
                e.remove();
                entcount++;
            }
        }
    }
}
