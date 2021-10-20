import java.util.List;
import java.util.concurrent.Semaphore;

public class MarksPuttingTest {
    public static void main(String[] args) throws InterruptedException {
        Journal journal = new Journal();
        Semaphore semaphore = new Semaphore(1);
        Thread teacher = new PuttingMarksThread(journal, semaphore);
        teacher.start();
        Thread assistant1 = new PuttingMarksThread(journal, semaphore);
        assistant1.start();
        Thread assistant2 = new PuttingMarksThread(journal, semaphore);
        assistant2.start();
        Thread assistant3 = new PuttingMarksThread(journal, semaphore);
        assistant3.start();

        teacher.join();
        assistant1.join();
        assistant2.join();
        assistant3.join();

        int sum = journal.students.stream().mapToInt(List::size).sum();

        System.out.println("Оцінок поставлено: " + sum);
    }
}
