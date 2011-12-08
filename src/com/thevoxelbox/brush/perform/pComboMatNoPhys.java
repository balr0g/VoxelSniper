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
public class pComboMatNoPhys extends vPerformer {

    private byte d;
    private int i;
    private int ir;

    public pComboMatNoPhys() {
        name = "Combo-Mat, No Physics";
    }

    @Override
    public void init(vSniper v) {
        w = v.p.getWorld();
        d = v.data;
        i = v.voxelId;
        ir = v.replaceId;
    }

    @Override
    public void info(vMessage vm) {
        vm.performerName(name);
        vm.voxel();
        vm.replace();
        vm.data();
        }

    @Override
    public void perform(Block b) {
        if (b.getY() > 128 || b.getY() < 0)
            return;
        
        if(b.getTypeId() == ir) {
            h.put(b);
            b.setTypeIdAndData(i, d, false);
        }
    }
}
