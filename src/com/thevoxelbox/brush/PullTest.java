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
 * @author Piotr
 */
public class PullTest extends SoftSelection {

    protected int vh;
    protected sBlock[] sel;

    protected void setBlock(sBlock b) {
        Block bl = s.getBlockAt(b.x, b.y + (int) (vh * b.str), b.z);
        if (getBlockIdAt(b.x, b.y - 1, b.z) == 0) {
            bl.setTypeId(b.id);
            bl.setData(b.d);
            for (int y = b.y; y < bl.getY(); y++) {
                this.setBlockIdAt(0, b.x, y, b.z);
            }
        } else {
            bl.setTypeId(b.id);
            bl.setData(b.d);
            for (int y = b.y - 1; y < bl.getY(); y++) {
                Block blo = s.getBlockAt(b.x, y, b.z);
                blo.setTypeId(b.id);
                blo.setData(b.d);
            }
        }
    }

    protected void setBlockDown(sBlock b) {
        Block bl = s.getBlockAt(b.x, b.y + (int) (vh * b.str), b.z);
//        if (getBlockIdAt(b.x, b.y - 1, b.z) == 0) {
//            bl.setTypeId(b.id);
//            bl.setData(b.d);
//            for (int y = b.y; y > bl.getY(); y--) {
//                Block blo = s.getBlockAt(b.x, y, b.z);
//                blo.setTypeId(b.id);
//                blo.setData(b.d);
//            }
//        } else {
        bl.setTypeId(b.id);
        bl.setData(b.d);
        for (int y = b.y; y > bl.getY(); y--) {
            this.setBlockIdAt(0, b.x, y, b.z);
        }
//        }
    }
    
    public PullTest() {
        name = "Soft Selection";
    }

    @Override
    public void arrow(vSniper v) {
        vh = v.voxelHeight;
        getSurface(v);

        if (vh > 0) {
            for (sBlock b : surface) {
                setBlock(b);
                //s.getBlockAt(b.x,(int) (b.y + (v.voxelHeight * b.str)), b.z).setTypeId(b.id);
            }
        } else if (vh < 0) {
            for (sBlock b : surface) {
                setBlockDown(b);
                //s.getBlockAt(b.x,(int) (b.y + (v.voxelHeight * b.str)), b.z).setTypeId(b.id);
            }
        }
    }

