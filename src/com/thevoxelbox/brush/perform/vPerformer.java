/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thevoxelbox.brush.perform;

import com.thevoxelbox.vMessage;
import com.thevoxelbox.vSniper;
import com.thevoxelbox.undo.vUndo;
import org.bukkit.World;
import org.bukkit.block.Block;

/**
 *
 * @author Voxel
 */
public abstract class vPerformer {

    public String name = "Performer";
    protected vUndo h;
    protected World w;

    public abstract void info(vMessage vm);

    public abstract void init(vSniper v);

    public void setUndo(int scale) {
        h = new vUndo(w.getName(), scale);
    }

    public abstract void perform(Block b);

    public vUndo getUndo() {
        return h;
    }
}
