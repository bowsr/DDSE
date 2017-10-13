package gui.input.arena;

import bit.BitConverter;
import file.FileHandler;
import gui.SEPanel;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ArenaEditPane extends SEPanel {

    public ArenaTile[][] tileData;
    private float map[][], template[][];

    public ArenaEditPane() {
        setLayout(new GridBagLayout());

        tileData = new ArenaTile[23][23];

        map = new float[51][51];
        template = new float[51][51];
        byte[] buffer = FileHandler.getMapBuffer(), tmp = new byte[4];
        for(int i = 0; i < buffer.length; i += 4) {
            System.arraycopy(buffer, i, tmp, 0, 4);
            map[i / 204][(i / 4) % 51] = BitConverter.toSingle(tmp);
            template[i / 204][(i / 4) % 51] = BitConverter.toSingle(tmp);
        }

        GridBagConstraints gbc = new GridBagConstraints();
        for(int row = 0; row < 23; row++) {
            for(int col = 0; col < 23; col++) {
                gbc.gridx = col;
                gbc.gridy = row;

                ArenaTile tile = new ArenaTile(map[row + 14][col + 14], row + 14, col + 14);

                tileData[row][col] = tile;

                Border border = null;
                if(row < 22) {
                    if(col < 22) {
                        border = new MatteBorder(1, 1, 0, 0, Color.DARK_GRAY);
                    }else {
                        border = new MatteBorder(1, 1, 0, 1, Color.DARK_GRAY);
                    }
                }else {
                    if(col < 22) {
                        border = new MatteBorder(1, 1, 1, 0, Color.DARK_GRAY);
                    }else {
                        border = new MatteBorder(1, 1, 1, 1, Color.DARK_GRAY);
                    }
                }
                tile.setBorder(border);
                add(tile, gbc);
            }
        }
    }

    public void loadMapFromFile() {
        map = new float[51][51];
        byte[] buffer = FileHandler.getMapBuffer(), tmp = new byte[4];
        for(int i = 0; i < buffer.length; i += 4) {
            System.arraycopy(buffer, i, tmp, 0, 4);
            int row = i / 204, col = (i / 4) % 51;
            map[row][col] = BitConverter.toSingle(tmp);
            if((row > 13 && col > 13) && (row < 37 && col < 37)) {
                tileData[row - 14][col - 14].data.height = map[row][col];
                tileData[row - 14][col - 14].setTile();
            }
        }
    }

    public void updateMapHeights() {
        for(int row = 0; row < 23; row++) {
            for(int col = 0; col < 23; col++) {
                map[14 + row][14 + col] = tileData[row][col].data.height;
            }
        }
    }

    public byte[] getBytes() {
        updateMapHeights();
        byte[] buf = new byte[FileHandler.MAP_BUFFER_SIZE];
        int offset = 0;
        for(int row = 0; row < 51; row++) {
            for(int col = 0; col < 51; col++) {
                System.arraycopy(BitConverter.getBytes(map[row][col]), 0, buf, offset, 4);
                offset += 4;
            }
        }
        return buf;
    }

    class ArenaTile extends JPanel {

        final Color defaultBG = Color.BLACK;
        final Color enabledBG = new Color(92, 79, 58);

        ArenaTileData data;

        ArenaTile(float height, int x, int y) {
            if(height >= 0f) setBackground(enabledBG);
            else setBackground(defaultBG);
            data = new ArenaTileData(height, x, y);
            addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    toggle();
                }
            });
        }

        void setTile() {
            if(data.height >= 0f) setBackground(enabledBG);
            else setBackground(defaultBG);
        }

        void toggle() {
            if(data.height >= 0f) disableTile();
            else enableTile();
        }

        void enableTile() {
            setBackground(enabledBG);
            data.height = template[data.x][data.y];
        }

        void disableTile() {
            setBackground(defaultBG);
            data.height = -1007.57031f;
        }

        public Dimension getPreferredSize() {
            return new Dimension(24, 24);
        }

        class ArenaTileData {

            int x, y;
            float height;

            ArenaTileData(float h, int xpos, int ypos) {
                height = h;
                x = xpos;
                y = ypos;
            }
        }
    }

}
