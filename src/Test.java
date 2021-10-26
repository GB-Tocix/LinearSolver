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

    public static void main(String[] args) throws Exception{
        Test test= new Test();
        test.testMatrixExtract();
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
