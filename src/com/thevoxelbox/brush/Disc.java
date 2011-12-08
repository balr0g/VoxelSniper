/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thevoxelbox.brush;

import com.thevoxelbox.brush.perform.PerformBrush;
import com.thevoxelbox.vMessage;
import com.thevoxelbox.undo.vUndo;
import com.thevoxelbox.vSniper;
import org.bukkit.ChatColor;
import org.bukkit.Material;

/**
 *
 * @author Voxel
 */
public class Disc extends PerformBrush {

    public Disc() {
        name = "Disc";
    }

    @Override
    public void arrow(vSniper v) {
        bx = tb.getX();
        by = tb.getY();
        bz = tb.getZ();
        disc(v);
    }

    @Override
    public void powder(vSniper v) {
        bx = lb.getX();
        by = lb.getY();
        bz = lb.getZ();
        disc(v);
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
            v.p.sendMessage(ChatColor.GOLD + "Disc Brush Parameters:");
            v.p.sendMessage(ChatColor.AQUA + "/b d true -- will use a true circle algorithm instead of the skinnier version with classic sniper nubs. /b b false will switch back. (false is default)");
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

    public void disc(vSniper v) {
        int bsize = v.brushSize;
        double bpow = Math.pow(bsize + trueCircle, 2);
        for (int x = bsize; x >= 0; x--) {
            double xpow = Math.pow(x, 2);
            for (int y = bsize; y >= 0; y--) {
                if ((xpow + Math.pow(y, 2)) <= bpow) {
                    current.perform(s.getBlockAt(bx + x, by, bz + y));
                    current.perform(s.getBlockAt(bx + x, by, bz - y));
                    current.perform(s.getBlockAt(bx - x, by, bz + y));
                    current.perform(s.getBlockAt(bx - x, by, bz - y));
                }
            }
        }

        if (current.getUndo().getSize() > 0) {
            v.hashUndo.put(v.hashEn, current.getUndo());
            v.hashEn++;
        }
    }
}
