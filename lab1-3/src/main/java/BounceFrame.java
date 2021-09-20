import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BounceFrame extends JFrame {

    private BallCanvas canvas;
    public static final int WIDTH = 450;
    public static final int HEIGHT = 350;

    public BounceFrame() {
        this.setSize(WIDTH, HEIGHT);
        this.setTitle("Bounce programm");

        this.canvas = new BallCanvas();
        System.out.println("In Frame Thread name = "
                + Thread.currentThread().getName());
        Container content = this.getContentPane();
        content.add(this.canvas, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.lightGray);

        JButton buttonStart = new JButton("Start");
        JButton buttonStartWithMaxPriority = new JButton("Start with max priority");
        JButton buttonStop = new JButton("Stop");

        buttonStart.addActionListener(event -> {

            Ball b = new Ball(canvas);
            b.setColor(Color.BLUE);
            canvas.add(b);
            BallThread thread = new BallThread(b);
            thread.setPriority(1);
            thread.start();
            System.out.println("Thread name = " + thread.getName());

        });

        buttonStartWithMaxPriority.addActionListener(event -> {
            List<BallThread> threadList = new ArrayList<BallThread>();
            for (int i = 0; i < Bounce.amount; i++) {
                Ball b = new Ball(canvas);
                b.setColor(Color.BLUE);
                canvas.add(b);
                BallThread thread = new BallThread(b);
                thread.setPriority(1);
                threadList.add(thread);
                System.out.println("Thread name = " + thread.getName());
            }
            for (int i = 0; i < 1; i++) {
                Ball b = new Ball(canvas);
                b.setColor(Color.RED);
                canvas.add(b);
                BallThread thread = new BallThread(b);
                thread.setPriority(10);
                thread.setName("MaxPriority");
                threadList.add(thread);
                System.out.println("Thread name = " + thread.getName());
            }

            threadList.forEach(Thread::start);
        });

        buttonStop.addActionListener(event -> System.exit(0));


        buttonPanel.add(buttonStart);
        buttonPanel.add(buttonStop);
        buttonPanel.add(buttonStartWithMaxPriority);

        content.add(buttonPanel, BorderLayout.SOUTH);
    }
}
