/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thevoxelbox.brush;

import com.thevoxelbox.vMessage;
import com.thevoxelbox.vSniper;
import net.minecraft.server.Packet51MapChunk;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.entity.CraftPlayer;

/**
 *THIS BRUSH SHOULD NOT USE PERFORMERS
 * @author giltwist
 * 
 */
public class LoadChunk extends Brush {

    @Override
    public void arrow(vSniper v) {

        dochunkload(v);


    }

    @Override
    public void powder(vSniper v) {

        arrow(v);

    }

    @Override
    public void info(vMessage vm) {
        vm.brushName("Load Chunk Brush");
        vm.custom(ChatColor.AQUA + "This brush refreshes chunks to fix chunk errors.");
    }

    public void dochunkload(vSniper v) {

        int chunkx=v.p.getWorld().getChunkAt(tb).getX();
        int chunkz=v.p.getWorld().getChunkAt(tb).getZ();
          
                   
        ((CraftPlayer) v.p).getHandle().netServerHandler.sendPacket(new Packet51MapChunk(chunkx * 16, 0, chunkz*16, 16,128,16, ((CraftWorld) v.p.getWorld()).getHandle()));

    }
}
