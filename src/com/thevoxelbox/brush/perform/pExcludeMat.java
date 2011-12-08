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
public class pExcludeMat extends vPerformer {

    private VoxelList el;
    private int i;

    public pExcludeMat() {
        name = "Exclude Material"; // This should probably be Material, Exclude Material and have the abbreviation mxm. Then you could have Combo exclude material cxm and material exclude combo mxc - Giltwist
        // I'll worry about the naming scheme when the performer works. ATM, it's very buggy... -psa
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
        el = v.voxelList;
    }

    @Override
    public void perform(Block b) {
        if (b.getY() > 128 || b.getY() < 0) {
            return;
        }

        if (el.contains(b.getTypeId())) {
            return;
        } else {
            h.put(b);
            b.setTypeId(i);
        }
    }
}
