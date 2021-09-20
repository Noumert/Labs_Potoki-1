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

        JButton joinShow = new JButton("join show");
        JButton buttonStop = new JButton("Stop");


        joinShow.addActionListener(event -> {
            Ball a = new Ball(canvas);
            a.setColor(Color.BLUE);
            canvas.add(a);

            Ball b = new Ball(canvas);
            b.setColor(Color.RED);
            canvas.add(b);

            JoinThread joinThread = new JoinThread(a,b);

            joinThread.start();
        });

        buttonStop.addActionListener(event -> System.exit(0));

        buttonPanel.add(buttonStop);
        buttonPanel.add(joinShow);

        content.add(buttonPanel, BorderLayout.SOUTH);
    }
}
