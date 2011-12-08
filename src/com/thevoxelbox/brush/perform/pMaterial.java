/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thevoxelbox.brush.perform;

import com.thevoxelbox.vMessage;
import com.thevoxelbox.vSniper;
import org.bukkit.block.Block;

/**
 *
 * @author Voxel
 */
public class pMaterial extends vPerformer {

    private int i;

    public pMaterial() {
        name = "Material";
    }

    @Override
    public void init(vSniper v) {
        w = v.p.getWorld();
        i = v.voxelId;
    }

    @Override
    public void info(vMessage vm) {
        vm.performerName(name);
        vm.voxel();
    }

    @Override
    public void perform(Block b) {
        if (b.getY() > 128 || b.getY() < 0) {
            return;
        }

        if (b.getTypeId() != i) {
            h.put(b);
            b.setTypeId(i);
        }
    }
}
