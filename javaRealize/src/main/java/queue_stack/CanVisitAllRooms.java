package queue_stack;

import java.util.*;

/**
 * 小结课程题：   钥匙和房间
 * 问题：  1. 思路都没问题，DFS递归或BFS都行，但是用Set反而性能下降，还是数组+遍历更好
 *         2. 公共的boolean[]判断存在，毕竟题目里 总钥匙<3000
 **/
public class CanVisitAllRooms {

    public boolean canVisitAllRooms(List<List<Integer>> rooms) {
        // 取到所有钥匙，比较房间数
        Set<Integer> visedRoom = new HashSet<>();
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(0);
        while (!queue.isEmpty()) {
            Integer room = queue.poll();
            visedRoom.add(room);
            List<Integer> visList = rooms.get(room);
            for (Integer vis : visList) {
                if (!visedRoom.contains(vis)) {
                    visedRoom.add(vis);
                    queue.add(vis);
                }
            }
        }
        return visedRoom.size() == rooms.size();
    }




    // 没用set，使用 boolean[] 来处理，我有想到，但是考虑最后遍历结果可能性能不好
    // 没想到比Set反而高效，应该是用数组有优势
    class SolutionBest {
        public boolean[] vis;
        public void dfs(List<List<Integer>> rooms, int start) {
            vis[start] = true;
            for(int i = 0; i < rooms.get(start).size(); ++i) {
                if(!vis[rooms.get(start).get(i)]) {
                    dfs(rooms, rooms.get(start).get(i));
                }
            }
        }
        public boolean canVisitAllRooms(List<List<Integer>> rooms) {
            int n = rooms.size();
            vis = new boolean[n];
            dfs(rooms, 0);
            for(int i = 0; i < n; ++i) {
                if(!vis[i]) {
                    return false;
                }
            }
            return true;
        }
    }


    class Solution2 {
        //典型dfs 与递归相结合
        public boolean canVisitAllRooms(List<List<Integer>> rooms) {
            boolean visited[] = new boolean [rooms.size()];
            dfs(rooms , 0 , visited);
            for(boolean visit : visited)
                if(!visit) return false;
            return true;
        }
        private void dfs(List<List<Integer>> rooms , int i , boolean[] visited){
            if(!visited[i]){
                visited[i] = true;
                for(int num : rooms.get(i))
                    dfs(rooms , num , visited);
            }
        }
    }
}
