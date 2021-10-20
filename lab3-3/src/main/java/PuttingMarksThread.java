import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class PuttingMarksThread extends Thread {
    private Journal journal;
    private Semaphore semaphore;

    public PuttingMarksThread(Journal journal, Semaphore semaphore) {
        this.journal = journal;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        Random random = new Random();
        for (int i = 0; i < 100000; i++) {
            try {
                semaphore.acquire();

                journal.putMark(random.nextInt(3));

            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                semaphore.release();
            }
        }
    }
}
