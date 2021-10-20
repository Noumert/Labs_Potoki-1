import java.util.Random;

public class Consumer implements Runnable {
    private Drop drop;

    public Consumer(Drop drop) {
        this.drop = drop;
    }

    public void run() {
        Random random = new Random();
        for (Integer message = drop.take();
             ! message.equals(0);
             message = drop.take()) {
            System.out.format("MESSAGE RECEIVED: %s%n", message);
        }
    }
}