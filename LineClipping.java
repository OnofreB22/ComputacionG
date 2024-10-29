import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;
import javax.swing.JFrame;

public class LineClipping extends JPanel {
    static int inside = 0;
    static int left = 1;
    static int right = 2;
    static int bottom = 4;
    static int top = 8;

    static int xMin = 100;
    static int xMax = 200;
    static int yMin = 100;
    static int yMax = 200;

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.BLACK);
        g2d.drawLine(xMin, yMin, xMin, yMax);
        g2d.drawLine(xMin, yMin, xMax, yMin);
        g2d.drawLine(xMax, yMax, xMin, yMax);
        g2d.drawLine(xMax, yMax, xMax, yMin);

        int x1 = 110;
        int y1 = 110;
        int x2 = 190;
        int y2 = 290;

        g2d.setColor(Color.RED);
        g2d.drawLine(x1, y1, x2, y2);

        int outcode0 = ComputeOutCode(x1, y1);
        int outcode1 = ComputeOutCode(x2, y2);

        while (true) {
            if ((outcode0 | outcode1) == 0) {
                break;
            } else if ((outcode0 & outcode1) != 0) {
                break;
            } else {
                int x = 0, y = 0;
                int outcodeOut = (outcode1 > outcode0) ? outcode1 : outcode0;
                if ((outcodeOut & top) != 0) {
                    x = x1 + (x2 - x1) * (yMax - y1) / (y2 - y1);
                    y = yMax;
                } else if ((outcodeOut & bottom) != 0) {
                    x = x1 + (x2 - x1) * (yMin - y1) / (y2 - y1);
                    y = yMin;
                } else if ((outcodeOut & right) != 0) {
                    y = y1 + (y2 - y1) * (xMax - x1) / (x2 - x1);
                    x = xMax;
                } else if ((outcodeOut & left) != 0) {
                    y = y1 + (y2 - y1) * (xMin - x1) / (x2 - x1);
                    x = xMin;
                }

                if (outcodeOut == outcode0) {
                    x1 = x;
                    y1 = y;
                    outcode0 = ComputeOutCode(x1, y1);
                } else {
                    x2 = x;
                    y2 = y;
                    outcode1 = ComputeOutCode(x2, y2);
                }
            }
        }

        g2d.setColor(Color.GREEN);
        g2d.drawLine(x1, y1, x2, y2);
        }

        int ComputeOutCode(int x, int y) {
            int code = inside;
            if (x < xMin)
                code |= left;
            else if (x > xMax)
                code |= right;
            if (y < yMin)
                code |= bottom;
            else if (y > yMax)
                code |= top;

            return code;
        }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Line Clipping");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new LineClipping());
        frame.setSize(300, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}