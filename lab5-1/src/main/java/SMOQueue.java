import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

public class SMOQueue {
    public BlockingQueue<Boolean> blockingQueue;
    private int limit;
    public List<Integer> lengthHistory = new CopyOnWriteArrayList<>();
    public AtomicInteger ok = new AtomicInteger(0);
    public AtomicInteger fail = new AtomicInteger(0);

    public SMOQueue(int limit) {
        this.limit = limit;
        this.blockingQueue = new LinkedBlockingDeque<>(limit);
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public synchronized double getAvrLength(){
        return lengthHistory.stream().mapToDouble(length->length).average().orElse(0.0);
    }

    public synchronized double getFailChance(){
        return 1.0*fail.get()/(ok.get()+fail.get());
    }
}
