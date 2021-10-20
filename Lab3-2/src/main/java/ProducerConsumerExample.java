public class ProducerConsumerExample {
    private static final int SIZE_100 = 100;
    private static final int SIZE_1000 = 1000;
    private static final int SIZE_5000 = 5000;

    public static void main(String[] args) throws InterruptedException {
        Drop drop = new Drop();

        startProAndCon(drop, SIZE_100);

        startProAndCon(drop, SIZE_1000);

        startProAndCon(drop, SIZE_5000);
    }

    private static void startProAndCon(Drop drop, int size100) throws InterruptedException {
        Thread thread1 = (new Thread(new Producer(drop, size100)));
        thread1.start();
        Thread thread2 = (new Thread(new Consumer(drop)));
        thread2.start();
        thread1.join();
        thread2.join();
    }
}