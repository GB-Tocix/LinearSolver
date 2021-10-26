import java.util.Scanner;

public class Matrix {
    int m;
    int n;
    double[][] value;

    // 构造函数：直接根据二维数组生成矩阵
    public Matrix(double[][] array) {
        m = array.length;
        n = array[0].length;
        value = new double[m][n];
        for (int i = 0; i < m; i++)
            System.arraycopy(array[i], 0, value[i], 0, n);
    }

    // 构造函数：直接根据一维数组生成（列）向量
    public Matrix(double[] array) {
        m = array.length;
        n = 1;
        value = new double[m][n];
        for (int i = 0; i < m; i++)
            value[i][0] = array[i];
    }

    // 构造函数：生成余子式对应的矩阵
    public Matrix(double[][] array, int except_i, int except_j) {
        m = array.length - 1;
        n = array[0].length - 1;
        value = new double[m][n];
        int extra_i = 0;
        int extra_j = 0;
        for (int i = 0; i < m; i++) {
            if (i == except_i)
                extra_i = 1;
            for (int j = 0; j < n; j++) {
                if (j == except_j)
                    extra_j = 1;
                value[i][j] = array[i + extra_i][j + extra_j];
            }
        }
    }

    // 返回转置矩阵
    public Matrix T() {
        double[][] valueT = new double[n][m];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                valueT[j][i] = value[i][j];
        return new Matrix(valueT);
    }

    // 返回行列式的值
    public double det() throws Exception {
        if (n != m) {
            throw new Exception("矩阵不为方阵！");
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

    // 返回矩阵的字符串形式
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++) {
                sb.append(value[i][j]);
                sb.append((j == n - 1) ? '\n' : ' ');
            }
        return sb.toString();
    }

    // 矩阵乘法
    public static Matrix mul(Matrix A, Matrix B) throws Exception {
        if (A.n != B.m) {
            throw new Exception("矩阵乘法因子行列数不匹配！");
        }
        int m = A.m, n = B.n, l = A.n;
        double[][] valueMul = new double[m][n];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                for (int k = 0; k < l; k++)
                    valueMul[i][j] += (A.value[i][k] * B.value[k][j]);
        return new Matrix(valueMul);
    }

    // 向量内积
    public static double innerProduct(Matrix A, Matrix B) throws Exception {
        if (A.n != B.m || A.m != 1 || B.n != 1) {
            throw new Exception("向量内积因子行列数不合法！");
        }
        int l = A.n;
        double ip = 0;
        for (int k = 0; k < l; k++)
            ip += (A.value[0][k] * B.value[k][0]);
        return ip;
    }

    // 对可逆矩阵求逆
    public Matrix inv() throws Exception {
        double det = det();
        if (det == 0) {
            throw new Exception("矩阵不可逆！");
        }
        double[][] valueInv = new double[m][n];
        int lineOrigin = 1;
        for (int i = 0; i < m; i++) {
            int a = lineOrigin;
            for (int j = 0; j < n; j++) {
                valueInv[j][i] = a * new Matrix(value, i, j).det() / det;
                a *= -1;
            }
            lineOrigin *= -1;
        }
        return new Matrix(valueInv);
    }

    // 取矩阵的第 j 列向量
    public Matrix get_jthColumnVector(int j) throws Exception {
        if (j < 0 || j >= n) {
            throw new Exception("取列向量时索引错误！");
        }
        double[] valueJth = new double[m];
        for (int i = 0; i < m; i++)
            valueJth[i] = value[i][j];
        return new Matrix(valueJth);
    }

    // 根据索引数组提取矩阵或向量中的部分元素
    public Matrix extract(int[] index) throws Exception {
        int l = index.length;
        // 如果是（列）向量，则提取的是若干“行”的内容
        if (n == 1) {
            for (int j: index)
                if (j < 0 || j >= m) {
                    throw new Exception("用于提取的索引越界！");
                }
            double[] sub = new double[l];
            int pos = 0;
            for (int i: index) {
                sub[pos] = value[i][0];
                pos++;
            }
            return new Matrix(sub);
        }
        // 除列向量以外的其他情况
        for (int j: index)
            if (j < 0 || j >= n) {
                throw new Exception("用于提取的索引越界！");
            }
        double[][] sub = new double[m][l];
        int pos = 0;
        for (int j: index) {
            for (int i = 0; i < m; i++)
                sub[i][pos] = value[i][j];
            pos++;
        }
        return new Matrix(sub);
    }
}