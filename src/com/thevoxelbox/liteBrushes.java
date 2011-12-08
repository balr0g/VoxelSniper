/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.thevoxelbox;

import com.thevoxelbox.brush.*;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Voxel
 */
public enum liteBrushes {
                                                                                                                    // What is this I don't even -- DR
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~przerwap~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~  \\
    SNIPE(                              Snipe.class,                    "s",        "snipe"),                   //  [   1    ]  \\
    DISC(                               Disc.class,                     "d",        "disc"),                    //  [   2    ]  \\
    DISC_FACE(                          DiscFace.class,                 "df",       "discface"),                //  [   3    ]  \\
   // DISC_FACE_REPLACE_FILL(             DiscFaceRepFill.class,          "dfr",      "discfacereplace"),         //  [   4    ]  \\
   //DISC_REPLACE_FILL(                  DiscReplFill.class,             "dr",       "discreplace"),             //  [   5    ]  \\
    BALL(                               Ball.class,                     "b",        "ball"),                    //  [   6    ]  \\
    //BALL_REPLACE_FILL(                  BallReplFill.class,             "br",       "ballreplace"),             //  [   7    ]  \\
    VOXEL(                              Voxel.class,                    "v",        "voxel"),                   //  [   8    ]  \\
    VOXEL_DISC(                         VoxelDisc.class,                "vd",       "voxeldisc"),               //  [   9    ]  \\
    //VOXEL_DISC_REPLACE_FILL(            VoxelDiscRepFill.class,         "vdr",      "voxeldiscreplace"),        //  [   10   ]  \\
    VOXEL_DISC_FACE(                    VoxelDiscFace.class,            "vdf",      "voxeldiscface"),           //  [   11   ]  \\
    //VOXEL_DISC_FACE_REPLACE_FILL(       VoxelDiscFaceRepFill.class,     "vdfr",     "voxeldiscfacereplace"),    //  [   12   ]  \\
    //VOXEL_REPLACE_FILL(                 VoxelReplFill.class,            "vr",       "voxelreplace"),            //  [   13   ]  \\
    //INK(                                Ink.class,                      "i",        "ink"),                     //  [   14   ]  \\
    //INK_BALL(                           InkBall.class,                  "ib",       "inkball"),                 //  [   15   ]  \\
    //INK_BALL_REPLACE(                   InkBallRepl.class,              "ibr",      "inkballreplace"),          //  [   16   ]  \\
   // INK_DISC(                           InkDisc.class,                  "id",       "inkdisc"),                 //  [   17   ]  \\
   // INK_DISC_REPLACE(                   InkDiscRepl.class,              "idr",      "inkdiscreplace"),          //  [   18   ]  \\
    CLONE_STAMP(                        Clone.class,                    "cs",       "clonestamp"),              //  [   22   ]  \\
    PAINTING(                           Painting.class,                 "paint",    "painting"),                //  [   25   ]  \\
    //SNIPE_PHYSICS(                      SnipePhysics.class,             "sp",       "snipephysics"),            //  [   29   ]  \\
   // SNIPE_INK(                          SnipeInk.class,                 "is",       "snipeink"),                //  [   30   ]  \\
   // SNIPE_INK_PHYSICS(                  SnipeInkPhysics.class,          "isp",      "sniperinkphysics"),        //  [   32   ]  \\

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Giltwist~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~  \\
    BLOB(                               Blob.class,                     "blob",     "splatblob"),               //  [   5    ]  \\
    BLEND_VOXEL_DISC(                   BlendVoxelDisc.class,           "bvd",      "blendvoxeldisc"),          //  [   8    ]  \\
    BLEND_VOXEL(                        BlendVoxel.class,               "bv",      "blendvoxel"),               //  [   9    ]  \\
    BLEND_DISC(                         BlendDisc.class,                "bd",      "blenddisc"),                //  [   10   ]  \\
    BLEND_BALL(                         BlendBall.class,                "bb",      "blendball"),                //  [   11   ]  \\
    LINE(                               Line.class,                     "l",        "line"),                    //  [   12   ]  \\
    RANDOM_ERODE(                       RandomErode.class,              "re",     "randomerode"),               //  [   16   ]  \\
    LOAD_CHUNK(                         LoadChunk.class,                "lc",      "loadchunk"),                //  [   18   ]  \\

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~DivineRage~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~  \\
    GENERATE_TREE(                      TreeSnipe.class,                "t",        "treesnipe"),               //  [   2    ]  \\


    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Gavjenks~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~  \\
    DRAIN(                              Drain.class,                    "drain",    "drain"),                   //  [   10   ]  \\
    OVERLAY(                            Overlay.class,                  "over",     "overlay"),                 //  [   4    ]  \\
    RULER(                              Ruler.class,                    "r",        "ruler"),                   //  [   7    ]  \\
    VOLT_METER(                         VoltMeter.class,                "volt",     "voltmeter");               //  [   8    ]  \\
    //
    private static final Map<String, liteBrushes> brushes;
    private Class<? extends Brush> brush;
    private String short_name;
    private String long_name;

    private liteBrushes(Class<? extends Brush> b, String shortName, String longName) {
        brush = b;
        short_name = shortName;
        long_name = longName;
    }

    private Brush getBrush() {
        Brush b;
        try {
            try {
                b = brush.getConstructor().newInstance();
                return b;
            } catch (InstantiationException ex) {
                Logger.getLogger(vBrushes.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(vBrushes.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(vBrushes.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                Logger.getLogger(vBrushes.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(vBrushes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(vBrushes.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private String getShort() {
        return short_name;
    }

    private String getLong() {
        return long_name;
    }

    public static HashMap<String, Brush> getSniperBrushes() {
        HashMap<String, Brush> temp = new HashMap<String, Brush>();

        for (Entry<String, liteBrushes> set : brushes.entrySet()) {
            temp.put(set.getKey(), set.getValue().getBrush());
        }

        return temp;
    }

    public static HashMap<String, String> getBrushAlternates() {
        HashMap<String, String> temp = new HashMap<String, String>();

        for (liteBrushes vb : brushes.values()) {
            temp.put(vb.getLong(), vb.getShort());
        }

        return temp;
    }

    static {
        brushes = new HashMap();

        for (liteBrushes vb : values()) {
            brushes.put(vb.getShort(), vb);
        }
    }
}
