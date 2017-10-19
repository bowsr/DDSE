package gui;

import bit.BitConverter;
import enemy.Enemy;
import file.FileHandler;

import javax.swing.*;

public class SEListModel extends DefaultListModel<Enemy> {

    public SEListModel() {
        super();
    }

    public void addEnemy(int enemy, int delay) {
        addEnemy(getSize(), enemy, delay);
    }

    public void addEnemy(int index, int enemy, int delay) {
        Enemy ed = new Enemy(enemy, delay);
//        ed.setRelativeSpawnTime((getSize() > 0) ? (get(getSize() - 1).getRelativeSpawnTime() + delay) : delay);
        super.add(index, ed);
        updateListDisplay();
    }

    public Enemy remove(int index) {
        Enemy e = super.remove(index);
        updateListDisplay();
        return e;
    }

    public void loadEnemyListFromFile() {
        clear();
        byte[] buffer = FileHandler.getSpawnBuffer(), tmp = new byte[4];
        int id, delay;
        for(int i = 0; i < buffer.length; i += 28) {
            System.arraycopy(buffer, i, tmp, 0, 4);
            id = BitConverter.toInt(tmp);
            System.arraycopy(buffer, i + 4, tmp, 0, 4);
            delay = (int) BitConverter.toSingle(tmp);
            addEnemy(id, delay);
        }
    }

    public void updateListDisplay() {
        updateGemCounts();
        updateSpawnTimes();
        updateLast17();
    }

    private void updateGemCounts() {
        for(int i = 0; i < getSize(); i++) {
            get(i).setGems((i == 0) ? 0 : get(i - 1).getRelativeGemCount());
        }
    }

    private void updateSpawnTimes() {
        for(int i = 0; i < getSize(); i++) {
            get(i).setRelativeSpawnTime((i == 0) ? get(i).getDelay() : get(i - 1).getRelativeSpawnTime() + get(i).getDelay());
        }
    }

    private void updateLast17() {
        for(int i = getSize() - 1; i >= 0; i--) {
            if(getSize() < 17)
                get(i).setLast17(false);
            else
                if(i >= getSize() - 17)
                    get(i).setLast17(true);
                else
                    get(i).setLast17(false);
        }
    }
}
