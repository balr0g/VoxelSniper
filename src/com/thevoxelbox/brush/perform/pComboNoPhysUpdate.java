/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thevoxelbox.brush.perform;

import com.thevoxelbox.vMessage;
import com.thevoxelbox.vSniper;
import com.thevoxelbox.undo.vUndo;
import org.bukkit.block.Block;
import org.bukkit.ChatColor;

import org.bukkit.craftbukkit.entity.CraftPlayer;
import net.minecraft.server.Packet53BlockChange;
import org.bukkit.craftbukkit.CraftWorld;

/**
 *
 * @author Voxel
 */
public class pComboNoPhysUpdate extends vPerformer {

    private int i;
    private byte d;
    private vSniper s;

    public pComboNoPhysUpdate() {
        name = "Combo-update-nophys";
    }

    @Override
    public void init(vSniper v) {
        w = v.p.getWorld();
        i = v.voxelId;
        d = v.data;
        s = v;
    }

    @Override
    public void info(vMessage vm) {
        vm.performerName(name);
        vm.custom(ChatColor.RED + "USE WITH CAUTION");
        vm.voxel();
        vm.data();
    }

    @Override
    public void perform(Block b) {
        if (b.getY() > 128 || b.getY() < 0)
            return;
        
        if (b.getTypeId() != i) {
            h.put(b);
            b.setTypeIdAndData(i, d, false);
            ((CraftPlayer) s.p).getHandle().netServerHandler.sendPacket(new Packet53BlockChange(b.getX(), b.getY(), b.getZ(), ((CraftWorld) s.p.getWorld()).getHandle()));
        }
    }
}
