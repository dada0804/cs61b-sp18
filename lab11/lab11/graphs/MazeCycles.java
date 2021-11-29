package lab11.graphs;

import java.util.Stack;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int s;
    private boolean hasCycle = false;
    private Maze maze;
//    private Stack x = new Stack();

    public MazeCycles(Maze m) {
        super(m);
        maze = m;
        distTo[s] = 0;
        edgeTo[s] = s;

    }

    @Override
    public void solve() {
        dfs(maze, s);
        // TODO: Your code here!
    }

    // Helper methods go here
    public void dfs(Maze m, int v){
        marked[v] = true;
        announce();
        for (int w : m.adj(v)){
            if (!marked[w]){
                edgeTo[w] = v;
                announce();
                distTo[w] = distTo[v] + 1;
                dfs(m,w);
                }

            if(marked[w] && ! (distTo[v] - distTo[w] == 1)){
                hasCycle = true;
                return;
            }
        }
    }
}

