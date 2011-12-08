///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//
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
//public class pInkRepl extends vPerformer{
//
//    private byte d;
//    private byte dr;
//    private int i;
//    private int ir;
//
//    public pInkRepl() {
//        name = "Ink Replace";
//    }
//
//    @Override
//    void init(vSniper v) {
//        w = v.s;
//        h = new vUndo(w.getName());
//        d = v.data;
//        dr = v.replaceData;
//        i = v.voxelId;
//        ir = v.replaceId;
//    }
//
//    @Override
//    void arrow(Block b) {
//        if (b.getTypeId() == ir && b.getData() == dr) {
//            h.put(b);
//            b.setTypeIdAndData(i, d, true);
//        }
//    }
//
//    @Override
//    void powder(Block b) {
//        if (b.getData() == dr) {
//            h.put(b);
//            b.setData(d);
//        }
//    }
//
//    @Override
//    void info(vMessage vm) {
//        vm.voxel();
//        vm.replace();
//        vm.data();
//        vm.replaceData();
//    }
//}
