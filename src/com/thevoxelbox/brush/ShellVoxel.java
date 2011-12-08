/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thevoxelbox.brush;

import com.thevoxelbox.vMessage;
import com.thevoxelbox.vSniper;
import com.thevoxelbox.undo.vUndo;
import org.bukkit.ChatColor;
import org.bukkit.Material;

/**
 * THIS BRUSH SHOULD NOT USE PERFORMERS
 * @author Voxel
 */
public class ShellVoxel extends Brush {

    public ShellVoxel() {
        name = "Shell Voxel";
    }

    @Override
    public void arrow(vSniper v) {
        bx = tb.getX();
        by = tb.getY();
        bz = tb.getZ();
        vshell(v);
    }

    @Override
    public void powder(vSniper v) {
        bx = lb.getX();
        by = lb.getY();
        bz = lb.getZ();
        vshell(v);
    }

    @Override
    public void info(vMessage vm) {
        vm.brushName(name);
        vm.size();
        vm.voxel();
        vm.replace();

    }

    @Override
    public void parameters(String[] par, vSniper v) {
        if (par[1].equalsIgnoreCase("info")) {
            v.p.sendMessage(ChatColor.GOLD + "Shell Voxel Parameters:");
            return;
        } else {
            v.p.sendMessage(ChatColor.RED + "Invalid parameter - see the info message for help.");
        }
    }

    public void vshell(vSniper v) {
        int bsize = v.brushSize;
        int bId = v.voxelId;
        int brId = v.replaceId;
        s = s;
        int[][][] oldmats = new int[2 * (bsize + 1) + 1][2 * (bsize + 1) + 1][2 * (bsize + 1) + 1]; //Array that holds the original materials plus a buffer
        int[][][] newmats = new int[2 * bsize + 1][2 * bsize + 1][2 * bsize + 1]; //Array that holds the hollowed materials

        //Log current materials into oldmats
        for (int x = 0; x <= 2 * (bsize + 1); x++) {
            for (int y = 0; y <= 2 * (bsize + 1); y++) {
                for (int z = 0; z <= 2 * (bsize + 1); z++) {
                    oldmats[x][y][z] = getBlockIdAt(bx - bsize - 1 + x, by - bsize - 1 + y, bz - bsize - 1 + z);
                }
            }
        }

        //Log current materials into newmats
        for (int x = 0; x <= 2 * bsize; x++) {
            for (int y = 0; y <= 2 * bsize; y++) {
                for (int z = 0; z <= 2 * bsize; z++) {
                    newmats[x][y][z] = oldmats[x + 1][y + 1][z + 1];
                }
            }
        }
        int temp;

        //Hollow Brush Area
        for (int x = 0; x <= 2 * bsize; x++) {
            for (int y = 0; y <= 2 * bsize; y++) {
                for (int z = 0; z <= 2 * bsize; z++) {
                    temp = 0;

                    if (oldmats[x + 1+1][y + 1][z + 1] == brId) {
                        temp++;
                    }
                    if (oldmats[x + 1-1][y + 1][z + 1] == brId) {
                        temp++;
                    }
                    if (oldmats[x + 1][y + 1+1][z + 1] == brId) {
                        temp++;
                    }
                    if (oldmats[x + 1][y + 1-1][z + 1] == brId) {
                        temp++;
                    }
                    if (oldmats[x + 1][y + 1][z + 1+1] == brId) {
                        temp++;
                    }
                    if (oldmats[x + 1][y + 1][z + 1-1] == brId) {
                        temp++;
                    }

                    if (temp == 0) {
                        newmats[x][y][z] = bId;
                    }

                }
            }
        }

        //Make the changes
        vUndo h = new vUndo(tb.getWorld().getName());
        
        for (int x = 2 * bsize; x >= 0; x--) {
           
            for (int y = 0; y <= 2 * bsize; y++) {
               
                for (int z = 2 * bsize; z >= 0; z--) {
                   

                        if (getBlockIdAt(bx - bsize + x, by - bsize + y, bz - bsize + z) != newmats[x][y][z]) {
                            h.put(s.getBlockAt(bx - bsize + x, by - bsize + y, bz - bsize + z));
                        }
                        setBlockIdAt(newmats[x][y][z], bx - bsize + x, by - bsize + y, bz - bsize + z);
                    
                }
            }
        }
        v.hashUndo.put(v.hashEn, h);
        v.hashEn++;
        
        v.p.sendMessage(ChatColor.AQUA+"Shell complete.");
    }
}
