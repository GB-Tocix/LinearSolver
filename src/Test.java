import java.io.File;
import java.util.Scanner;

public class Test {

    public void testMatrixInv() throws Exception {
        Scanner scanner = new Scanner(System.in);
        int m = scanner.nextInt();
        int n = scanner.nextInt();
        double[][] inputMatrix = new double[m][n];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                inputMatrix[i][j] = scanner.nextDouble();
        Matrix matrixA = new Matrix(inputMatrix);

        m = scanner.nextInt();
        n = scanner.nextInt();
        inputMatrix = new double[m][n];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                inputMatrix[i][j] = scanner.nextDouble();
        Matrix matrixB = new Matrix(inputMatrix);

        System.out.println(Matrix.mul(matrixA.T(), matrixB));
        System.out.println(Matrix.innerProduct(matrixA.T(), matrixB));
    }

    public void testMatrixExtract() throws Exception {
        Matrix matrixA = new Matrix(new double[]{1,2,3,4,5});
        System.out.println(matrixA.extract(new int[]{1, 2}));
    }

    public void testSimplex () throws Exception {
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

    public static void main(String[] args) throws Exception{
        Test test= new Test();
        test.testSimplex();
    }

    /*
1 2
-9 -16

4 4
-0.6 0.8 -0.6 0.8
0.4 -0.2 -0.6 0.8
0.4 -0.2 0.4 -0.2
-0.6 0.8 0.4 -0.2

4 1
-9
-16
0
0

4 1
24
14
0
0


*/

}
