package yolgun;

import com.carrotsearch.hppc.IntByteHashMap;
import com.carrotsearch.hppc.IntByteMap;
import com.carrotsearch.hppc.cursors.IntByteCursor;
import com.google.common.base.Preconditions;

/**
 * Created by yunus on 21.01.17.
 */
public class ShortByteTable {
    public static final byte DEFAULT_VALUE = Byte.MIN_VALUE;
    private static final int MAX_INDEX = 1 << 16;
    private IntByteHashMap map = new IntByteHashMap();

    public void set(int x, int y, byte val) {
        checkKeys(x, y);
        int key = underlyingIndex(x, y);
        map.put(key, val);
    }

    public byte get(int x, int y) {
        checkKeys(x, y);
        int key = underlyingIndex(x, y);
        return map.getOrDefault(key, DEFAULT_VALUE);
    }

    public void addToAllExceptNegatives(byte val) {
        for (IntByteCursor cursor : map) {
            if (cursor.value >= 0) {
                int newVal = cursor.value + val;
                map.indexReplace(cursor.index, (byte) newVal);
            }
        }
        map.removeAll((key, value) -> value < 0);
    }

    private void checkKeys(int x, int y) {
        Preconditions.checkArgument(x < MAX_INDEX);
        Preconditions.checkArgument(x >= 0);
        Preconditions.checkArgument(y < MAX_INDEX);
        Preconditions.checkArgument(y >= 0);
    }

    private int underlyingIndex(int x, int y) {
        return (x << 16) | y;
    }

    public static byte b(int val) {
        return (byte) val;
    }
}
