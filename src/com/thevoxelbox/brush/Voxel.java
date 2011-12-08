/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thevoxelbox.brush;

import com.thevoxelbox.brush.perform.PerformBrush;
import com.thevoxelbox.vMessage;
import com.thevoxelbox.vSniper;

/**
 *
 * @author Piotr
 */
public class Voxel extends PerformBrush {

    public Voxel() {
        name = "Voxel";
    }

    @Override
    public void arrow(vSniper v) {
        bx = tb.getX();
        by = tb.getY();
        bz = tb.getZ();
        voxel(v);
    }

    @Override
    public void powder(vSniper v) {
        arrow(v);
    }

    @Override
    public void info(vMessage vm) {
        vm.brushName(name);
        vm.size();
    }

    public void voxel(vSniper v) {
        int bsize = v.brushSize;

        for (int z = bsize; z >= -bsize; z--) {
            for (int x = bsize; x >= -bsize; x--) {
                for (int y = bsize; y >= -bsize; y--) {
                    current.perform(s.getBlockAt(bx + x, by + z, bz + y));
                }
            }
        }
        if (current.getUndo().getSize() > 0) {
            v.hashUndo.put(v.hashEn, current.getUndo());
            v.hashEn++;
        }
    }
}
