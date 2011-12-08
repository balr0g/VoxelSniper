/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thevoxelbox.brush.perform;

import com.thevoxelbox.vMessage;
import com.thevoxelbox.vSniper;
import com.thevoxelbox.undo.vUndo;
import org.bukkit.block.Block;

/**
 *
 * @author Voxel
 */
public class pMatCombo extends vPerformer {

    private byte dr;
    private int i;
    private int ir;

    public pMatCombo() {
        name = "Mat-Combo";
    }

    @Override
    public void init(vSniper v) {
        w = v.p.getWorld();
        dr = v.replaceData;
        i = v.voxelId;
        ir = v.replaceId;
    }

    @Override
    public void info(vMessage vm) {
        vm.performerName(name);
        vm.voxel();
        vm.replace();
        vm.replaceData();
    }

    @Override
    public void perform(Block b) {
        if (b.getY() > 128 || b.getY() < 0)
            return;
        
        if(b.getTypeId() == ir && b.getData() == dr) {
            h.put(b);
            b.setTypeId(i, true);
        }
    }
}
