package com.thevoxelbox;

import com.thevoxelbox.undo.vUndo;
import java.util.HashMap;

import org.bukkit.block.Block;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.thevoxelbox.brush.Brush;
import com.thevoxelbox.brush.Sneak;
import com.thevoxelbox.brush.Snipe;
import com.thevoxelbox.brush.perform.Performer;
import com.thevoxelbox.stuff.VoxelList;
import java.util.logging.Level;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.event.block.Action;

/**
 * VoxelSniper's core class
 *
 * vSniper holds a Sniper's data and provides methods for the brushes to work properly
 *
 * @author Voxel
 */
public class vSniper {

//    /**
//     * World pointer to the last World a snipe occurred in
//     */
//    @Deprecated
//    public World s;
    /**
     * Player pointer
     * Who does this class belong to?
     */
    public Player p;
    // 
    public vMessage vm;
    /**
     * Sniper's brush size
     */
    public int brushSize;  // Brush size  --  set by /b #
    /**
     * Sniper's voxel ID
     */
    public int voxelId;     // Voxel Id   --   set by /v (#,name)
    /**
     * Sniper's voxel replace ID
     */
    public int replaceId;   // Voxel Replace Id   --   set by /vr #
    /**
     * Sniper's voxel ink data
     */
    public byte data;   // Voxel 'ink'  --   set by /vi #
    /**
     * Sniper's voxel ink replace data
     */
    public byte replaceData; // Voxel 'ink' Replace -- set bt /vir #
    /**
     * Sniper's block exclusion list for the Exclude performer
     */
    public VoxelList voxelList = new VoxelList();
    //public ArrayList<Integer> voxelList = new ArrayList<Integer>(); // Voxel Exclusion List -- set by /ve #
    /**
     * Sniper's voxel height
     */
    public int voxelHeight;    // Voxel 'heigth'   --  set by /vh #
    //
    //
    public boolean lightning = false;
    public boolean printout = true; //if false, will suppress many types of common, spammy vmessages. -Gav
    public boolean distRestrict = false;
    public double range = 5.0D;
    //
    //
    /**
     * Sniper's centroid
     *
     * TODO: Move to Clone.java
     */
    public int cCen;
    //
    //
    /**
     * Holds the index of the last entry of the vUndo
     */
    public int hashEn = 0;
    /**
     * Map of vUndo's
     */
    public HashMap<Integer, vUndo> hashUndo = new HashMap<Integer, vUndo>();
    //
    //
    protected HashMap<String, Brush> myBrushes;
    protected HashMap<String, String> brushAlt;
    protected HashMap<Integer, Brush> brushPresets = new HashMap();
    protected HashMap<Integer, int[]> brushPresetsParams = new HashMap();
    protected HashMap<String, Brush> brushPresetsS = new HashMap();
    protected HashMap<String, int[]> brushPresetsParamsS = new HashMap();
    //activated
    private Brush current = new Snipe();
    private Brush previous = new Snipe();
    private Brush twoBack = new Snipe();
    private int[] currentP = new int[6];
    //private int[] previousP = new int[6];
    //private int[] twoBackP = new int[6];
    private Brush sneak = new Sneak();

    /**
     * Default constructor, gathers the brushes for the Sniper's use
     */
    public vSniper() {
        myBrushes = vBrushes.getSniperBrushes();
        brushAlt = vBrushes.getBrushAlternates();
        vm = new vMessage(this);
        //defaults
        currentP[0] = 0;
        currentP[1] = 0;
        currentP[2] = 0;
        currentP[3] = 3;
        currentP[4] = 1;
        currentP[5] = 0;
        brushPresetsParamsS.put("current@", currentP);
        brushPresetsParamsS.put("previous@", currentP);
        brushPresetsParamsS.put("twoBack@", currentP);
    }

    public void setBrushSize(int size) {
        brushSize = size;
        vm.size();
    }

    public void setVoxel(int voxel) {
        voxelId = voxel;
        vm.voxel();
    }

    public void setReplace(int replace) {
        replaceId = replace;
        vm.replace();
    }

    public void setData(byte dat) {
        data = dat;
        vm.data();
    }

    public void setReplaceData(byte dat) {
        replaceData = dat;
        vm.replaceData();
    }

    public void addVoxelToList(int i) {
        voxelList.add(i);
        vm.voxelList();
    }

    public void removeVoxelFromList(int i) {
        voxelList.removeValue(i);
        vm.voxelList();
    }

