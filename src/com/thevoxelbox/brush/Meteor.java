/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thevoxelbox.brush;

import com.thevoxelbox.vMessage;
import com.thevoxelbox.vSniper;
import org.bukkit.Location;
import net.minecraft.server.Entity;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.craftbukkit.entity.CraftFireball;
import net.minecraft.server.EntityFireball;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.bukkit.craftbukkit.CraftServer;

/**
 *THIS BRUSH SHOULD NOT USE PERFORMERS
 * @author Gavjenks
 * Heavily revamped from ruler brush by Giltwist
 */
public class Meteor extends Brush {

    protected boolean first = true;
    protected double[] origincoords = new double[3];
    protected double[] targetcoords = new double[3];
    protected int[] currentcoords = new int[3];
    protected int[] previouscoords = new int[3];
    protected double[] slopevector = new double[3];
    private Location player_loc;

    public Meteor() {
        name = "Meteor";
    }

    @Override
    public void arrow(vSniper v) {
        
        player_loc=v.p.getLocation();

        origincoords[0] = player_loc.getX();
        origincoords[1] = player_loc.getY();
        origincoords[2] = player_loc.getZ();

        targetcoords[0] = tb.getX() + .5 * tb.getX() / Math.abs(tb.getX()); //I hate you sometimes, Notch.  Really? Every quadrant is different?
        targetcoords[1] = tb.getY() + .5;
        targetcoords[2] = tb.getZ() + .5 * tb.getZ() / Math.abs(tb.getZ());

       //didn't work.  I guess I don't understand where the origin of the fireball is determined in this code.  shrug.  -Gav
       // origincoords[0] = origincoords[0] + (int)(targetcoords[0] - origincoords[0])*0.1; //attempting to make fireballs not blow up in your face anymore.  -Gav
       // origincoords[1] = origincoords[1] + (int)(targetcoords[1] - origincoords[1])*0.1;
       // origincoords[2] = origincoords[2] + (int)(targetcoords[2] - origincoords[2])*0.1;
        dofireball(v);
    }

    @Override
    public void powder(vSniper v) {

        arrow(v);

    }

    @Override
    public void info(vMessage vm) {
        vm.brushName(name);
        vm.voxel();
    }

    public void dofireball(vSniper v) {
        s = s;
        int bId = v.voxelId;
        double linelength = 0;

        //Calculate slope vector
        for (int i = 0; i < 3; i++) {
            slopevector[i] = targetcoords[i] - origincoords[i];
        }
        //Calculate line length 
        linelength = Math.pow((Math.pow(slopevector[0], 2) + Math.pow(slopevector[1], 2) + Math.pow(slopevector[2], 2)), .5);

        //Unitize slope vector
        for (int i = 0; i < 3; i++) {
            slopevector[i] = slopevector[i] / linelength;

        }

        //Hadoken!
        
          EntityFireball entityfireball = new EntityFireball(((CraftWorld) v.p.getWorld()).getHandle(), ((CraftPlayer) v.p).getHandle(),slopevector[0]*linelength,slopevector[1]*linelength,slopevector[2]*linelength);
          CraftFireball craftfireball = new CraftFireball((CraftServer) v.p.getServer(),entityfireball);
          Vector velocity = new Vector();
          velocity.setX(slopevector[0]);
          velocity.setY(slopevector[1]);
          velocity.setZ(slopevector[2]);
          craftfireball.setVelocity(velocity);
          ((CraftWorld) v.p.getWorld()).getHandle().addEntity(entityfireball);

        

    }
}
