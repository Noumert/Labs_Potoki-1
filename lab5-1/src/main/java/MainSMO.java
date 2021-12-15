import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

public class MainSMO {
    private static final int LIMIT = 100;
    private static final int CUSTOMERS_LIMIT = 1000;

    public static void main(String[] args) {
        List<SMOQueue> smoQueueList = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            smoQueueList.add(new SMOQueue(LIMIT));
        }

        ForkJoinPool forkJoinPool1 = new ForkJoinPool(4);
        SMOProducer smoProducer = new SMOProducer(smoQueueList,CUSTOMERS_LIMIT,smoQueueList.size());
        forkJoinPool1.execute(smoProducer);

        ForkJoinPool forkJoinPool2 = new ForkJoinPool(8);
        SMOConsumer smoConsumer = new SMOConsumer(smoQueueList,CUSTOMERS_LIMIT);
        forkJoinPool2.execute(smoConsumer);

        InfoThread infoThread = new InfoThread(smoQueueList,CUSTOMERS_LIMIT);
        infoThread.start();
        smoProducer.join();
        smoConsumer.join();

        smoQueueList.forEach(smoQueue -> {
            System.out.println("Avr length: " + smoQueue.getAvrLength() + " Fail Chance: " + smoQueue.getFailChance());
        });
    }
}
