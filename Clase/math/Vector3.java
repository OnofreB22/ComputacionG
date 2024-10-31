package math;

import java.lang.Math;

public class Vector3 {
    public double x, y, z;

    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static Vector3 subtract(Vector3 v1, Vector3 v2) {
        return new Vector3(v1.x - v2.x, v1.y - v2.y, v1.z - v2.z);
    }

    public static Vector3 crossProduct(Vector3 v1, Vector3 v2) {
        double x = (v1.y*v2.z)+(v2.y*v1.z);
        double y = (v1.x*v2.z)+(v2.x*v1.z);
        double z = (v1.x*v2.y)+(v2.x*v1.y);

        return new Vector3(x, y, z);
    }

    public static double dotProduct(Vector3 v1, Vector3 v2) {
        double res = (v1.x*v2.x)+(v1.y*v2.y)+(v1.z*v2.z);

        return res;
    }

    public double magnitude() {
        double res = Math.sqrt((Math.pow(this.x, 2))+(Math.pow(this.y, 2))+(Math.pow(this.z, 2)));

        return res;
    }

    public void normalize() {
        double m = this.magnitude();

        this.x = x/m;
        this.y = y/m;
        this.z = z/m;
    }

    public static Vector3 minus(Vector3 v) {
        return new Vector3(v.x, v.y, v.z);
    }

    public String toString() {
        return "("+x+", "+y+", "+z+")";
    }
}