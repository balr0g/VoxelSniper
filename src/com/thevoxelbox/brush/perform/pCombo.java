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
public class pCombo extends vPerformer {

    private int i;
    private byte d;

    public pCombo() {
        name = "Combo";
    }

    @Override
    public void info(vMessage vm) {
        vm.performerName(name);
        vm.voxel();
        vm.data();
    }

    @Override
    public void init(vSniper v) {
        w = v.p.getWorld();
        i = v.voxelId;
        d = v.data;
    }

    @Override
    public void perform(Block b) {
        if (b.getY() > 128 || b.getY() < 0)
            return;
        
        h.put(b);
        b.setTypeIdAndData(i, d, true);
    }
}
