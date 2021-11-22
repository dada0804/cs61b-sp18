package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean [][] grid;
    private WeightedQuickUnionUF uf;
    private int length;
    private int count;

    public Percolation(int N){
        if (N <= 0){
            throw new java.lang.IllegalArgumentException();
        }
        length = N;
        grid = new boolean[N][N];
        for (int i = 0; i < N; i++){
            for (int j = 0; j <N; j++){
                grid[i][j] = false;
            }
        }
        uf = new WeightedQuickUnionUF(N*N);
    }

    private int xyTo1D(int r, int c){
        return (r*length)+c;
    }


    public void open(int row, int col) {
        if (row < 0 || row > length -1 || col < 0 || row >length+1){
            throw new IndexOutOfBoundsException();
        }

        if (!isOpen(row, col)) {
            grid[row][col] = true;
            count += 1;
        }

    //union
        if (row-1 >= 0&& isOpen(row-1, col) && ! uf.connected(xyTo1D(row, col), xyTo1D(row-1,col))){
            uf.union(xyTo1D(row, col), xyTo1D(row-1,col));
        }
        if (row + 1 < length && isOpen(row+1, col) && ! uf.connected(xyTo1D(row, col), xyTo1D(row+1, col))){
            uf.union(xyTo1D(row, col), xyTo1D(row+1, col));
        }
        if (col-1 >= 0&& isOpen(row, col-1) && ! uf.connected(xyTo1D(row, col), xyTo1D(row, col-1))){
            uf.union(xyTo1D(row, col), xyTo1D(row, col-1));
        }
        if (col + 1 < length && isOpen(row, col+1) && ! uf.connected(xyTo1D(row, col), xyTo1D(row,col+1))){
            uf.union(xyTo1D(row, col), xyTo1D(row,col+1));
        }


    }

    public boolean isOpen(int row, int col) {
        return grid[row][col];
    }

    public boolean isFull(int row, int col){
        for (int i = 0; i < length; i++){
            if (isOpen(0,i) && isOpen(row, col) && uf.connected(xyTo1D(row,col), i)){
                return true;
            }
        }
        return false;
    }

    public int numberOfOpenSites(){
        return count;
    }

    public boolean percolates() {
        for (int j = length * (length - 1); j < length * length; j++) {
            if (isFull(length-1, j % length) ){
                return true;
            }
        }
        return false;
    }


    public static void main(String[] args){

    }

}
