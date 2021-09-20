import java.awt.*;
import java.awt.geom.Ellipse2D;

class Ball {
    private Color color;
    private Component canvas;
    private static final int XSIZE = 30;
    private static final int YSIZE = 30;
    private int x = 0;
    private int y = 0;
    private int dx = 10;
    private int dy = 10;

    public Ball(Component c) {
        this.canvas = c;
//        if (Math.random() < 0.5) {
//            x = new Random().nextInt(this.canvas.getWidth());
//            y = 0;
//        } else {
//            x = 0;
//            y = new Random().nextInt(this.canvas.getHeight());
//        }
        x = 0;
        y = 0;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

//    public static void f() {
//        int a = 0;
//    }

    public void draw(Graphics2D g2) {
        g2.setColor(color);

        g2.fill(new Ellipse2D.Double(x, y, XSIZE, YSIZE));
    }

    public void move() {
        x += dx;
        y += dy;
        if (x < 0) {
            x = 0;
            dx = -dx;
        }
        if (x + XSIZE >= this.canvas.getWidth()) {
            x = this.canvas.getWidth() - XSIZE;
            dx = -dx;
        }
        if (y < 0) {
            y = 0;
            dy = -dy;
        }
        if (y + YSIZE >= this.canvas.getHeight()) {
            y = this.canvas.getHeight() - YSIZE;
            dy = -dy;
        }
        this.canvas.repaint();
    }
}
