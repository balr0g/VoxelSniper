/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thevoxelbox.brush;

import com.thevoxelbox.vMessage;
import com.thevoxelbox.vSniper;

/**
 *
 * @author Gavjenks
 */
public class ChunkCoords extends Brush {
    
    public ChunkCoords() {
        name = "ChunkCoords";
    }

    @Override
    public void arrow(vSniper v) {
        int x = s.getBlockAt(tb.getX(), tb.getY(), tb.getZ()).getChunk().getX();
        int z = s.getBlockAt(tb.getX(), tb.getY(), tb.getZ()).getChunk().getZ();
        v.p.sendMessage("X value of Chunk: " + x);
        v.p.sendMessage("Z value of Chunk: " + z);
    }

    @Override
    public void powder(vSniper v) {
        arrow(v);
    }

    @Override
    public void info(vMessage vm) {
    }
}
