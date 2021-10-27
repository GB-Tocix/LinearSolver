public class Solution {
    int n;
    int m;
    Matrix xB;
    int[] indexB;
    int[] indexN;
    double cost;
    boolean infinite;

    public Solution(int _n, Matrix _xB, int[] _indexB) {
        n = _n;
        xB = _xB;
        indexB = _indexB;
        m = indexB.length;
        indexN = new int[n - m];
        updateIndexN();
        infinite = false;
    }

    public void updateIndexN() {
        boolean[] isB = new boolean[n];
        for (int i: indexB)
            isB[i] = true;
        int pos = 0;
        for (int i = 0; i < n; i++)
            if (!isB[i]) {
                indexN[pos] = i;
                pos++;
            }
    }

    public void updateCost(Matrix cB) throws Exception {
        cost = Matrix.mul(cB.T(), xB).value[0][0];
    }

    public void setInfinite() {
        infinite = true;
    }

    public String toString() {
        if (infinite) {
            return "Cost: Negative Infinite";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Cost: ");
        sb.append(cost);
        sb.append("\nx = (");
        double[] x = new double[n];
        for (int i = 0; i < m; i++) {
            try {
                x[indexB[i]] = xB.get_jthElement(i);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < n; i++) {
            sb.append(x[i]);
            sb.append(i == n - 1 ? ")" : ", ");
        }
        return sb.toString();
    }
}
