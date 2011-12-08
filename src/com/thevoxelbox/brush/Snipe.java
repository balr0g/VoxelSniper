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
 * @author Voxel
 */
public class Snipe extends PerformBrush {

    public Snipe() {
        name = "Snipe";
        undoScale = 1;
    }

    @Override
    public void arrow(vSniper v) {
        current.perform(tb);
        if (current.getUndo().getSize() > 0) {
            v.hashUndo.put(v.hashEn, current.getUndo());
            v.hashEn++;
        }
    }

    @Override
    public void powder(vSniper v) {
        current.perform(lb);
        if (current.getUndo().getSize() > 0) {
            v.hashUndo.put(v.hashEn, current.getUndo());
            v.hashEn++;
        }
    }

    @Override
    public void info(vMessage vm) {
        vm.brushName(name);
    }
}
