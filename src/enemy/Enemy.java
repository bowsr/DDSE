package enemy;

import bit.BitConverter;

import java.security.InvalidParameterException;

public class Enemy {

    private int identifier;
    private int delay;

    private int relativeSpawnTime;
    private int relativeGemCount;

    private boolean isLast17 = false;

    public Enemy(int id, int d) {
        identifier = id;
        delay = d;
    }

    public byte[] convertIdentifier() {
        return BitConverter.getBytes(getID());
    }

    public byte[] convertDelay() {
        return BitConverter.getBytes((float)getDelay());
    }

    public byte[] getBytes() {
        byte[] buf = new byte[28];
        System.arraycopy(convertIdentifier(), 0, buf, 0, 4);
        System.arraycopy(convertDelay(), 0, buf, 4, 4);
        System.arraycopy(BitConverter.getBytes(0, 3, 0, 1106247680, 10), 0, buf, 8, 20);
        return buf;
    }

    public int getID() {
        return identifier;
    }

    public void setID(int id) {
        identifier = id;
    }

    public int getDelay() {
        return delay;
    }

    public int getRelativeSpawnTime() {
        return relativeSpawnTime;
    }

    public void setRelativeSpawnTime(int sec) {
        relativeSpawnTime = sec;
    }

    public int getRelativeGemCount() {
        return relativeGemCount;
    }

    public void setRelativeGemCount(int gems) {
        relativeGemCount = gems;
    }

    public void setGems(int gems) {
        setRelativeGemCount(gems + getEnemyGems());
    }

    public void setLast17(boolean flag) {
        isLast17 = flag;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    private String convertID() {
        switch(identifier) {
        case 0: return "SQUID I";
        case 1: return "SQUID II";
        case 2: return "CENTIPEDE";
        case 3: return "SPIDER I";
        case 4: return "LEVIATHAN";
        case 5: return "GIGAPEDE";
        case 6: return "SQUID III";
        case 7: return "THORN";
        case 8: return "SPIDER II";
        case 9: return "GHOSTPEDE";
        case -1: return "EMPTY";
        default:
            throw new InvalidParameterException("There is no enemy spawn with ID " + identifier);
        }
    }

    private int getEnemyGems() {
        switch(identifier) {
        case 0:  return 2;
        case 1:  return 3;
        case 2:  return 25;
        case 3:  return 1;
        case 4:  return 6;
        case 5:  return 50;
        case 6:  return 3;
        case 8:  return 1;
        case 9:  return 10;
        default: return 0;
        }
    }

    public String toString() {
        StringBuilder s = new StringBuilder(convertID());
        for(int i = s.toString().length(); i <= 17; i++) {
            s.append(' ');
        }
        s.append(delay);
        for(int j = Integer.toString(delay).length(); j <= 9; j++) {
            s.append(' ');
        }
        s.append(relativeSpawnTime);
        for(int k = Integer.toString(relativeSpawnTime).length(); k <= 12; k++) {
            s.append(' ');
        }
        if(isLast17) s.append('\u2713');
        else s.append(' ');
        for(int l = 0; l <= 11; l++)
            s.append(' ');
        s.append(getRelativeGemCount());
        return s.toString();
    }
}
