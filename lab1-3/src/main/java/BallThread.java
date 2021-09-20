public class BallThread extends Thread {
    private Ball b;

    public BallThread(Ball ball) {
        b = ball;
    }

    @Override
    public void run() {
        try {
            while (true) {
                b.move();
//                synchronized (this) {
                    if (currentThread().getName().equals("MaxPriority")) {
                        Bounce.max++;

                    } else {
                        Bounce.min++;
                    }
//                }

                Thread.sleep(2);
            }
        } catch (InterruptedException ex) {

        }
    }
}
