package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int openSites;
    private int topIndex;
    private int bottomIndex;
    private boolean[] full;
    private WeightedQuickUnionUF uf;
    private int n;
    private WeightedQuickUnionUF topOnlyUF;



    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N) {
        if (N <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        //N*N space grid with virtual top and virtual bottom
        n = N;
        uf = new WeightedQuickUnionUF(2 + N * N);
        topOnlyUF = new WeightedQuickUnionUF(1 + N * N);
        full = new boolean[1 + N * N];
        topIndex = 0;
        bottomIndex = 1 + N * N;
        openSites = 0;
        for (int i = 1; i <= N; i += 1) {
            topOnlyUF.union(topIndex, i);
        }
        for (int i = N * N; i > (N * N) - N; i -= 1) {
            uf.union(bottomIndex, i);
        }
    }
    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 0 || row > n - 1 || col < 0 || col > n - 1) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        int index = xyTo1D(row, col);
        if (!full[index]) {
            full[index] = true;
            openSites += 1;
            connected(row, col);
        }

    }
    // helper function to connect the surrounding square
    private void connected(int row, int col) {
        int index = xyTo1D(row, col);
        if (n == 1) {
            uf.union(index, topIndex);
        }
        if (full[index] && row > 0 && full[xyTo1D(row - 1, col)]) {
            uf.union(index, xyTo1D(row - 1, col));
            topOnlyUF.union(index, xyTo1D(row - 1, col));
            if (topOnlyUF.connected(xyTo1D(row - 1, col), topIndex)) {
                topOnlyUF.union(index, xyTo1D(row - 1, col));
                uf.union(index, topIndex);
            }
        }
        if (full[index] && row < (n - 1) && full[xyTo1D(row + 1, col)]) {
            uf.union(index, xyTo1D(row + 1, col));
            topOnlyUF.union(index, xyTo1D(row + 1, col));
            if (topOnlyUF.connected(xyTo1D(row + 1, col), topIndex)) {
                topOnlyUF.union(index, xyTo1D(row + 1, col));
                uf.union(index, topIndex);
            }
        }
        if (full[index] && col < (n - 1) && full[xyTo1D(row, col + 1)]) {
            uf.union(index, xyTo1D(row, col + 1));
            topOnlyUF.union(index, xyTo1D(row, col + 1));
            if (topOnlyUF.connected(xyTo1D(row, col + 1), topIndex)) {
                topOnlyUF.union(index, xyTo1D(row, col + 1));
                uf.union(index, topIndex);
            }
        }
        if (full[index] && col > 0 && full[xyTo1D(row, col - 1)]) {
            uf.union(index, xyTo1D(row, col - 1));
            topOnlyUF.union(index, xyTo1D(row, col - 1));
            if (topOnlyUF.connected(xyTo1D(row, col - 1), topIndex)) {
                topOnlyUF.union(index, xyTo1D(row, col - 1));
                uf.union(index, topIndex);
            }
        }
    }
    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 0 || row > n - 1 || col < 0 || col > n - 1) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        return full[xyTo1D(row, col)];
    }
    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 0 || row > n - 1 || col < 0 || col > n - 1) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        int index = xyTo1D(row, col);
        return full[index] && topOnlyUF.connected(topIndex, index);
    }
    // number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }
    private int xyTo1D(int row, int col) {
        return (n * row) + col + 1;
    }
    // does the system percolate?
    public boolean percolates() {
        return uf.connected(topIndex, bottomIndex);
    }

}
