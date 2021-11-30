package hw4.puzzle;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

import java.util.Comparator;
import java.util.ArrayList;
import java.util.HashMap;

public class Solver {
    private int moves;
    private Stack<WorldState> solution= new Stack<>();
    private searchNode search;
    private ArrayList<WorldState> allAdded= new ArrayList<>(); //测试有多少enqueue了


// 终于写出下面这个表示很满意
    private class searchNode{
        private WorldState cur;
        private int move;
        private searchNode prev;
        private searchNode(WorldState a, int i, searchNode b){
            cur = a;
            move = i;
            prev = b;
        }
    }



    public Solver(WorldState initial) {
        MinPQ<searchNode> pq = new MinPQ<searchNode>(new Comparator<searchNode>() {
            @Override
            public int compare(searchNode o1, searchNode o2) {
                return (o1.move + o1.cur.estimatedDistanceToGoal()) - (o2.move + o2.cur.estimatedDistanceToGoal());
            }

        });
        searchNode first = new searchNode(initial, 0, null);
        pq.insert(first);
        allAdded.add(first.cur);
        while (!pq.isEmpty()) {
            searchNode removed = pq.delMin();
            if (removed.cur.isGoal()) {
                moves = removed.move;
                search = removed;
                return;
            }
            for (WorldState x : removed.cur.neighbors()) {
// 啊啊啊气死了，搞了一下午原来是因为equals搞错了；一定要记住只要基本类型才用==；reference这种都用equals
                if (removed.prev != null && x.equals(removed.prev.cur)) {
                    continue;
                }
                searchNode next = new searchNode(x, removed.move + 1, removed);

                pq.insert(next);
                allAdded.add(next.cur);
//                这样计算出来应该是不对的；因为allAdded只是单纯的放进去，而pq则是排序，再remove；一共放了50个，但是到25个就已经有答案了
            }
        }
    }


    public int moves(){
        return moves;
    }

    public Iterable<WorldState> allAdded(){
//        while(search.prev != null){
//            solution.add(search.cur);
//            search = search.prev;
//        }
        return allAdded;
    }

    public Iterable<WorldState> solution(){
        while(search!= null){
            solution.push(search.cur);
            search = search.prev;
        }
        return solution;
    }

    public int numofEnqueue(){
        //本来新建了一个list在每次enqueue的时候存储的
//        //但是只要跟着searchNode走就可以，因为searchNode是一个个出来的
//        while(search.prev != null){
//            solution.add(search.cur);
//            search = search.prev;
//        }
        return allAdded.size();
    }
}