    public void clearVoxelList() {
        voxelList.clear();
        vm.voxelList();
    }

    public void setHeigth(int heigth) {
        voxelHeight = heigth;
        vm.height();
    }

    public void setCentroid(int centroid) {
        cCen = centroid;
        vm.center();
    }

    public void setRange(double rng) {
        if (rng > -1) {
            range = rng;
            distRestrict = true;
            vm.toggleRange();
        } else {
            distRestrict = !distRestrict;
            vm.toggleRange();
        }
    }

    public void toggleLightning() {
        lightning = !lightning;
        vm.toggleLightning();
    }

    public void togglePrintout() {
        printout = !printout;
        vm.togglePrintout();
    }

    public void setPerformer(String[] args) {
        String[] derp = new String[args.length + 1];
        derp[0] = "";
        System.arraycopy(args, 0, derp, 1, args.length);
        if (current instanceof Performer) {
            ((Performer) current).parse(derp, this);
        } else {
            vm.custom(ChatColor.GOLD + "This brush is not a Performer brush!");
        }
    }

    /**
     * The set brush method
     *
     *
     * @param args String array of arguments where args[0] is the name of the brush being set
     * @return true if brush exists
     */
    public boolean setBrush(String[] args) {


        try {
            //if (args == null) {
            //    p.sendMessage(ChatColor.RED + "Invalid input!");  //This was spamming the console and people were complaining.  Fixed below with a catch instead.  -GJ
            //    return false;
            //}
            if (myBrushes.containsKey(args[0])) {
                //parameters:
                brushPresetsParamsS.put("twoBack@", brushPresetsParamsS.get("previous@"));
                fillPrevious(); //there are no current parameters yet, you just declared the brush / haven't input your new params.

                twoBack = previous;
                previous = current;
                current = myBrushes.get(args[0]);

            } else if (brushAlt.containsKey(args[0])) {
                //parameters:
                brushPresetsParamsS.put("twoBack@", brushPresetsParamsS.get("previous@"));
                fillPrevious();

                twoBack = previous;
                previous = current;
                current = myBrushes.get(brushAlt.get(args[0]));

            } else {
                p.sendMessage(ChatColor.LIGHT_PURPLE + "No such brush.");
                return false;
            }

            args = parseParams(args);

            if (args.length > 1) {
                try {
                    if (current instanceof Performer) {
                        ((Performer) current).parse(args, this);
                    } else {
                        current.parameters(args, this);
                    }
                    return true;
                } catch (Exception e) {
                    p.sendMessage(ChatColor.RED + "Invalid parameters! (Parameter error)");
                    p.sendMessage(ChatColor.DARK_PURPLE + "" + fromArgs(args));
                    p.sendMessage(ChatColor.RED + "Is not a valid statement");
                    p.sendMessage(ChatColor.DARK_BLUE + "" + e.getMessage());
                    VoxelSniper.log.warning("[VoxelSniper] Exception while receiving parameters: \n(" + p.getName() + " " + current.name + ") par[ " + fromArgs(args) + "]");
                    VoxelSniper.log.log(Level.SEVERE, null, e);
                    return false;
                }
            }
            info();
            return true;
        } catch (ArrayIndexOutOfBoundsException e) {
            p.sendMessage(ChatColor.RED + "Invalid input!");
            e.printStackTrace();
            return false;
        }
    }

    private String fromArgs(String[] args) {
        String str = "";
        for (String st : args) {
            str += st + " ";
        }
        return str;
    }

    private String[] parseParams(String[] args) {
        boolean[] toremove = new boolean[args.length];
        if (args.length > 1) {
            for (int x = 1; x < args.length; x++) {
                String str = args[x];
                if (str.startsWith("-") && str.length() > 1) {
                    switch (str.charAt(1)) {

                        case 'b':
                            try {
                                int i = Integer.parseInt(str.substring(2));
                                setBrushSize(i);
                                toremove[x] = true;
                            } catch (Exception e) {
                                p.sendMessage(ChatColor.RED + args[x] + " is Not a valid parameter!");
                            }
                            break;

                        case 'r':
                            try {
                                if (str.length() == 2) {
                                    setRange(-1);
                                } else {
                                    setRange(Double.parseDouble(str.substring(2)));
                                }
                                toremove[x] = true;
                            } catch (Exception e) {
                                p.sendMessage(ChatColor.RED + args[x] + " is Not a valid parameter!");
                            }
                            break;

                        case 'l':
                            toggleLightning();
                            toremove[x] = true;
                            break;

                        case 'e':
                            p.chat("/ve " + str.substring(2));
                            break;
                    }
                }
            }
        }
        int i = 0;
        for (boolean b : toremove) {
            if (b) {
                i++;
            }
        }
        if (i == 0) {
            return args;
        }
        String[] temp = new String[args.length - i];
        i = 0;
        for (int x = 0; x < args.length; x++) {
            if (!toremove[x]) {
                temp[i++] = args[x];
            }
        }
        return temp;
    }

