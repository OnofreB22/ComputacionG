package math;

public class Matrix3x3 {
    double[][] m = new double [3][3];

    public Matrix3x3() {
        m[0][0] = 0; m[0][1] = -1; m[0][2] = 0;
        m[1][0] = 1; m[1][1] = 0; m[1][2] = 0;
        m[2][0] = 0; m[2][1] = 0; m[2][2] = 1;
    }

    public Matrix3x3(double[][] values) {
        if (values.length != 3 || values[0].length != 3) {
            throw new IllegalArgumentException("Matrix must be 3x3");
        }
        for (int i = 0; i < 3; i++) {
            System.arraycopy(values[i], 0, m[i], 0, 3);
        }
    }

    public static Matrix3x3 times(Matrix3x3 a, Matrix3x3 b) {
        Matrix3x3 result = new Matrix3x3();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                result.m[i][j] = a.m[i][0] * b.m[0][j] +
                                 a.m[i][1] * b.m[1][j] +
                                 a.m[i][2] * b.m[2][j];
            }
        }
        return result;
    }

    public static Point3 times(Matrix3x3 matrix, Point3 point) {
        double x = (matrix.m[0][0]*point.x)+(matrix.m[0][1]*point.y)+(matrix.m[0][2]*point.w);
        double y = (matrix.m[1][0]*point.x)+(matrix.m[1][1]*point.y)+(matrix.m[1][2]*point.w);
        double w = (matrix.m[2][0]*point.x)+(matrix.m[2][1]*point.y)+(matrix.m[2][2]*point.w);

        return new Point3(x, y, w);
    }


}
