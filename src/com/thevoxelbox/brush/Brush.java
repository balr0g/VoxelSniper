/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thevoxelbox.brush;

import com.thevoxelbox.HitBlox;
import com.thevoxelbox.brush.perform.PerformBrush;
import com.thevoxelbox.undo.vBlock;
import com.thevoxelbox.vMessage;
import com.thevoxelbox.vSniper;
import org.bukkit.event.block.Action;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

/**
 * The abstract class Brush
 * Base of all the brushes 
 *
 * @author Piotr
 */
public abstract class Brush {

    
    /**
     * Pointer to the world the current action is being executed
     */
    protected World s;
    /**
     * Targeted reference point X
     */
    protected int bx;
    /**
     * Targeted reference point Y
     */
    protected int by;
    /**
     * Targeted reference point Z
     */
    protected int bz;
    /**
     * Brush's Target Block
     * Derived from getTarget()
     */
    protected Block tb;
    
    /**
     * Brush's Target 'Last' Block
     * Block at the face of the block clicked
     * ColDerived from getTarget()
     */
    protected Block lb;
    /**
     * Brush's private name.
     */
    public String name = "Undefined";
    protected int undoScale = 1000;

    /**
     *
     * @param v
     */
    protected void setBlock(vBlock v) {
        s.getBlockAt(v.x, v.y, v.z).setTypeId(v.id);
    }

    /**
     * Sets the Id of the block at the passed coordinate
     *
     * @param t The id the block will be set to
     * @param ax X coordinate
     * @param ay Y coordinate
     * @param az Z coordinate
     */
    protected void setBlockIdAt(int t, int ax, int ay, int az) {
        s.getBlockAt(ax, ay, az).setTypeId(t);
    }

    /**
     * Returns the block at the passed coordinates
     *
     * @param ax X coordinate
     * @param ay Y coordinate
     * @param az Z coordinate
     * @return
     */
    protected int getBlockIdAt(int ax, int ay, int az) {
        return s.getBlockAt(ax, ay, az).getTypeId();
    }

    /**
     * The arrow action. Executed when a player RightClicks with an Arrow
     *
     * @param v vSniper caller
     */
    protected void arrow(vSniper v) {
    }

    /**
     * The powder action. Executed when a player RightClicks with Gunpowder
     *
     * @param v vSniper caller
     */
    protected void powder(vSniper v) {
    }

    /**
     *
     * @param vm
     */
    public abstract void info(vMessage vm);

    public void updateScale() {
    }

    /**
     *
     * @param action
     * @param v
     * @param heldItem
     * @param clickedBlock
     * @param clickedFace
     */
    public boolean perform(Action action, vSniper v, Material heldItem, Block clickedBlock, BlockFace clickedFace) {
        switch (action) {
            case RIGHT_CLICK_AIR:
            case RIGHT_CLICK_BLOCK:
                switch (heldItem) {
                    case ARROW:
                        if (getTarget(v, clickedBlock, clickedFace)) {
                            updateScale();
                            if (this instanceof PerformBrush) {
                                ((PerformBrush) this).initP(v);
                            }
                            arrow(v);
                            return true;
                        }
                        break;

                    case SULPHUR:
                        if (getTarget(v, clickedBlock, clickedFace)) {
                            updateScale();
                            if (this instanceof PerformBrush) {
                                ((PerformBrush) this).initP(v);
                            }
                            powder(v);
                            return true;
                        }
                        break;

                    default:
                        return false;
                }
                break;

            case LEFT_CLICK_AIR:

                break;

            case LEFT_CLICK_BLOCK:

                break;

            case PHYSICAL:
                break;

            default:
                v.p.sendMessage(ChatColor.RED + "Something is not right. Report this to przerwap. (Perform Error)");
                return true;
        }
        return false;
    }

    /**
     * Overridable getTarget method
     *
     * @param v
     * @param clickedBlock
     * @param clickedFace
     * @return
     */
    protected boolean getTarget(vSniper v, Block clickedBlock, BlockFace clickedFace) {
        s = v.p.getWorld();
        if (clickedBlock != null) {
            tb = clickedBlock;
            lb = clickedBlock.getRelative(clickedFace);
            if (lb == null) {
                v.p.sendMessage(ChatColor.RED + "You clicked outside of your sniping range.");
                return false;
            }
            if (v.lightning) {
                s.strikeLightning(tb.getLocation());
            }
            return true;
        } else {
            HitBlox hb = null;
            if (v.distRestrict) {
                hb = new HitBlox(v.p, s, v.range);
                tb = hb.getRangeBlock();
            } else {
                hb = new HitBlox(v.p, s);
                tb = hb.getTargetBlock();
            }
            if (tb != null) {
                lb = hb.getLastBlock();
                if (lb == null) {
                    v.p.sendMessage(ChatColor.RED + "You clicked outside of your sniping range.");
                    return false;
                }
                if (v.lightning) {
                    s.strikeLightning(tb.getLocation());
                }
                return true;
            } else {
                v.p.sendMessage(ChatColor.RED + "You clicked outside of your sniping range.");
                return false;
            }
        }
    }

    /**
     * A Brush's custom command handler
     *
     * @param par Array of string containing parameters
     * @param v vSniper caller
     */
    public void parameters(String[] par, vSniper v) {
        v.p.sendMessage(ChatColor.DARK_GREEN + "This brush doesn't take any extra parameters.");
    }
}
