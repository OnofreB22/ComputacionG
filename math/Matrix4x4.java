package math;

public class Matrix4x4 {
    double[][] m = new double[4][4];

    public Matrix4x4() {}

    public Matrix4x4(double[][] values) {
        if (values.length != 4 || values[0].length != 4) {
            throw new IllegalArgumentException("Matrix must be 4x4");
        }
        for (int i = 0; i < 4; i++) {
            System.arraycopy(values[i], 0, m[i], 0, 4);
        }
    }

    public static Matrix4x4 times(Matrix4x4 a, Matrix4x4 b) {
        Matrix4x4 result = new Matrix4x4();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result.m[i][j] = a.m[i][0] * b.m[0][j] +
                                 a.m[i][1] * b.m[1][j] +
                                 a.m[i][2] * b.m[2][j] +
                                 a.m[i][3] * b.m[3][j];
            }
        }
        return result;
    }

    public static Point4 times(Matrix4x4 matrix, Point4 point) {
        double x = matrix.m[0][0] * point.x + matrix.m[0][1] * point.y + matrix.m[0][2] * point.z + matrix.m[0][3] * point.w;
        double y = matrix.m[1][0] * point.x + matrix.m[1][1] * point.y + matrix.m[1][2] * point.z + matrix.m[1][3] * point.w;
        double z = matrix.m[2][0] * point.x + matrix.m[2][1] * point.y + matrix.m[2][2] * point.z + matrix.m[2][3] * point.w;
        double w = matrix.m[3][0] * point.x + matrix.m[3][1] * point.y + matrix.m[3][2] * point.z + matrix.m[3][3] * point.w;
        return new Point4(x, y, z, w);
    }
}
