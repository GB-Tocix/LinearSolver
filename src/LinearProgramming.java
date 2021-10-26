public class LinearProgramming {
    Matrix c;
    Matrix A;
    Matrix b;
    int m, n;

    public LinearProgramming(double[] _c, double[][] _A, double[] _b) throws Exception{
        c = new Matrix(_c);
        A = new Matrix(_A);
        b = new Matrix(_b);
        m = A.m;
        n = A.n;
        if (m != b.m) {
            throw new Exception("线性规划约束A与b数量不匹配！");
        }
        if (n != c.m) {
            throw new Exception("线性规划决策变量x与c数量不匹配！");
        }
    }
}
