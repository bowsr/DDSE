package bit;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class BitConverter {

    public static byte[] getBytes(int val) {
        return ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(val).array();
    }

    public static byte[] getBytes(float val) {
        return getBytes(Float.floatToIntBits(val));
    }

    public static byte[] getBytes(int... vals) {
        byte[] buf = new byte[vals.length * 4];
        for(int i = 0; i < vals.length; i++) {
            System.arraycopy(getBytes(vals[i]), 0, buf, i * 4, 4);
        }
        return buf;
    }

    public static int toInt(byte[] bytes) {
        return ByteBuffer.wrap(bytes, 0, 4).order(ByteOrder.LITTLE_ENDIAN).getInt();
    }

    public static float toSingle(byte[] bytes) {
        return ByteBuffer.wrap(bytes, 0, 4).order(ByteOrder.LITTLE_ENDIAN).getFloat();
    }

}