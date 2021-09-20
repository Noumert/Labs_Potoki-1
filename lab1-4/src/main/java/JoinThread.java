public class JoinThread extends Thread {
    private Ball a;
    private Ball b;

    public JoinThread(Ball a,Ball b) {
       this.a = a;
       this.b = b;
    }

    @Override
    public void run() {
        while (true) {
            BallThread thread1 = new BallThread(a);
            thread1.setName("blueBall");
            BallThread thread2 = new BallThread(b);
            thread2.setName("redBall");

            thread1.start();

            try {
                thread1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            thread2.start();
        }
    }
}