    //writes parameters to the current key in the hashmap
    private void fillCurrent() {
        currentP[0] = this.voxelId;
        currentP[1] = this.replaceId;
        currentP[2] = this.data;
        currentP[3] = this.brushSize;
        currentP[4] = this.voxelHeight;
        currentP[5] = this.cCen;
        brushPresetsParamsS.put("current@", currentP);
    }

    //writes parameters of the last brush you were working with to the previous key in the hashmap
    private void fillPrevious() {
        currentP[0] = this.voxelId;
        currentP[1] = this.replaceId;
        currentP[2] = this.data;
        currentP[3] = this.brushSize;
        currentP[4] = this.voxelHeight;
        currentP[5] = this.cCen;
        brushPresetsParamsS.put("previous@", currentP);
    }

    //reads parameters from the current key in the hashmap
    private void readCurrent() {
        currentP = brushPresetsParamsS.get("current@");
        this.voxelId = currentP[0];
        this.replaceId = currentP[0];
        this.data = (byte) currentP[0];
        this.brushSize = currentP[0];
        this.voxelHeight = currentP[0];
        this.cCen = currentP[0];
    }

    /**
     * The main sniper method
     * Executes the current selected brush
     *
     * @param playr The caller
     */
    public boolean snipe(Player playr, Action action, Material itemInHand, Block clickedBlock, BlockFace clickedFace) {
        boolean bool = false;
        try {
            p = playr;

            if (p.isSneaking()) {
                return sneak.perform(action, this, itemInHand, clickedBlock, clickedFace);
            }

            bool = current.perform(action, this, itemInHand, clickedBlock, clickedFace);

            if (this.hashUndo.size() > 20) {
                this.hashUndo.remove(this.hashEn - 20);
            }
        } catch (Exception e) {
            p.sendMessage(ChatColor.RED + "An Exception has occured! (Sniping error)");
            p.sendMessage(ChatColor.RED + "" + e.toString());
            StackTraceElement[] ste = e.getStackTrace();
            for (StackTraceElement se : ste) {
                p.sendMessage(ChatColor.DARK_GRAY + se.getClassName() + ChatColor.DARK_GREEN + " : " + ChatColor.DARK_GRAY + se.getLineNumber());
            }
            VoxelSniper.log.warning("[VoxelSniper] Exception while sniping: (" + p.getName() + " " + current.name + ")");
            VoxelSniper.log.log(Level.SEVERE, null, e);
            return false;
        }
        return bool;
    }

    /**
     * Performs the undo process
     */
    public void doUndo() {
        if (this.hashEn > 0) {
            vUndo h = this.hashUndo.get(--hashEn);
            if (h != null) {
                h.undo();
                this.hashUndo.remove(hashEn);
            }
            this.p.sendMessage(ChatColor.GREEN + "Undo succesfull!  " + ChatColor.RED + h.getSize() + ChatColor.GREEN + "  Blocks have been replaced.");
        } else {
            this.p.sendMessage(ChatColor.GREEN + "Nothing to undo");
        }
    }

    public void doUndo(int num) {
        int sum = 0;
        if (hashEn > 0) {
            for (int x = 0; x < num; x++) {
                if (hashEn > 0) {
                    vUndo h = hashUndo.get(--hashEn);
                    if (h != null) {
                        h.undo();
                        this.hashUndo.remove(hashEn);
                        sum += h.getSize();
                    }
                } else {
                    break;
                }
            }
            p.sendMessage(ChatColor.GREEN + "Undo succesfull!  " + ChatColor.RED + sum + ChatColor.GREEN + "  Blocks have been replaced.");
        } else {
            p.sendMessage(ChatColor.GREEN + "Nothing to undo");
        }
    }

    /**
     * Displays the info of the current brush to the player
     */
    public void info() {
        current.info(vm);
        if (current instanceof Performer) {
            ((Performer) current).showInfo(vm);
        }
    }

