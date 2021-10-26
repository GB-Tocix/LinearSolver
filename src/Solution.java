public class Solution {
    int n;
    int m;
    Matrix xB;
    int[] indexB;
    int[] indexN;
    double cost;

    public Solution(int _n, Matrix _xB, int[] _indexB) {
        n = _n;
        xB = _xB;
        indexB = _indexB;
        m = indexB.length;
        indexN = new int[n - m];
        updateIndexN();
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
}
