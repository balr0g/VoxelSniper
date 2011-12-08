/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thevoxelbox.brush;

import com.thevoxelbox.brush.perform.PerformBrush;
import com.thevoxelbox.vMessage;
import com.thevoxelbox.vSniper;
import com.thevoxelbox.undo.vUndo;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;

/**
 *
 * @author Voxel
 */
public class FillDown extends PerformBrush {

    private int i;
    private int bsize;
    private vUndo h;

    public FillDown() {
        name = "Fill Down";
    }

    @Override
    protected void arrow(vSniper v) {
        i = v.voxelId;
        bsize = v.brushSize;
        fillDown(tb);
        v.hashUndo.put(v.hashEn, h);
        v.hashEn++;
    }

    @Override
    protected void powder(vSniper v) {
        i = v.voxelId;
        bsize = v.brushSize;
        fillDown(lb);
        v.hashUndo.put(v.hashEn, h);
        v.hashEn++;
    }

    @Override
    public void info(vMessage vm) {
        vm.brushName(name);
        vm.size();
        //vm.voxel();
    }
    double trueCircle = 0;

    @Override
    public void parameters(String[] par, vSniper v) {
        if (par[1].equalsIgnoreCase("info")) {
            v.p.sendMessage(ChatColor.GOLD + "Fill Down Parameters:");
            v.p.sendMessage(ChatColor.AQUA + "/b fd true -- will use a true circle algorithm instead of the skinnier version with classic sniper nubs. /b b false will switch back. (false is default)");
            return;
        }
        for (int x = 1; x < par.length; x++) {
            if (par[x].startsWith("true")) {
                trueCircle = 0.5;
                v.p.sendMessage(ChatColor.AQUA + "True circle mode ON.");
                continue;
            } else if (par[x].startsWith("false")) {
                trueCircle = 0;
                v.p.sendMessage(ChatColor.AQUA + "True circle mode OFF.");
                continue;
            } else {
                v.p.sendMessage(ChatColor.RED + "Invalid brush parameters! use the info parameter to display parameter info.");
            }
        }
    }

    private void fillDown(Block b) {
        h = new vUndo(b.getWorld().getName());

        bx = b.getX();
        by = b.getY();
        bz = b.getZ();

        double bpow = Math.pow(bsize + trueCircle, 2);
        for (int x = 0 - bsize; x <= bsize; x++) {
            double xpow = Math.pow(x, 2);
            for (int z = 0 - bsize; z <= bsize; z++) {
                if (xpow + Math.pow(z, 2) <= bpow) {
                    if (s.getBlockTypeIdAt(bx + x, by, bz + z) == 0) { //why is this if statement here?  You don't want to fill anything in the whole column if there is a single block at the level of your disc?  Are you sure? -gavjenks
                        int y = by;
                        while (--y >= 0) {
                            if (s.getBlockTypeIdAt(bx + x, y, bz + z) != 0) {
                                break;
                            }
                        }
                        for (int yy = y; yy <= by; yy++) {
                            Block bl = s.getBlockAt(bx + x, yy, bz + z);
                            h.put(bl);
                            current.perform(bl);
                        }
                    }
                }
            }
        }
    }
}
