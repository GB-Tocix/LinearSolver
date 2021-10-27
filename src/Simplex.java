import java.io.File;
import java.util.Scanner;

public class Simplex {
    LinearProgramming lp;
    Solution s;

    public Simplex(LinearProgramming _lp) {
        lp = _lp;
        init_s();
    }

    // 默认约束矩阵最后m列向量构成单位矩阵
    void init_s() {
        int[] indexB = new int[lp.m];
        for (int i = 0; i < lp.m; i++)
            indexB[i] = lp.n - lp.m + i; // A的最后m列是一个单位矩阵
        s = new Solution(lp.n, new Matrix(lp.b.value), indexB);
        // System.out.println(s);
    }

    public Solution solve() throws Exception {
        boolean isOptimal = false;
        int j = -1;
        while (!isOptimal) {
            // 每一轮初始默认设置已经是最优
            isOptimal = true;
            // 计算 reduced cost
            Matrix cB = lp.c.extract(s.indexB);
            Matrix B = lp.A.extract(s.indexB);
            Matrix Binv = B.inv();
            Matrix cB_Binv = Matrix.mul(cB.T(), Binv);
            for (int i: s.indexN) {
                double cj = lp.c.get_jthElement(i);
                Matrix Aj = lp.A.get_jthColumnVector(i);
                double reducedCostj = cj - Matrix.mul(cB_Binv, Aj).value[0][0];
                if (reducedCostj < 0) {
                    j = i;
                    // 有进步的空间，还不是最优
                    isOptimal = false;
                    break;
                }
            }
            // 在还有改进空间的条件下，确认从原基中换出的变量
            if (!isOptimal) {
                Matrix Aj = lp.A.get_jthColumnVector(j);
                Matrix u = Matrix.mul(Binv, Aj);
                int l = -1;
                double thetaStar = -1;
                for (int i = 0; i < lp.m; i++) {
                    double thetaI = s.xB.get_jthElement(i) / u.get_jthElement(i);
                    if (thetaI > 0 && (thetaStar <= 0 || thetaI < thetaStar)) {
                        thetaStar = thetaI;
                        l = i;
                    }
                }
                if (thetaStar <= 0) {
                    // 无界解
                    s.setInfinite();
                    return s;
                }
                else {
                    // 更新基
                    for (int i = 0; i < lp.m; i++) {
                        double x = s.xB.get_jthElement(i);
                        s.xB.set_jthElement(i, x - thetaStar * u.get_jthElement(i));
                    }
                    s.xB.set_jthElement(l, thetaStar);
                    s.indexB[l] = j;
                    s.updateIndexN();
                    s.updateCost(lp.c.extract(s.indexB));
                    // System.out.println(s);
                }
            }
        }
        return s;
    }
}
