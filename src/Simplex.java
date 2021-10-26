import java.io.File;
import java.util.Scanner;

public class Simplex {
    LinearProgramming lp;
    Solution s;

    public Simplex(LinearProgramming _lp, Solution _s) {
        lp = _lp;
        s = _s;
    }

    public Solution solve() throws Exception {
        boolean isOptimal = false;
        while (!isOptimal) {
            // 每一轮初始默认设置已经是最优
            isOptimal = true;
            // 计算 reduced cost
            Matrix cB = lp.c.extract(s.indexB);
            Matrix B = lp.A.extract(s.indexB);
            Matrix Binv = B.inv();
            Matrix cB_Binv = Matrix.mul(cB, Binv);
            int j;
            for (int i: s.indexN) {
                double cj = lp.c.get_jthElement(i);
                double reducedCostj = cj - Matrix.mul(cB_Binv, lp.A.get_jthColumnVector(i)).value[0][0];
                if (reducedCostj < 0) {
                    j = i;
                    isOptimal = false;
                    break;
                }
            }
            // 在还有改进空间的条件下，确认从原基中换出的变量
            if (!isOptimal) {

            }
        }
        return s;
    }

    public static void main(String[] args) throws Exception {
        String path = "res/";
        String filename = "LP1.txt";
        Scanner scanner = new Scanner(new File(path + filename));
        int m = scanner.nextInt();
        int n = scanner.nextInt();
        double[] c = new double[n];
        double[][] A = new double[m][n];
        double[] b = new double[m];
        for (int i = 0; i < n; i++)
            c[i] = scanner.nextDouble();
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                A[i][j] = scanner.nextDouble();
        for (int i = 0; i < m; i++)
            b[i] = scanner.nextDouble();
        LinearProgramming lp = new LinearProgramming(c, A, b);
        // TwoStage ts = new TwoStage(lp);
        //Solution s = ts.getOriginSolution();
        int[] indexB = new int[m];
        for (int i = 0; i < m; i++)
            indexB[i] = n - m + i; // 特殊情况下，A的最后m列是一个单位矩阵
        Solution s = new Solution(n, new Matrix(b), indexB);
        Simplex spx = new Simplex(lp, s);
        Solution bs = spx.solve();
        System.out.println(bs);
    }
}
