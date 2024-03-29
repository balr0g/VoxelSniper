/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.thevoxelbox.brush;

import com.thevoxelbox.brush.perform.PerformBrush;
import com.thevoxelbox.vMessage;
import com.thevoxelbox.vSniper;
import org.bukkit.ChatColor;

/**
 *
 * @author jmck95
 * Credit to GavJenks for framework and 95 of code.
 * Big Thank you to GavJenks
 */

public class Underlay extends PerformBrush {
    
    public Underlay(){
    name="Underlay (Reverse Overlay)";
    }
    
    @Override
    public void arrow(vSniper v){
    bx = tb.getX();
    by = tb.getY();
    bz = tb.getZ();
    Underlay(v);
    }
    
    @Override
    public void powder(vSniper v){
    bx = tb.getX();
    by = tb.getY();
    bz = tb.getZ();
    Underlaytwo(v);
    
    }
    @Override
    public void info(vMessage vm){
    vm.brushName(name);
    vm.size();
    }
    int depth = 3;
    boolean allBlocks = false;
    
    @Override
    public void parameters(String[] par, vSniper v){
    if(par[1].equalsIgnoreCase("info")){
    v.p.sendMessage(ChatColor.GOLD + "Reverse Overlay brush parameters:");
    v.p.sendMessage(ChatColor.AQUA + "d[number] (ex: d3) The number of blocks thick to change.");
    v.p.sendMessage(ChatColor.BLUE + "all (ex: /b reover all) Sets the brush to affect ALL materials");
    if(depth < 1){
        depth = 1;
    }
    return;
    }
    for(int x=1; x < par.length;x++){
    if (par[x].startsWith("d")){
        depth = Integer.parseInt(par[x].replace("d", ""));
                v.p.sendMessage(ChatColor.AQUA + "Depth set to " + depth);
              
                continue;
            } else if (par[x].startsWith("all")) {
                allBlocks = true;
                v.p.sendMessage(ChatColor.BLUE + "Will underlay over any block." + depth);
                continue;
            }else if (par[x].startsWith("some")) {
                allBlocks = false;
                v.p.sendMessage(ChatColor.BLUE + "Will underlay only natural block types." + depth);
                continue;
            }else {
                v.p.sendMessage(ChatColor.RED + "Invalid brush parameters! use the info parameter to display parameter info.");
            }
        
    }
 }

   public void Underlay (vSniper v) {
        int bsize = v.brushSize;

        int[][] memory = new int[bsize * 2 + 1][bsize * 2 + 1];
        double bpow = Math.pow(bsize + 0.5, 2);
        for (int z = bsize; z >= -bsize; z--) {
            for (int x = bsize; x >= -bsize; x--) {
                for (int y = by; y < by + depth; y++) { //start scanning from the height you clicked at
                    if (memory[x + bsize][z + bsize] != 1) { //if haven't already found the surface in this column
                        if ((Math.pow(x, 2) + Math.pow(z, 2)) <= bpow) { //if inside of the column...
                                 if (!allBlocks) { //if the override parameter has not been activated, go to the switch that filters out manmade stuff.
                                        switch (getBlockIdAt(bx + x, y, bz + z)) {
                                            case 1:
                                            case 2:
                                            case 3:
                                            case 12:
                                            case 13:
                                            //case 14: //commented out the ores, since voxelbox uses these for structural materials.
                                            //case 15:
                                            //case 16:
                                            case 24://These cases filter out any manufactured or refined blocks, any trees and leas, etc. that you don't want to mess with.
                                            case 48:
                                            case 82:
                                            case 49:
                                            case 78:
                                                for (int d = 0; (d < depth); d++) {
                                                    if (s.getBlockAt(bx + x, y + d, bz + z).getTypeId() != 0) {
                                                        current.perform(s.getBlockAt(bx + x, y + d, bz + z)); //fills down as many layers as you specify in parameters
                                                        memory[x + bsize][z + bsize] = 1; //stop it from checking any other blocks in this vertical 1x1 column.
                                                    }
                                                }
                                                break;

                                            default:
                                                break;
                                        }
                                    } else {
                                        for (int d = 0; (d < depth); d++) {
                                            if (s.getBlockAt(bx + x, y + d, bz + z).getTypeId() != 0) {
                                                current.perform(s.getBlockAt(bx + x, y + d, bz + z)); //fills down as many layers as you specify in parameters
                                                memory[x + bsize][z + bsize] = 1; //stop it from checking any other blocks in this vertical 1x1 column.
                                            }
                                        }
                                    }
                                }
                            
                        }
                    }
                }
            }
        

        v.hashUndo.put(v.hashEn, current.getUndo());
        v.hashEn++;
   }
    public void Underlaytwo (vSniper v) {
        int bsize = v.brushSize;

        int[][] memory = new int[bsize * 2 + 1][bsize * 2 + 1];
        double bpow = Math.pow(bsize + 0.5, 2);
        for (int z = bsize; z >= -bsize; z--) {
            for (int x = bsize; x >= -bsize; x--) {
                for (int y = by; y < by + depth; y++) { //start scanning from the height you clicked at
                    if (memory[x + bsize][z + bsize] != 1) { //if haven't already found the surface in this column
                        if ((Math.pow(x, 2) + Math.pow(z, 2)) <= bpow) { //if inside of the column...
                            
                                    if (!allBlocks) { //if the override parameter has not been activated, go to the switch that filters out manmade stuff.

                                        switch (getBlockIdAt(bx + x, y, bz + z)) {
                                            case 1:
                                            case 2:
                                            case 3:
                                            case 12:
                                            case 13:
                                            case 14: //These cases filter out any manufactured or refined blocks, any trees and leas, etc. that you don't want to mess with.
                                            case 15:
                                            case 16:
                                            case 24:
                                            case 48:
                                            case 82:
                                            case 49:
                                            case 78:
                                                for (int d = -1; (d < depth - 1); d++) {
                                                    current.perform(s.getBlockAt(bx + x, y - d, bz + z)); //fills down as many layers as you specify in parameters
                                                    memory[x + bsize][z + bsize] = 1; //stop it from checking any other blocks in this vertical 1x1 column.
                                                }
                                                break;

                                            default:
                                                break;
                                        }
                                    } else {
                                        for (int d = -1; (d < depth - 1); d++) {
                                            current.perform(s.getBlockAt(bx + x, y - d, bz + z)); //fills down as many layers as you specify in parameters
                                            memory[x + bsize][z + bsize] = 1; //stop it from checking any other blocks in this vertical 1x1 column.
                                        }
                                    }

                                }
                            }
                        }
                    
                }
            }
        

        v.hashUndo.put(v.hashEn, current.getUndo());
        v.hashEn++;
    }
}
