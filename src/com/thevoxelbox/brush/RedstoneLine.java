/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thevoxelbox.brush;

import com.thevoxelbox.vMessage;
import com.thevoxelbox.vSniper;
import com.thevoxelbox.undo.vUndo;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.Material;

/**
 *
 * @author Voxel
 */
public class RedstoneLine extends Brush {

    protected int i;
    protected Block b = null;
    protected vUndo h;
    int rd = 0;
    int rs = 15;
    int dat = 3;
    int lowx;
    int lowy;
    int lowz;
    int highx;
    int highy;
    int highz;

    public RedstoneLine() {
        name = "RedstoneLine";
    }

    @Override
    protected void arrow(vSniper v) {
        i = 55;
        if (SetRedstone(tb, v)) {
            v.p.sendMessage(ChatColor.GRAY + "Point one");
        } 
    }

    @Override
    protected void powder(vSniper v) {
        i = 55;
        if (SetRedstone(lb, v)) {
            v.p.sendMessage(ChatColor.GRAY + "Point one");
        }
    }

    @Override
    public void info(vMessage vm) {
        b = null;
        vm.brushName(name);
    }

    @Override
    public void parameters(String[] par, vSniper v) {
        if (par[1].equalsIgnoreCase("info")) {
            v.p.sendMessage(ChatColor.GOLD + "Redstone Line Parameters:");
            v.p.sendMessage(ChatColor.AQUA + "/b rline -- Places a line of redstone with a repeater every 15 blocks.");
            return;
        }
    }
    //Integer.parseInt(par[x].replace("rf", ""));
    private void setRepeaterData(Block bl) {
        if (bl.getType() == Material.DIODE_BLOCK_ON || bl.getType() == Material.DIODE_BLOCK_OFF) {
            if(highx - lowx > 0.5) {
                int dat = (b.getX() < bl.getX()) ? 1 : 3;
            } else {
                int dat = (b.getZ() < bl.getZ()) ? 0 : 2;
            }
            dat = 0;
            dat = dat + (rd * 4);
            bl.setData((byte) dat);
        }
    }

    private boolean SetRedstone(Block bl, vSniper v) {
        boolean Keep = true;
        int w = 0;
        if (b == null) {
            b = bl;
            return true;
        } else {
            lowx = (b.getX() <= bl.getX()) ? b.getX() : bl.getX();
            lowy = (b.getY() <= bl.getY()) ? b.getY() : bl.getY();
            lowz = (b.getZ() <= bl.getZ()) ? b.getZ() : bl.getZ();
            highx = (b.getX() >= bl.getX()) ? b.getX() : bl.getX();
            highy = (b.getY() >= bl.getY()) ? b.getY() : bl.getY();
            highz = (b.getZ() >= bl.getZ()) ? b.getZ() : bl.getZ();
            if((highy - lowy) > 0.5) {
                Keep = false;
                v.p.sendMessage(ChatColor.GRAY + "Error, you can only place redstone in a straight flat line.");
            }
            if((highx - lowx) > 0.5 && (highz - lowz) > 0.5) {
                Keep = false;
                v.p.sendMessage(ChatColor.GRAY + "Error, you can only place redstone in a straight flat line.");
            }
            for (int y = lowy; y <= highy; y++) {
                for (int x = lowx; x <= highx; x++) {
                    for (int z = lowz; z <= highz; z++) {
                        if(Keep) {
                            w++;
                            if((w % (rs + 1)) == rs) {
                                super.setBlockIdAt(93, x, y, z);
                                setRepeaterData(s.getBlockAt(x, y, z));
                            } else {
                                super.setBlockIdAt(55, x, y, z);
                            }
                        }
                    }
                }
            }
            b = null;
            return false;
        }
    }
}
