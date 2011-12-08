/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thevoxelbox.brush;

import com.thevoxelbox.vMessage;
import com.thevoxelbox.vSniper;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;

/**
 *
 * @author psanker
 */
public class Lathe extends Spline {

    private Block center;
    private int d;
    
    private boolean fill;
    
    public Lathe() {
        name = "Lathe";
    }
    
    @Override
    public void arrow(vSniper v) {
        bx = tb.getX();
        by = tb.getY();
        bz = tb.getZ();
    }
    
    @Override
    public void powder (vSniper v) {
        bx = tb.getX();
        by = tb.getY();
        bz = tb.getZ();
        
        if (set)
            addToSet(v, false);
        if (ctrl)
            addToSet(v, true);
    }
    
    @Override
    public void parameters(String[] par, vSniper v) {
        if (par[1].equalsIgnoreCase("info")) {
            v.p.sendMessage(ChatColor.GOLD+"Lathe brush parameters");
            v.p.sendMessage(ChatColor.AQUA+"ss: Enable endpoint selection mode for desired curve");
            v.p.sendMessage(ChatColor.AQUA+"sc: Enable control point selection mode for desired curve");
            v.p.sendMessage(ChatColor.AQUA+"clear: Clear out the curve selection");
            v.p.sendMessage(ChatColor.AQUA+"fill: Toggle interior fill mode");
            v.p.sendMessage(ChatColor.AQUA+"ren: Lathe the spline");
            return;
        }
        
        for (int i = 1; i < par.length; i++) {
            for (String str : sparams) {
                if (str.equals(par[i]))
                    super.parameters(new String[] {par[i]}, v);
            }
            
            if (par[i].equalsIgnoreCase("fill")) {
                if (!fill) {
                    fill = true;
                    v.p.sendMessage(ChatColor.AQUA + "Fill mode enabled.");
                    continue;
                } else {
                    fill = false;
                    v.p.sendMessage(ChatColor.AQUA + "Fill mode disabled.");
                    continue;
                }
            } else if (par[i].equalsIgnoreCase("ren")) {
//                if (spline(new Point(endPts.get(0)), new Point(endPts.get(1)), new Point(ctrlPts.get(0)), new Point(ctrlPts.get(1)), v))
                    this.render(v);
            } else {
                v.p.sendMessage(ChatColor.RED + "Invalid brush parameters! use the info parameter to display parameter info.");
            }
        }
    }
    
    @Override
    public void info(vMessage vm) {
        vm.brushName(name);
        if (fill)
            vm.custom(ChatColor.AQUA+"Fill mode is enabled");
        else
            vm.custom(ChatColor.AQUA+"Fill mode is disabled");
        
        //-----------------------------------------------------
        
        if (set)
            vm.custom(ChatColor.GRAY + "Endpoint selection mode ENABLED.");
        else if (ctrl)
            vm.custom(ChatColor.GRAY + "Control point selection mode disabled.");
        else
            vm.custom(ChatColor.AQUA + "No selection mode enabled.");
    }
    
    @Override
    protected void render(vSniper v) {
        v.p.sendMessage("Not ready yet!");
    }
    
    public void latheZ(vSniper v) {
        
    }
    
    public void latheX(vSniper v) {
    
    }
    
    public void latheY(vSniper v) {
    
    }
}
