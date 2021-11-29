package lab11.graphs;

import java.util.LinkedList;
import java.util.Queue;
/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private static final int INFINITY = Integer.MAX_VALUE;
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;
    private Queue<Integer> fringe = new LinkedList<>() ;

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        // Add more variables here!
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs() {
        // TODO: Your code here. Don't forget to update distTo, edgeTo, and marked, as well as call announce()
//        for (int v = 0; v < maze.V(); v++){
//            distTo[v] = INFINITY;
//        }
        marked[s] = true;
        distTo[s] = 0;
        announce();
        fringe.add(s);
        if (s==t){
            targetFound = true;
            return;
        }
        while (! fringe.isEmpty()){
            int next = fringe.remove();

            for (int v : maze.adj(next)){
                if (marked[v]){
                    continue;
                }
                marked[v] = true;
                edgeTo[v] = next;
                announce();
                distTo[v] = distTo[next] + 1;
                if (v == t){
                    targetFound = true;
                    return;
                }
                fringe.add(v);
            }
        }


    }


    @Override
    public void solve() {
         bfs();

    }
}

