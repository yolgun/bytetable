package yolgun;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static yolgun.ShortByteTable.DEFAULT_VALUE;
import static yolgun.ShortByteTable.b;

/**
 * Created by yunus on 21.01.17.
 */
@RunWith(JUnitParamsRunner.class)
public class ShortByteTableTest {
    private static final int TABLE_SIZE = 1000;
    private static final double FILL_FACTOR = 0.1;
    private static final int MAX_VAL = 128;
    private final ShortByteTable sut = new ShortByteTable();
    private final byte[][] byteArray = new byte[TABLE_SIZE][TABLE_SIZE];

    public ShortByteTableTest() {
        for (int i = 0; i < TABLE_SIZE; i++) {
            for (int j = 0; j < TABLE_SIZE; j++) {
                byteArray[i][j] = ShortByteTable.DEFAULT_VALUE;
            }
        }
        int numElements = Math.toIntExact(Math.round(FILL_FACTOR * TABLE_SIZE * TABLE_SIZE));
        Random random = new Random(42);
        for (int i = 0; i < numElements; i++) {
            int x = random.nextInt(TABLE_SIZE);
            int y = random.nextInt(TABLE_SIZE);
            int val = random.nextInt(MAX_VAL);
            byteArray[x][y] = b(val);
            sut.set(x, y, b(val));
        }
    }

    @Test
    public void setAndGetSameValue() throws Exception {
        for (int i = 0; i < TABLE_SIZE; i++) {
            for (int j = 0; j < TABLE_SIZE; j++) {
                assertEquals("Wrong value at index " + i + " " + j, byteArray[i][j], sut.get(i, j));
            }
        }
    }

    @Test
    @Parameters({"1", "2", "42"})
    public void addToAllExceptNegatives(byte val) throws Exception {
//        byte val = b(1);
        sut.addToAllExceptNegatives(val);
        for (int i = 0; i < TABLE_SIZE; i++) {
            for (int j = 0; j < TABLE_SIZE; j++) {
                if (byteArray[i][j] != DEFAULT_VALUE && byteArray[i][j] + val < MAX_VAL) {
                    assertEquals("Wrong value at index " + i + " " + j, byteArray[i][j] + val, sut.get(i, j));
                }
            }
        }
    }

}