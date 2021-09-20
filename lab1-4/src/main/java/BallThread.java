public class BallThread extends Thread {
    private Ball b;

    public BallThread(Ball ball) {
        b = ball;
    }

    @Override
    public void run() {
        try {

            b.move();
            System.out.println("Thread name = "
                    + Thread.currentThread().getName());

            Thread.sleep(1000);

        } catch (InterruptedException ex) {

        }
    }
}
