import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

public class SMOConsumer extends RecursiveAction {

    private final List<SMOQueue> smoQueueList;
    private final int customersLimit;
    private final int ONE = 1;
    private final Random random = new Random();

    public SMOConsumer(List<SMOQueue> smoQueueList,int customersLimit) {
        this.smoQueueList = smoQueueList;
        this.customersLimit = customersLimit;
    }

    @Override
    protected void compute() {
        if (smoQueueList.size() != ONE) {
            ForkJoinTask.invokeAll(createSubtasks());
        } else {
            try {
                processing(smoQueueList.get(0));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private List<SMOConsumer> createSubtasks() {
        List<SMOConsumer> subtasks = new ArrayList<>();
        subtasks.add(new SMOConsumer(smoQueueList.subList(0,smoQueueList.size()/2),customersLimit));
        subtasks.add(new SMOConsumer(smoQueueList.subList(smoQueueList.size()/2,smoQueueList.size()),customersLimit));
        return subtasks;
    }

    private void processing(SMOQueue smoQueue) throws InterruptedException {
        int ok;
        do {
            Thread.sleep(random.nextInt(10));
            smoQueue.blockingQueue.take();
            ok = smoQueue.ok.incrementAndGet();
//            System.out.println("OK " + ok);
        }while (ok !=customersLimit);
    }
}