    public void printBrushes() {
        String msg = ChatColor.GREEN + "Available brush short-names: /b ";
        for (String brushName : myBrushes.keySet()) {
            msg += ChatColor.GREEN + " | " + ChatColor.BLUE + brushName;
        }
        p.sendMessage(msg);
    }

    public void printBrushesLong() {
        String msg = ChatColor.GREEN + "Available brush long-names: /b ";
        for (String brushName : brushAlt.keySet()) {
            msg += ChatColor.GREEN + " | " + ChatColor.BLUE + brushName;
        }
        p.sendMessage(msg);
    }

    /**
     * Switches the current brush to the previous brush.  just returns a snipe brush if they haven't chosen anything else in the past.
     */
    public void previousBrush() {
        fillCurrent();
        Brush temp = current;
        current = previous;
        previous = temp;
        int[] tempP = brushPresetsParamsS.get("current@"); //just filled with the fillCurrent() above
        brushPresetsParamsS.put("current@", brushPresetsParamsS.get("previous@"));
        brushPresetsParamsS.put("previous@", tempP);
        readCurrent();

        info();
    }

    /**
     * Switches the current brush to the brush two back from this one (also just a snipe if nothing else exists)
     */
    public void twoBackBrush() {
        fillCurrent();
        Brush temp = current;
        Brush tempTwo = previous;
        current = twoBack;
        previous = temp;
        twoBack = tempTwo;

        int[] tempP = brushPresetsParamsS.get("current@");
        int[] tempTwoP = brushPresetsParamsS.get("previous@");
        brushPresetsParamsS.put("current@", brushPresetsParamsS.get("twoBack@"));
        brushPresetsParamsS.put("previous@", tempP);
        brushPresetsParamsS.put("twoBack@", tempTwoP);
        readCurrent();

        info();
    }

    /**
     * Loads current brush settings to a numerical preset, to be grabbed later.  More complicated but more powerful version of green record, basically.
     */
    public void savePreset(int slot) {
        brushPresets.put(slot, current);
        fillCurrent();
        brushPresetsParams.put(slot, currentP);
        p.sendMessage(ChatColor.AQUA + "Preset saved in slot " + slot);
    }

    public void savePreset(String slot) { //string version
        brushPresetsS.put(slot, current);
        fillCurrent();
        brushPresetsParamsS.put(slot, currentP);
        p.sendMessage(ChatColor.AQUA + "Preset saved in slot " + slot);
    }

    /**
     * Loads presets that have been set with savePreset().
     */
    public void loadPreset(int slot) {
        try {
            int[] paramArray = brushPresetsParams.get(slot);

            Brush temp = brushPresets.get(slot);
            if (temp != current) {
                twoBack = previous;
                previous = current;
                current = temp;
            }
            this.voxelId = paramArray[0];
            this.replaceId = paramArray[1];
            this.data = (byte) paramArray[2];
            this.brushSize = paramArray[3];
            this.voxelHeight = paramArray[4];
            this.cCen = paramArray[5];

            info();
        } catch (Exception e) {
            p.sendMessage(ChatColor.RED + "Preset is empty.  Cannot load.");
        }
    }

    public void loadPreset(String slot) {
        try {
            int[] paramArray = brushPresetsParamsS.get(slot);

            Brush temp = brushPresetsS.get(slot);
            if (temp != current) {
                twoBack = previous;
                previous = current;
                current = temp;
            }
            this.voxelId = paramArray[0];
            this.replaceId = paramArray[1];
            this.data = (byte) paramArray[2];
            this.brushSize = paramArray[3];
            this.voxelHeight = paramArray[4];
            this.cCen = paramArray[5];
            info();
        } catch (Exception e) {
            p.sendMessage(ChatColor.RED + "Preset is empty.  Cannot load.");
        }
    }

    /**
     * Resets the Sniper's data
     */
    public void reset() {
        if (this instanceof liteSniper) {
            myBrushes = liteBrushes.getSniperBrushes();
        } else {
            myBrushes = vBrushes.getSniperBrushes();
        }

        current = new Snipe();

        fillPrevious();
        this.voxelId = 0;
        this.replaceId = 0;
        this.data = 0;
        this.replaceData = 0; // Noticed this was missing - Giltwist
        this.brushSize = 3;
        this.voxelHeight = 1;
        this.cCen = 0;

        //Is it possible to set the performer of every brush simultaneously or would
        //there need to be a for loop? - Giltwist

        fillCurrent();
    }
}
