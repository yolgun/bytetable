package yolgun;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

/**
 * Hello world!
 */
public class Driver {
    public byte[][] doubleArray = new byte[1000][1000];
    public ShortByteTable table = new ShortByteTable();

    public static void main(String[] args) {
        System.out.println("Hello World!");
        Driver driver = new Driver();
        IntStream.range(0, 1000).forEach(i -> {
            int x = ThreadLocalRandom.current().nextInt(0, 1000);
            int y = ThreadLocalRandom.current().nextInt(0, 1000);
            int val = ThreadLocalRandom.current().nextInt(1, 128);
            driver.doubleArray[x][y] = (byte) val;
            driver.table.set(x, y, (byte) val);
        });

        IntStream.range(0, 1000).forEach(i -> {
            IntStream.range(0, 1000).forEach(j -> {
                if (driver.doubleArray[i][j] != 0) {
                    System.out.printf("%d %d-> %s %s%n", i, j, driver.doubleArray[i][j], driver.table.get(i, j));
                    assert driver.doubleArray[i][j] == driver.table.get(i, j);
                }
            });
        });

        driver.table.addToAllExceptNegatives((byte) 1);

        IntStream.range(0, 1000).forEach(i -> {
            IntStream.range(0, 1000).forEach(j -> {
                if (driver.doubleArray[i][j] == 0) {
                    assert ShortByteTable.DEFAULT_VALUE == driver.table.get(i, j);
                } else if (driver.doubleArray[i][j] == 127) {
                    assert -128 == driver.table.get(i, j);
                } else {
                    System.out.printf("%d %d-> %s %s%n", i, j, driver.doubleArray[i][j], driver.table.get(i, j));
                    assert driver.doubleArray[i][j] + 1 == driver.table.get(i, j);
                }
            });
        });

        driver.table.addToAllExceptNegatives((byte) 1);

        IntStream.range(0, 1000).forEach(i -> {
            IntStream.range(0, 1000).forEach(j -> {
                if (driver.doubleArray[i][j] == 0 || driver.doubleArray[i][j] == 127 || driver.doubleArray[i][j] == 126) {
                    assert ShortByteTable.DEFAULT_VALUE == driver.table.get(i, j);
                } else {
                    System.out.printf("%d %d-> %s %s%n", i, j, driver.doubleArray[i][j], driver.table.get(i, j));
                    assert driver.doubleArray[i][j] + 2 == driver.table.get(i, j);
                }
            });
        });

        try {
            Thread.sleep(1000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
