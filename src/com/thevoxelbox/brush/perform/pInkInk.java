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
public class pInkInk extends vPerformer {

    private byte d;
    private byte dr;


    public pInkInk() {
        name = "Ink-Ink";
    }

    @Override
    public void init(vSniper v) {
        w = v.p.getWorld();
        d = v.data;
        dr = v.replaceData;

    }

    @Override
    public void info(vMessage vm) {
        vm.performerName(name);
        vm.data();
        vm.replaceData();
    }

    @Override
    public void perform(Block b) {
        if (b.getY() > 128 || b.getY() < 0)
            return;
        
        if( b.getData() == dr) {
            h.put(b);
            b.setData(d, true);
        }
    }
}
