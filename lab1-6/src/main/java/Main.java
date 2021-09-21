public class Main {
    static volatile boolean isPrinted = false;

    public static void main(String[] args) throws InterruptedException {
        //async

        Counter c = new Counter();

        Thread t1 = new Thread(() -> {
            for(int i=0; i<1000000; i++)
                c.increment();
        });

        Thread t2 = new Thread(() -> {
            for(int i=0; i<1000000; i++)
                c.decrement();
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println("Async: " + c.count);

        //sync

        CounterSync cs = new CounterSync();

        Thread ts1 = new Thread(() -> {
            for(int i=0; i<1000000; i++)
                cs.increment();
        });

        Thread ts2 = new Thread(() -> {
            for(int i=0; i<1000000; i++)
                cs.decrement();
        });

        ts1.start();
        ts2.start();
        ts1.join();
        ts2.join();

        System.out.println("Sync: " + cs.count);
    }
}
