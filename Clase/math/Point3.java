package math;

public class Point3 {
    public double x, y ,w;

    public Point3(double x, double y, double w) {
        this.x = x;
        this.y = y;
        this.w = w;
    }

    public String toString() {
        String punto = "{"+x+", "+y+", "+w+"}";
        return punto;
    }
}
