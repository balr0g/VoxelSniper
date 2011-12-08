/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thevoxelbox.brush;

import com.thevoxelbox.vMessage;
import org.bukkit.block.Block;

/**
 *
 * @author Voxel
 */
public class Pinch extends Brush {

    private boolean[][][] area;

    public Pinch() {
        name = "Pinch";
    }

    @Override
    public void info(vMessage vm) {
        vm.brushName(name);
    }


    


    private class pBlock {

        public Block b;
        public int i;
        public byte d;

        public pBlock(Block bl) {
            b = bl;
            i = bl.getTypeId();
            d = bl.getData();
        }
    }
}