    @Override
    public void powder(vSniper v) {
        int bsize = v.brushSize;
        // sel = new sBlock[(int)Math.pow(((bsize*2) + 1), 3)];

        vh = v.voxelHeight;

        bx = tb.getX();
        by = tb.getY();
        bz = tb.getZ();

        s = s;
        surface.clear();

        int lasty;
        int newy;
        int laststr;
        double str;
        double bpow = Math.pow(bsize + 0.5, 2);

        int id;

        // Are we pulling up ?
        if (vh > 0) {

            // Z - Axis
            for (int z = -bsize; z <= bsize; z++) {

                int zpow = z * z;
                int zz = bz + z;

                // X - Axis
                for (int x = -bsize; x <= bsize; x++) {

                    int xpow = x * x;
                    int xx = bx + x;

                    // Down the Y - Axis
                    for (int y = bsize; y >= -bsize; y--) {

                        double pow = zpow + xpow + (y * y);

                        // Is this in the range of the brush?
                        if (pow <= bpow && s.getBlockTypeIdAt(xx, by + y, zz) != 0) {

                            int yy = by + y;

                            // Starting strength and new Position
                            str = getStr(pow / bpow);
                            laststr = (int) (vh * str);
                            lasty = yy + laststr;

                            s.getBlockAt(xx, lasty, zz).setTypeId(s.getBlockTypeIdAt(xx, yy, zz));

                            if (str == 1) {
                                str = 0.8;
                            }

                            while (laststr > 0) {
                                if (yy < by) {
                                    str = str * str;
                                }
                                laststr = (int) (vh * str);
                                newy = yy + laststr;
                                id = s.getBlockTypeIdAt(xx, yy, zz);
                                for (int i = newy; i < lasty; i++) {
                                    s.getBlockAt(xx, i, zz).setTypeId(id);
                                }
                                lasty = newy;
                                yy--;
                            }
                            break;
                        }
                    }

//                    double pow = (Math.pow(x, 2) + zpow);
//                    if (pow <= bpow) {
//
//                        int xx = bx + x;
//
//                        for (int y = max; y >= low; y--) {
//                            if (s.getBlockTypeIdAt(xx, y, zz) != 0) {
//
//                                //lasty = y + (int) (vh * getStr(pow / bpow));
//                                s.getBlockAt(xx, y + (int) (vh * getStr(pow / bpow)), zz).setTypeId(s.getBlockTypeIdAt(xx, y, zz));
//                                y--;
//
//                                while (y >= low) {
//                                    //lasty = y + (int) (vh * getStr(pow / bpow));
//                                    s.getBlockAt(xx, y + (int) (vh * getStr(pow / bpow)), zz).setTypeId(s.getBlockTypeIdAt(xx, y, zz));
//                                    y--;
//                                }
//                                break;
//                            }
//                        }
//                    }



//
//                    for (int y = bsize; y >= -bsize; y--) {
//                        double pow = (xpow + Math.pow(y, 2) + zpow);
//                        if (pow <= bpow && s.getBlockTypeIdAt(xx, by + y, zz) != 0) {
//                            int byy = by + y;
//                            lasty = byy + (int) (vh * getStr(pow / bpow));
//                            s.getBlockAt(xx, lasty, zz).setTypeId(s.getBlockTypeIdAt(xx, byy, zz));
//                            y--;
//                            pow = (xpow + Math.pow(y, 2) + zpow);
//                            while (pow <= bpow) {
//                                int blY = by + y + (int) (vh * getStr(pow / bpow));
//                                int blId = s.getBlockTypeIdAt(xx, by + y, zz);
//                                for (int i = blY; i < lasty; i++) {
//                                    s.getBlockAt(xx, i, zz).setTypeId(blId);
//                                }
//                                lasty = blY;
//                                y--;
//                                pow = (xpow + Math.pow(y, 2) + zpow);
//                            }
//                            break;
//                        }
//                    }
                }
            }
        } else {
            // double bpow = Math.pow(bsize, 2);
            for (int z = -bsize; z <= bsize; z++) {
                double zpow = Math.pow(z, 2);
                int zz = bz + z;
                for (int x = -bsize; x <= bsize; x++) {
                    double xpow = Math.pow(x, 2);
                    int xx = bx + x;
                    for (int y = -bsize; y <= bsize; y++) {
                        double pow = (xpow + Math.pow(y, 2) + zpow);
                        if (pow <= bpow && s.getBlockTypeIdAt(xx, by + y, zz) != 0) {
                            int byy = by + y;
                            //int firsty = byy + (int) (vh * getStr(pow / bpow));
                            lasty = byy + (int) (vh * getStr(pow / bpow));
                            s.getBlockAt(xx, lasty, zz).setTypeId(s.getBlockTypeIdAt(xx, byy, zz));
                            y++;
                            pow = (xpow + Math.pow(y, 2) + zpow);
                            while (pow <= bpow) {
                                int blY = by + y + (int) (vh * getStr(pow / bpow));
                                int blId = s.getBlockTypeIdAt(xx, by + y, zz);
                                for (int i = blY; i < lasty; i++) {
                                    s.getBlockAt(xx, i, zz).setTypeId(blId);
                                }
                                lasty = blY;
                                y++;
                                pow = (xpow + Math.pow(y, 2) + zpow);
                            }
                            //for(int ii = firsty + 1; ii  < )
                            break;
                        }
                    }
                }
            }
        }
    }

    @Override
    public void info(vMessage vm) {
        vm.brushName(name);
        vm.size();
        vm.height();
        vm.custom(ChatColor.AQUA + "Pinch " + (-c1 + 1));
        vm.custom(ChatColor.AQUA + "Bubble " + c2);
    }

    @Override
    public void parameters(String[] par, vSniper v) {
        try {
            double pinch = Double.parseDouble(par[1]);
            double bubble = Double.parseDouble(par[2]);
            c1 = 1 - pinch;
            c2 = bubble;
        } catch (Exception ex) {
            v.p.sendMessage(ChatColor.RED + "Invalid brush parameters!");
        }
    }
}
