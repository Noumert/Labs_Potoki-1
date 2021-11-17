import java.util.Random;
import java.util.concurrent.Semaphore;

public class PuttingMarksThread extends Thread {
    private Journal journal;


    public PuttingMarksThread(Journal journal, Semaphore semaphore) {
        this.journal = journal;

    }

    @Override
    public void run() {
        Random random = new Random();
        for (int i = 0; i < 100000; i++) {
                journal.putMark(random.nextInt(3));
        }
    }
}
