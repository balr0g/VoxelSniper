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
public class pMatInk extends vPerformer {

    private int i;
    private byte dr;


    public pMatInk() {
        name = "Mat-Ink";
    }

    @Override
    public void init(vSniper v) {
        w = v.p.getWorld();
        i = v.voxelId;
        dr = v.replaceData;

    }

    @Override
    public void info(vMessage vm) {
        vm.performerName(name);
        vm.voxel();
        vm.replaceData();
    }

    @Override
    public void perform(Block b) {
        if (b.getY() > 128 || b.getY() < 0)
            return;
        
        if( b.getData() == dr) {
            h.put(b);
            b.setTypeId(i, true);
        }
    }
}
