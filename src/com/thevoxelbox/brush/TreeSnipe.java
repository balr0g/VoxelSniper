package com.thevoxelbox.brush;

import com.thevoxelbox.vMessage;
import com.thevoxelbox.vSniper;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeType;

/**
 *
 * @author Mick
 */
public class TreeSnipe extends Brush {
    private TreeType treeType = TreeType.TREE;
    
    public TreeSnipe() {
        name = "Tree Snipe";
    }

    @Override
    public void arrow(vSniper v) {
        bx = tb.getX();
        by = tb.getY();
        bz = tb.getZ();
        s = s;
        by = getLocation(v);
        single(v);
    }

    @Override
    public void powder(vSniper v) {
        bx = lb.getX();
        by = lb.getY();
        bz = lb.getZ();
        single(v);
    }

    @Override
    public void info(vMessage vm) {
        vm.brushName(name);
        printTreeType(vm);
    }

    @Override
    public void parameters(String[] par, vSniper v) {
        if (par[1].equalsIgnoreCase("info")) {
            v.p.sendMessage(ChatColor.GOLD + "Tree snipe brush:");
            v.p.sendMessage(ChatColor.AQUA + "/b ts treetype");
            printTreeType(v.vm);
            return;
        }
        for (int x = 1; x < par.length; x++) {
            if (par[x].equals("bigtree")) {
                treeType = TreeType.BIG_TREE;
                printTreeType(v.vm);
                break;
            }
            if (par[x].equals("birch")) {
                treeType = TreeType.BIRCH;
                printTreeType(v.vm);
                break;
            }
            if (par[x].equals("redwood")) {
                treeType = TreeType.REDWOOD;
                printTreeType(v.vm);
                break;
            }
            if (par[x].equals("tallredwood")) {
                treeType = TreeType.TALL_REDWOOD;
                printTreeType(v.vm);
                break;
            }
            if (par[x].equals("tree")) {
                treeType = TreeType.TREE;
                printTreeType(v.vm);
                break;
            }
        }

    }

    public void single(vSniper v) {
        try {
            s.generateTree(new Location(s, (double) bx, (double) by, (double) bz), treeType);
        }
        catch (Exception e) {
            v.p.sendMessage("Nope");
        }
    }

    private int getLocation(vSniper v) {
        for (int i = 1; i < (127 - by); i++) {
            if (s.getBlockAt(bx, by+i, bz).getType() == Material.AIR) { // Dont you mean != AIR ?  -- prz
                return by+i;                                            // But why are you even grabbing the highest Y ?
            }
        }
        return by;
    }
    
    private void printTreeType(vMessage vm) {
        switch (treeType) {
            case BIG_TREE:
                vm.custom(ChatColor.GRAY + "bigtree " + ChatColor.DARK_GRAY + "birch " + "redwood " + "tallredwood " + "tree");
                break;

            case BIRCH:
                vm.custom(ChatColor.DARK_GRAY + "bigtree " + ChatColor.GRAY + "birch " + ChatColor.DARK_GRAY + "redwood " + "tallredwood " + "tree");
                break;

            case REDWOOD:
                vm.custom(ChatColor.DARK_GRAY + "bigtree " + "birch " + ChatColor.GRAY + "redwood " + ChatColor.DARK_GRAY + "tallredwood " + "tree");
                break;

            case TALL_REDWOOD:
                vm.custom(ChatColor.DARK_GRAY + "bigtree " + "birch " + "redwood " + ChatColor.GRAY + "tallredwood " + ChatColor.DARK_GRAY + "tree");
                break;

            case TREE:
                vm.custom(ChatColor.DARK_GRAY + "bigtree " + "birch " + "redwood " + "tallredwood " + ChatColor.GRAY + "tree");
                break;
        }
    }
}
