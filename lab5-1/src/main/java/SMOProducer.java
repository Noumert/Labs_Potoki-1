import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

public class SMOProducer extends RecursiveAction {

    private final List<SMOQueue> smoQueueList;
    private final long customers;
    private final Random random = new Random();
    private final int THRESHOLD;

    public SMOProducer(List<SMOQueue> smoQueueList, long customers, int THRESHOLD) {
        this.smoQueueList = smoQueueList;
        this.customers = customers;
        this.THRESHOLD = THRESHOLD;
    }

    @Override
    protected void compute() {
        if (THRESHOLD > 1) {
            ForkJoinTask.invokeAll(createSubtasks());
        } else {
            try {
                processing(smoQueueList);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private List<SMOProducer> createSubtasks() {
        List<SMOProducer> subtasks = new ArrayList<>();
        for (int i = 0; i < smoQueueList.size(); i++) {
            subtasks.add(new SMOProducer(smoQueueList, customers, 1));
        }
        return subtasks;
    }

    private void processing(List<SMOQueue> smoQueues) throws InterruptedException {
        do{
            Thread.sleep(random.nextInt(5));
            SMOQueue smoQueue = chooseMinQueue(smoQueues);
            if (!smoQueue.blockingQueue.offer(true) && smoQueue.ok.get() != customers) {
                smoQueue.fail.incrementAndGet();
            }
            addQueueLengthHistory(smoQueues);
        }while (!testIsFinish());
    }

    private void addQueueLengthHistory(List<SMOQueue> smoQueues) {
        for (SMOQueue s :
                smoQueues) {
            s.lengthHistory.add(s.blockingQueue.size());
        }
    }

    private SMOQueue chooseMinQueue(List<SMOQueue> smoQueues) {
        SMOQueue smoQueue = smoQueues.get(0);
        for (SMOQueue s : smoQueues) {
            if (s.blockingQueue.size() < smoQueue.blockingQueue.size()) {
                smoQueue = s;
            }
        }
        return smoQueue;
    }

    private boolean testIsFinish() {
        int i = 0;
        for (SMOQueue s : smoQueueList) {
            if (s.ok.get() == customers) {
                i++;
            }
        }
        return i == smoQueueList.size();
    }
}