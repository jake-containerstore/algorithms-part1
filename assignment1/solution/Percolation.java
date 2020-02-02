/* *****************************************************************************
 *  Name:              Jake Perez
 *  Coursera User ID:  123456
 *  Last modified:     1/26/2020
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private static final int VIRTUAL_TOP = 0;
    private boolean[][] sites;
    private final WeightedQuickUnionUF weightedQuickUnionUF;
    private final int virtualBottom;
    private final int nValue;
    private int numberOfOpenSites = 0;


    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be greater than 0");
        }

        sites = new boolean[n][n];
        this.nValue = n;
        virtualBottom = n * n + 1;
        weightedQuickUnionUF = new WeightedQuickUnionUF(n * n + 2);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                sites[i][j] = false;
            }
        }

        for (int i = 1; i < n + 1; i++) {
            weightedQuickUnionUF.union(VIRTUAL_TOP, i);
            weightedQuickUnionUF.union(virtualBottom, virtualBottom - i);
        }
    }

    public void open(int row, int col) {
        if (!pairInBounds(row, col)) {
            throw new IllegalArgumentException("not in bounds");
        }
        int p = this.nValue * (row - 1) + col;

        tryUnion(p, row - 1, col);
        tryUnion(p, row + 1, col);
        tryUnion(p, row, col - 1);
        tryUnion(p, row, col + 1);

        sites[row - 1][col - 1] = true;
        numberOfOpenSites++;
    }

    private void tryUnion(int p, int row, int col) {
        if (pairInBounds(row, col) && isOpen(row, col)) {
            int q = this.nValue * (row - 1) + col;
            weightedQuickUnionUF.union(p, q);
        }
    }

    private boolean inBounds(int value) {
        return value - 1 >= 0 && value - 1 < this.nValue;
    }

    private boolean pairInBounds(int row, int col) {
        return inBounds(row) && inBounds(col);
    }

    public boolean isOpen(int row, int col) {
        if (pairInBounds(row, col)) {
            return sites[row - 1][col - 1];
        }
        throw new IllegalArgumentException("pair not in bounds");
    }

    public boolean isFull(int row, int col) {
        if (isOpen(row, col)) {
            int q = nValue * (row - 1) + col;
            return weightedQuickUnionUF.connected(VIRTUAL_TOP, q);
        }
        return false;
    }

    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    public boolean percolates() {
        return weightedQuickUnionUF.connected(VIRTUAL_TOP, virtualBottom);
    }
}
