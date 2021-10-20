import java.util.Random;

public class Producer implements Runnable {
    private Drop drop;
    private int size;

    public Producer(Drop drop, int size) {
        this.drop = drop;
        this.size = size;
    }

    private Integer[] createArray(int size) {
        Integer[] integers = new Integer[size];
        for (int i = 1; i <= size; i++) {
            integers[i - 1] = i;
        }
        return integers;
    }

    public void run() {
        Integer integers[] = createArray(size);
        Random random = new Random();

        for (int i = 0;
             i < integers.length;
             i++) {
            drop.put(integers[i]);
        }
        drop.put(0);
    }
}