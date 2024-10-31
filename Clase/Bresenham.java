import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;
import javax.swing.JFrame;

public class Bresenham extends JPanel{

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.RED);

        int x1 = 10;
        int y1 = 20;
        int x2 = -100;
        int y2 = 10;

        int d = 0;

        int dY = Math.abs(y2-y1);
        int dX = Math.abs(x2-x1);

        int dx2 = 2 * dX;
        int dy2 = 2 * dY;

        int ix = x1 < x2 ? 1 : -1;
        int iy = y1 < y2 ? 1 : -1;

        int x = x1;
        int y = y1;

        if (dX >= dY) {
            while (true) {
                g2d.drawLine(x, y, x, y);
                if (x == x2)
                    break;
                x += ix;
                d += dy2;
                if (d > dX) {
                    y += iy;
                    d -= dx2;
                }
            }
        } else {
            while (true) {
                g2d.drawLine(x, y, x, y);
                if (y == y2)
                    break;
                y += iy;
                d += dx2;
                if (d > dY) {
                    x += ix;
                    d -= dy2;
                }
            }
        }
    }
    public static void main(String[] args) {
        JFrame frame = new JFrame("Puntos");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new Bresenham());
        frame.setSize(250,200);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}