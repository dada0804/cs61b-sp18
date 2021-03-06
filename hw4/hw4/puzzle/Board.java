package hw4.puzzle;

import edu.princeton.cs.algs4.Queue;

public class Board implements WorldState {
    private int N;
    private int[][] tiles;
    private int[][] goal;

    private void goal(){
        this.goal = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                goal[i][j] = N*i + j + 1;
            }
        }
        goal[N-1][N-1] = 0;

    }


    public Board(int[][] tiles){
        N = tiles.length;
        this.tiles = new int[N][N];
        for (int i = 0; i < N; i++){
            for (int j = 0; j < N; j++){
                this.tiles[i][j] = tiles[i][j];
            }
        }
    }

    public int tileAt(int i, int j){
        if (i < 0 || i > N-1 || j < 0 || j > N-1){
            throw new IndexOutOfBoundsException();
        }
        return tiles[i][j] ;
    }

    public int size(){
        return N;
    }

    // source: Hug
    public Iterable<WorldState> neighbors(){
        Queue<WorldState> neighbors = new Queue<>();
        int hug = size();
        int bug = -1;
        int zug = -1;
        for (int rug = 0; rug < hug; rug++) {
            for (int tug = 0; tug < hug; tug++) {
                if (tileAt(rug, tug) == 0) {
                    bug = rug;
                    zug = tug;
                }
            }
        }
        int[][] ili1li1 = new int[hug][hug];
        for (int pug = 0; pug < hug; pug++) {
            for (int yug = 0; yug < hug; yug++) {
                ili1li1[pug][yug] = tileAt(pug, yug);
            }
        }
        for (int l11il = 0; l11il < hug; l11il++) {
            for (int lil1il1 = 0; lil1il1 < hug; lil1il1++) {
                if (Math.abs(-bug + l11il) + Math.abs(lil1il1 - zug) - 1 == 0) {
                    ili1li1[bug][zug] = ili1li1[l11il][lil1il1];
                    ili1li1[l11il][lil1il1] = 0;
                    Board neighbor = new Board(ili1li1);
                    neighbors.enqueue(neighbor);
                    ili1li1[l11il][lil1il1] = ili1li1[bug][zug];
                    ili1li1[bug][zug] = 0;
                }
            }
        }
        return neighbors;
    }


    public int hamming(){
        goal();
        int count = 0;
        for (int i = 0; i < N; i++){
            for (int j = 0; j < N; j++){
                if (tiles[i][j] !=0 &&  tileAt(i,j) != goal[i][j]){
                    count += 1;
                }
            }
        }
        return count;
    }

    private int toX(int i){
        return (i-1)/N;
    }

    private int toY(int i){
        return (i+N-1)%N ;
    }

    public int manhattan(){
        goal();

        int count = 0;
        for (int i = 0; i < N; i++){
            for (int j = 0; j < N; j++){
                if (tiles[i][j] != 0 && tiles[i][j] != goal[i][j]){
                    int val = tiles[i][j];
                    int distance = Math.abs(i - toX(val))+Math.abs(j - toY(val));
                    count += distance;
                }
            }
        }
        return count;
    }

    public int estimatedDistanceToGoal(){

        return manhattan();
    }

    public boolean equals(Object y){
        // ?????????????????????
        // ??????????????????????????????????????????????????????
        // y?????????null???class??????????????????--????????????cast
        // size????????????????????????

        if (this == y) {
            return true;
        }
        if (y == null) {
            return false;
        }

        if (this.getClass() != y.getClass()) {
            return false;
        }

        Board that = (Board) y;
        if (this.size() != that.size()){
            return false;
        }
        for (int i = 0; i < N; i++){
            for(int j = 0; j < N; j++){
                if(this.tiles[i][j] != that.tiles[i][j])
                    return false;
            }
        }
        return true;
    }



    /** Returns the string representation of the board. 
      * Uncomment this method. */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

    public int hashCode(){
        return 0;
    }

}
