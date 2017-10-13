package enemy;

import bit.BitConverter;

import java.security.InvalidParameterException;

public class Enemy {

    private int identifier;
    private int delay;

    private int relativeSpawnTime;

    private boolean isLast17 = false;

    public Enemy(int id, int d) {
        identifier = id;
        delay = d;
    }

    public byte[] convertIdentifier() {
        return BitConverter.getBytes(identifier);
    }

    public byte[] convertDelay() {
        return BitConverter.getBytes((float)delay);
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

    public int getDelay() {
        return delay;
    }

    public int getRelativeSpawnTime() {
        return relativeSpawnTime;
    }

    public void setRelativeSpawnTime(int sec) {
        relativeSpawnTime = sec;
    }

    public void setLast17(boolean flag) {
        isLast17 = flag;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    private String convertID(int enemy) {
        switch(enemy) {
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
            throw new InvalidParameterException("There is no enemy spawn with ID " + enemy);
        }
    }

    public String toString() {
        StringBuilder s = new StringBuilder(convertID(identifier));
        for(int i = s.toString().length(); i <= 18; i++) {
            s.append(' ');
        }
        s.append(delay);
        for(int j = Integer.toString(delay).length(); j <= 12; j++) {
            s.append(' ');
        }
        s.append(relativeSpawnTime);
        if(isLast17) {
            for(int k = Integer.toString(relativeSpawnTime).length(); k <= 10; k++) {
                s.append(' ');
            }
            s.append('\u2713');
        }
        return s.toString();
    }
}
