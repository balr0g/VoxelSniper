package com.thevoxelbox.brush;

import com.thevoxelbox.vMessage;
import com.thevoxelbox.vSniper;
import com.thevoxelbox.undo.vUndo;
import org.bukkit.ChatColor;
import org.bukkit.Material;

/**
 *
 * @author Mick
 */
public class Flatten extends Brush {
    double trueCircle = 0;
    private float falloff = 0.5f;
    private float smooth = 0.5f;
    private int vh = 10;
    
    public Flatten() {
        name = "Flatten";
    }

    @Override
    public void arrow(vSniper v) {
        bx = tb.getX();
        by = tb.getY();
        bz = tb.getZ();
        vh = v.voxelHeight;
        s = s;

        flatten(v);
    }

    @Override
    public void powder(vSniper v) {
        bx = lb.getX();
        by = lb.getY();
        bz = lb.getZ();
        flatten(v);
    }

    @Override
    public void info(vMessage vm) {
        vm.brushName(name);
        vm.size();
        vm.custom(ChatColor.GOLD + "Brush falloff set to " + falloff);
    }

    @Override
    public void parameters(String[] par, vSniper v) {
        if (par[1].equalsIgnoreCase("info")) {
            v.p.sendMessage(ChatColor.GOLD + "Flatten!");
            v.p.sendMessage(ChatColor.GOLD + "/b f f# - Set brush falloff. Between 0 and 1.");
            v.p.sendMessage(ChatColor.GOLD + "/b f s# - Set brush falloff smoothness. Between 0 and 1.");
            return;
        }
    }

    public void flatten(vSniper v) {
        int bsize = v.brushSize;
        int bId = v.voxelId;

        // Store all values in the affected cylinder.
        vUndo h = new vUndo(tb.getWorld().getName());

        double bpow = Math.pow(bsize + trueCircle, 2);
        for (int x = bsize; x >= 0; x--) {                                          // x
            double xpow = Math.pow(x, 2);
            for (int z = bsize; z >= 0; z--) {                                      // z
                for (int y = getHeight(); y >= 0; y--) {                            // Height (1 + (2 * vh))
                    if ((xpow + Math.pow(z, 2)) <= bpow) {
                        h.put(s.getBlockAt(bx + x, y, bz + z));
                        h.put(s.getBlockAt(bx + x, y, bz - z));
                        h.put(s.getBlockAt(bx - x, y, bz + z));
                        h.put(s.getBlockAt(bx - x, y, bz - z));
                    }
                }
            }
        }
        v.hashUndo.put(v.hashEn, h);
        v.hashEn++;

        // center
        //  nothing
        // falloff      P1
        //  -->         C
        // radius       P2

        // A = (1-t)*P1 + t*C
        // B = (1-t)*C + t*P2
        // P = (1-t)*A + t*B

        double falloffThreshold = bsize * falloff;
        double smoothThreshold = bsize - (bsize - falloffThreshold) * smooth;
        double curStr;
        double curT;
        int firstAirHeight;

        for (int x = bsize; x >= 0; x--) {                                          // x
            double xpow = Math.pow(x, 2);
            for (int z = bsize; z >= 0; z--) {                                      // z
                double ypow = Math.pow(z, 2);
                double xypow = xpow + ypow;
                if (xypow <= bpow) {
                    // Current block is inside the cylinder
                    if (xypow <= falloffThreshold) {
                        // inside threshold. Always level with target block
                    }
                    else {
                        // not inside threshold. getStr and stuff
                        firstAirHeight = getLocation();
                        if (firstAirHeight > (by+vh) || firstAirHeight == -1) {
                            // Ignore this column, it's height is above the /vh height.
                            continue;
                        }
                        else {
                            curT = Math.sqrt(xypow - falloffThreshold);
                            curStr = getStr(smooth, curT);
                            this.setBlockIdAt(this.getBlockIdAt(x, firstAirHeight-1, z), x, (int) (by + (vh * curStr)), z);
                            for (int i = 1; i < firstAirHeight-1; i++) {
                                this.setBlockIdAt(0, x, by+i, z);
                            }
                        }
                    }
                }
            }
        }
    }

    private double getStr(double C, double t) {
        return 2*(1-t)*t*C + (t*t)*vh;
    }

    private int getLocation() {
        for (int i = 1; i < (127 - by); i++) {
            if (s.getBlockAt(bx, by+i, bz).getType() == Material.AIR) { 
                return by+i;
            }
        }
        return -1;
    }


    private int getHeight() {
        int height = by + (2*vh);

        if ((by - vh) < 0) {
            height -= (vh - by);
        }
        if ((by + vh) > 127) {
            height -= (127 - (vh + by));
        }


        return height;
    }

    private int clamp(int value, int min, int max) {
        if (value < min) {
            return min;
        }
        else if (value > max) {
            return max;
        }
        return value;
    }
}