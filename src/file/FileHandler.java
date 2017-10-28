package file;

import bit.BitConverter;
import enemy.Enemy;
import gui.SEGUIHandler;

import java.io.*;

public class FileHandler {

    private static final int TRAIT_BUFFER_SIZE = 36;
    public static final int MAP_BUFFER_SIZE = 10404;
    private static final int PRE_BUFFER_SIZE = 40;

    private static byte[] traitBuffer;
    private static byte[] mapBuffer, mapBufferTemplate;
    private static byte[] preBuffer;
    private static byte[] spawnBuffer;

    private static int spawnBufferSize = 28;

    private static void readFile(InputStream in, boolean spawns) {
        traitBuffer = new byte[TRAIT_BUFFER_SIZE];
        mapBuffer = new byte[MAP_BUFFER_SIZE];
        preBuffer = new byte[PRE_BUFFER_SIZE];
        spawnBuffer = null;
        spawnBufferSize = 28;
        try {
            in.read(traitBuffer, 0, TRAIT_BUFFER_SIZE);
            in.read(mapBuffer, 0, MAP_BUFFER_SIZE);
            in.read(preBuffer, 0, PRE_BUFFER_SIZE);
            if(spawns && in.available() > 0) {
                int offset = 0;
                while(in.available() > 0) {
                    growSpawnBuffer();
                    in.read(spawnBuffer, offset, 28);
                    offset += 28;
                }
            }
        }catch(IOException ioe) {
            ioe.printStackTrace();
        }finally {
            try {
                if(in != null) in.close();
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void readFile(File input) {
        InputStream in = null;
        try {
            in = new FileInputStream(input);
            readFile(in, true);
        }catch(IOException ioe) {
            ioe.printStackTrace();
        }finally {
            try {
                if(in != null) in.close();
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void readTemplateFile() {
        InputStream in = null;
        try {
            /* Use this line when packed in jar. */
            in = FileHandler.class.getClassLoader().getResourceAsStream("data/survival");

            /* Use this line when testing in dev env. */
//            in = new FileInputStream(new File("data/survival"));
            readFile(in, false);
            mapBufferTemplate = new byte[MAP_BUFFER_SIZE];
            System.arraycopy(mapBuffer, 0, mapBufferTemplate, 0, MAP_BUFFER_SIZE);
        }catch(Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(in != null) in.close();
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void writeNewSpawnsetFile(File file) {
        setupDataBuffersFromGUI();
        OutputStream out = null;
        try {
            if(file.exists()) file.delete();
            file.createNewFile();
            out = new FileOutputStream(file);
            out.write(traitBuffer, 0, TRAIT_BUFFER_SIZE);
            out.write(mapBuffer, 0, MAP_BUFFER_SIZE);
            out.write(preBuffer, 0,PRE_BUFFER_SIZE);
            if(spawnBuffer != null) out.write(spawnBuffer, 0, spawnBufferSize);
        }catch(Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(out != null) {
                    out.flush();
                    out.close();
                }
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void setupDataBuffersFromGUI() {
        int totalEnemies = SEGUIHandler.instance.elm.getSize(), enemy = totalEnemies;
        if(SEGUIHandler.instance.combo.getSelectedIndex() == 1)
            totalEnemies += 17;
        
        System.arraycopy(BitConverter.getBytes(totalEnemies), 0, preBuffer, PRE_BUFFER_SIZE - 4, 4);
        spawnBufferSize = totalEnemies * 28;
        spawnBuffer = new byte[spawnBufferSize];
        for(int i = 0; i < totalEnemies; i++) {
            if(i >= enemy) {
                System.arraycopy(new Enemy(-1, 60).getBytes(), 0, spawnBuffer, i * 28, 28);
                continue;
            }
            System.arraycopy(SEGUIHandler.instance.elm.get(i).getBytes(), 0, spawnBuffer, i * 28, 28);
        }

        byte[]  radF   = BitConverter.getBytes((float)(double)SEGUIHandler.instance.radiusFinal.getValue()),
                radS   = BitConverter.getBytes((float)(double)SEGUIHandler.instance.radiusStart.getValue()),
                rate   = BitConverter.getBytes((float)(double)SEGUIHandler.instance.shrinkRate.getValue()),
                bright = BitConverter.getBytes((float)(double)SEGUIHandler.instance.spinBrightness.getValue());
        System.arraycopy(radF, 0, traitBuffer, 8, 4);
        System.arraycopy(radS, 0, traitBuffer, 12, 4);
        System.arraycopy(rate, 0, traitBuffer, 16, 4);
        System.arraycopy(bright, 0, traitBuffer, 20, 4);

        System.arraycopy(SEGUIHandler.instance.aep.getBytes(), 0, mapBuffer, 0, MAP_BUFFER_SIZE);
    }

    private static void growSpawnBuffer() {
        if(spawnBuffer == null || spawnBuffer.length == 0) {
            spawnBuffer = new byte[spawnBufferSize];
            return;
        }
        byte[] temp = new byte[spawnBufferSize];
        System.arraycopy(spawnBuffer, 0, temp, 0, spawnBufferSize);
        spawnBufferSize += 28;
        spawnBuffer = new byte[spawnBufferSize];
        System.arraycopy(temp, 0, spawnBuffer, 0, temp.length);
    }

    public static byte[] getMapBuffer() {
        return mapBuffer;
    }

    public static byte[] getTraitBuffer() {
        return traitBuffer;
    }

    public static byte[] getSpawnBuffer() {
        return spawnBuffer;
    }

}
