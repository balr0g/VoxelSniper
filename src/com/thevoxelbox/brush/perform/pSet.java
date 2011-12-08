///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.thevoxelbox.brush.perform;
//
//import com.thevoxelbox.vMessage;
//import com.thevoxelbox.vSniper;
//import com.thevoxelbox.vUndo;
//import org.bukkit.block.Block;
//
///**
// *
// * @author Voxel
// */
//public class pSet extends vPerformer {
//
//    private int i;
//
//    public pSet() {
//        name = "Set";
//    }
//
//    @Override
//    void init(vSniper v) {
//        w = v.s;
//        h = new vUndo(w.getName());
//        i = v.voxelId;
//    }
//
//    @Override
//    void arrow(Block b) {
//        if (b.getTypeId() != i) {
//            h.put(b);
//            b.setTypeId(i);
//        }
//    }
//
//    @Override
//    void powder(Block b) {
//        if (b.getTypeId() != i) {
//            h.put(b);
//            b.setTypeId(i);
//        }
//    }
//
//    @Override
//    void info(vMessage vm) {
//        vm.voxel();
//    }
//}
