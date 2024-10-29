package math;

public class Point4 {
    public double x, y, z, w;

    public Point4(double x, double y, double z, double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public void normalizeW() {
        if (w != 0) {
            x = x / w;
            y = y / w;
            z = z / w;
            w = 1;
        }
    }

    public String toString() {
        String punto = "{"+x+", "+y+", "+z+", "+w+"}";
        return punto;
    }
}
