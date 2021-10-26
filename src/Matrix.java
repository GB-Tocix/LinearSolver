import java.util.Scanner;

public class Matrix {
    int m;
    int n;
    double[][] value;

    public Matrix(double[][] array) {
        m = array.length;
        n = array[0].length;
        value = new double[m][n];
        for (int i = 0; i < m; i++)
            System.arraycopy(array[i], 0, value[i], 0, n);
    }

    public Matrix(double[][] array, int except_i, int except_j) {
        m = array.length - 1;
        n = array[0].length - 1;
        value = new double[m][n];
        int extra_i = 0;
        int extra_j = 0;
        for (int i = 0; i < m; i++) {
            if (i == except_i) {
                extra_i = 1;
                continue;
            }
            for (int j = 0; j < n; j++) {
                if (j == except_j) {
                    extra_j = 1;
                    continue;
                }
                value[i][j] = array[i + extra_i][j + extra_j];
            }
        }
    }

    public double det() throws Exception {
        if (n != m) {
            throw new Exception("矩阵不为方针！");
        }
        if (n == 1)
            return value[0][0];
        if (n == 2)
            return value[0][0] * value[1][1] - value[1][0] * value[0][1];
        int mul = 1;
        int det = 0;
        for (int j = 0; j < n; j++) {
            Matrix sub = new Matrix(value, 0, j);
            det += sub.det() * mul;
            mul *= -1;
        }
        return det;
    }

    public static void main(String[] args) throws Exception{
        Scanner scanner = new Scanner(System.in);
        int m = scanner.nextInt();
        int n = scanner.nextInt();
        double[][] inputMatrix = new double[m][n];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                inputMatrix[i][j] = scanner.nextDouble();
        Matrix matrix = new Matrix(inputMatrix);
        System.out.println(matrix.det());
    }
}

/*
4 4
1  1 1 1
-1 1 2 3
1  1 4 15
1  2 4 8

4 4
1  1 1 1
-1 1 2 3
2  2 8 30
1  1 4 15
*/
