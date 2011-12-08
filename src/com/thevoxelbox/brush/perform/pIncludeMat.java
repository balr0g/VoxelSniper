/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thevoxelbox.brush.perform;

import com.thevoxelbox.stuff.VoxelList;
import com.thevoxelbox.vMessage;
import com.thevoxelbox.vSniper;
import org.bukkit.block.Block;

/**
 *
 * @author Voxel
 */
public class pIncludeMat extends vPerformer {

    private VoxelList il;
    private int i;

    public pIncludeMat() {
        name = "Include Mat";
    }

    @Override
    public void info(vMessage vm) {
        vm.performerName(name);
        vm.voxelList();
        vm.voxel();
        vm.data();
    }

    @Override
    public void init(vSniper v) {
        w = v.p.getWorld();
        i = v.voxelId;
        il = v.voxelList;
    }

    @Override
    public void perform(Block b) {
        if (b.getY() > 128 || b.getY() < 0) {
            return;
        }

        if (il.contains(b.getTypeId())) {
            h.put(b);
            b.setTypeId(i);
        } else {
            return;
        }
    }
}
