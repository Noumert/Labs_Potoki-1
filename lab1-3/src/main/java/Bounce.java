import javax.swing.*;

public class Bounce {
    public static volatile int max = 0;
    public static volatile int min = 0;
    public final static int amount = 100;

    public static void main(String[] args) throws InterruptedException {
        //Бильярд

        BounceFrame frame = new BounceFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setVisible(true);
        System.out.println("Thread name = " + Thread.currentThread().getName());

        while (true){
            System.out.println("Max " + max);
            System.out.println("Min " + min/amount);
            Thread.sleep(500);
        }

    }

}
