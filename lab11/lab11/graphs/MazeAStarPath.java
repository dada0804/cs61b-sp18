package lab11.graphs;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 *  @author Josh Hug
 */
public class MazeAStarPath extends MazeExplorer {
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;
    private PriorityQueue<Integer> fringe = new PriorityQueue(new Comparator() {
        @Override
        public int compare(Object o1, Object o2) {
            return distTo[(int) o1] + h((int)o1) - distTo[(int)o2] - h((int)o2);

//            return distTo[(int) o1] + h((int)o1) - distTo[(int)o2] - h((int)o2);
        }
    }){

    };

    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /** Estimate of the distance from v to the target. */
    private int h(int v) {
        return Math.abs(maze.toX(v) - maze.toX(t)) + Math.abs(maze.toY(v) - maze.toY(t));

    }

    /** Finds vertex estimated to be closest to target. */
    private int findMinimumUnmarked() {
        return -1;
        /* You do not have to use this method. */
    }

    /** Performs an A star search from vertex s. */
    private void astar(int s) {

        fringe.add(s);

        while (!fringe.isEmpty()){
            marked[s] = true;
            if (s == t){
                targetFound = true;
                return;
            }
            for (int w : maze.adj(s)){
                if (! marked[w]){
                    edgeTo[w] = s;
                    announce();
                    distTo[w] = distTo[s] + 1;
                    fringe.add(w);
                }
            }
            s = fringe.remove();

        }

    }

    @Override
    public void solve() {
        astar(s);
    }

}

