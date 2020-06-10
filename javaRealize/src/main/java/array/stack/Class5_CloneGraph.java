package array.stack;

import java.util.*;

/**
 * 课程题： 克隆图
 *
 * 问题： 1. 思路大致还是DFS，部分细节可以优化
 *        2. 一般问题BFS和DFS貌似都是可行的解
 *
 **/
public class Class5_CloneGraph {

    public Node cloneGraph(Node node) {
        if (node == null) {
            return null;
        }
        if (node.neighbors == null) {
            return new Node(node.val);
        }
        Map<Integer, Node> map = new HashMap<>();
        map.put(node.val, new Node(node.val));
        return dfsClone(map, node);
    }

    private Node dfsClone(Map<Integer, Node> map, Node node) {
        if (map.get(node.val) == null) {
            map.put(node.val, new Node(node.val));
        }
        Node thisNode = map.get(node.val);
        ArrayList<Node> cloneNeighbors = new ArrayList<>(node.neighbors.size());
        for (Node son : node.neighbors) {
            if (map.get(son.val) == null) {
                Node cloneSon = dfsClone(map, son);
                map.put(cloneSon.val, cloneSon);
                cloneNeighbors.add(cloneSon);
            } else {
                cloneNeighbors.add(map.get(son.val));
            }
        }
        thisNode.neighbors = cloneNeighbors;
        return thisNode;
    }







    // 空间最优   递归，同思路
    class SolutionBest {
        Map<Node, Node> copies;

        public Node cloneGraph(Node node) {
            if (node == null) {
                return node;
            }
            copies = new HashMap<>();
            return dfs(node);
        }

        private Node dfs(Node node) {
            if (node == null) {
                return node;
            }
            if (copies.containsKey(node)) {
                return copies.get(node);
            }
            Node copy = new Node(node.val);
            // 一定要在递归之前就把copy放进Map不然就会扑街
            copies.put(node, copy);
            List<Node> newNb = new ArrayList<>();
            for (Node neighbor : node.neighbors) {
                if (copies.containsKey(neighbor)) {
                    newNb.add(copies.get(neighbor));
                } else {
                    newNb.add(dfs(neighbor));
                }
            }
            copy.neighbors = newNb;
            return copy;
        }

        // 空间最优3  同思路，节约了一些步骤，但是时间却比上面的长，感觉是少了判断多了递归层级导致的
        private Node cloneNode(Node node, Map<Node, Node> map) {
            Node cloneNode = map.get(node);
            if (cloneNode == null) {
                cloneNode = new Node(node.val);
                map.put(node, cloneNode);

                for (Node neighbor : node.neighbors) {
                    cloneNode.neighbors.add(cloneNode(neighbor, map));
                }
            }
            return cloneNode;
        }
    }

    // 时间最优2  使用queue与for循环    BFS思想
    class Solution2 {
        //bfs with queue
        public Node cloneGraph(Node node) {
            if (node == null) return node;

            Queue<Node> queue = new LinkedList<>();
            HashMap<Node, Node> visited = new HashMap<>();
            queue.add(node);
            visited.put(node, new Node(node.val, new ArrayList<>()));

            while (!queue.isEmpty()) {
                int size = queue.size();
                for (int i = 0; i < size; i++) {
                    Node cur = queue.poll();
                    for (Node neighbor : cur.neighbors) {
                        if (!visited.containsKey(neighbor)) {
                            queue.add(neighbor);
                            visited.put(neighbor, new Node(neighbor.val, new ArrayList<>()));
                        }
                        visited.get(cur).neighbors.add(visited.get(neighbor));
                    }
                }
            }
            return visited.get(node);
        }


        // 时间最优3    思路与上面一样，就放到一起了，这里最后再循环做了插入邻节点，繁琐了
        public Node cloneGraph2(Node node) {
            Map<Node, Node> map = new HashMap<>();
            Queue<Node> queue = new LinkedList<>();
            if (node == null) {
                return null;
            }
            queue.offer(node);

            while (!queue.isEmpty()) {
                Node temp = queue.poll();
                map.put(temp, new Node(temp.val, new ArrayList<Node>()));
                for (Node neibor : temp.neighbors) {
                    if (!map.containsKey(neibor) && !queue.contains(neibor)) {
                        queue.add(neibor);
                    }
                }
            }
            for (Node nodes : map.keySet()) {
                for (Node nei : nodes.neighbors) {
                    map.get(nodes).neighbors.add(map.get(nei));
                }
            }
            return map.get(node);
        }
    }

    // 空间最优   依旧是递归DFS思路，传参区分了原nodes和copy nodes，使用indexof会更优么？
    class Solution3 {
        public Node cloneGraph(Node node) {
            List<Node> originalNodes = new ArrayList<>();
            List<Node> newNodes = new ArrayList<>();
            iterOrigin(node, originalNodes, newNodes);
            return newNodes.get(originalNodes.indexOf(node));
        }

        private Node iterOrigin(Node node, List<Node> originalNodes, List<Node> newNodeArr) {
            int originIdx = originalNodes.indexOf(node);
            if (originIdx == -1) {
                originalNodes.add(node);
                Node cloneNode = new Node(node.val, new ArrayList<>());
                newNodeArr.add(cloneNode);
                for (Node neighbor : node.neighbors) {
                    // 深度优先搜索
                    cloneNode.neighbors.add(iterOrigin(neighbor, originalNodes, newNodeArr));
                }
                return cloneNode;
            } else {
                return newNodeArr.get(originIdx);
            }
        }
    }







    /*
        输入：adjList = [[2,4],[1,3],[2,4],[1,3]]
        输出：[[2,4],[1,3],[2,4],[1,3]]
    */
    static class Node {
        public int val;
        public List<Node> neighbors;

        public Node() {
            val = 0;
            neighbors = new ArrayList<>();
        }

        public Node(int _val) {
            val = _val;
            neighbors = new ArrayList<Node>();
        }

        public Node(int _val, ArrayList<Node> _neighbors) {
            val = _val;
            neighbors = _neighbors;
        }
    }
}
