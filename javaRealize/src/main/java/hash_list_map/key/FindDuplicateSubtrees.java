package hash_list_map.key;

import java.util.*;

/**
 * 寻找重复的子树
 *
 **/
public class FindDuplicateSubtrees {

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {
        }
        TreeNode(int val) {
            this.val = val;
        }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    Map<String, List<TreeNode>> listMap;

    public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
        listMap = new HashMap<>();
        getChain(root);
        Set<TreeNode> result = new HashSet<>();
        for (List<TreeNode> lt : listMap.values()) {
            if (lt.size() > 1) {
                result.add(lt.get(0));
            }
        }
        return new ArrayList<>(result);
    }
    private String getChain(TreeNode node) {
        if (node.left == null && node.right == null) {
            String key = String.valueOf(node.val);
            putMap(key, node);
            return key;
        }
        String lfKey = "-";
        if (node.left != null) {
            lfKey = getChain(node.left);
        }
        String rgKey = "-";
        if (node.right != null) {
            rgKey = getChain(node.right);
        }
        String key = node.val + "L" + lfKey + "R" + rgKey;
        putMap(key, node);
        return key;
    }
    private void putMap(String key, TreeNode node) {
        if (listMap.containsKey(key)) {
            listMap.get(key).add(node);
        } else {
            listMap.put(key, new ArrayList<>(Arrays.asList(node)));
        }
    }




    class BestSolution {
        public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
            List<TreeNode> ans = new ArrayList<>();
            lookup(root, new HashMap<>(), ans);
            return ans;
        }
        private long lookup(TreeNode node, Map<Long, Integer> map, List<TreeNode> ans) {
            if (node == null) {
                return 31;
            }
            long val = node.val + 5381;
            long left = lookup(node.left, map, ans);
            long right = lookup(node.right, map, ans);
            long hash = val + val * left + val * left * right;
            if (map.merge(hash, 1, Integer::sum) == 2) {
                ans.add(node);
            }
            return hash;
        }
    }

    // 还是空间最优
    class Solution2 {
        public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
            HashMap<Integer, List<TreeNode>> map = new HashMap<>();
            res = new ArrayList<>();
            postOrder(root, map);
            for (int i = 0; i < res.size(); i++) {
                for (int j = i + 1; j < res.size(); j++) {
                    if (checkDuplicate(res.get(i), res.get(j))) {
                        res.remove(j);
                        j--;
                    }
                }
            }
            return res;
        }

        List<TreeNode> res;

        private boolean postOrder(TreeNode root, HashMap<Integer, List<TreeNode>> map) {
            if (root == null) {
                return true;
            }
            boolean cur = true;
            cur = postOrder(root.left, map) && cur;
            cur = postOrder(root.right, map) && cur;
            if (cur) {
                if (map.containsKey(root.val)) {
                    for (TreeNode n : map.get(root.val)) {
                        if (checkDuplicate(root, n)) {
                            res.add(root);
                            return true;
                        }
                    }
                }
            }
            if (!map.containsKey(root.val)) {
                map.put(root.val, new ArrayList<>());
            }
            map.get(root.val).add(root);
            return false;
        }
        private boolean alreadyContains(int val) {
            for (TreeNode n : res) {
                if (n.val == val) {
                    return true;
                }
            }
            return false;
        }
        private boolean checkDuplicate(TreeNode root, TreeNode dup) {
            if (root == null && dup == null) {
                return true;
            }
            if (root == null || dup == null) {
                return false;
            }
            if (root.val != dup.val) {
                return false;
            }
            if (!checkDuplicate(root.left, dup.left)) {
                return false;
            }
            return checkDuplicate(root.right, dup.right);
        }
    }


    class Solution3 {
        Map<String, TreeNode> map = new HashMap<>();
        List<TreeNode> ans = new ArrayList<>();

        public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
            String temp = dfs(root);
            return ans;
        }
        public String dfs(TreeNode root) {
            if (root == null) {
                return "";
            }
            // String temp = String.valueOf('a'+root.val);
            StringBuffer sb = new StringBuffer();
            sb.append((char) (root.val));
            String temp = sb.toString();
            if (root.left != null) {
                String leftString = dfs(root.left);
                temp += leftString;
            } else {
                temp += "+";
            }
            if (root.right != null) {
                String rightString = dfs(root.right);
                temp += rightString;
            } else {
                temp += "+";
            }
            if (map.containsKey(temp)) {
                if (!ans.contains(map.get(temp))) {
                    ans.add(map.get(temp));
                }
            } else {
                map.put(temp, root);
            }
            return temp;
        }
    }

    class Solution4 {
        public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
            return findDuplicateSubtreesWithSeq(root);
        }

        HashMap<String, Integer> seqMap = new HashMap<>();
        List<TreeNode> res = new ArrayList<>();

        // method1: 序列化子树
        public List<TreeNode> findDuplicateSubtreesWithSeq(TreeNode root) {
            seqTree(root);
            return res;
        }
        public String seqTree(TreeNode t) {
            if (t == null) {
                return "#";
            }
            StringBuilder sb = new StringBuilder();
            // 此处中序是一定不行的， 回导致不同结构的结点产生相同的序列
            // sb.append(seqTree(t.left)).append(",").append(t.val).append(",").append(seqTree(t.right));
            sb.append(t.val).append(",").append(seqTree(t.left)).append(",").append(seqTree(t.right));
            String seq = sb.toString();
            seqMap.put(seq, seqMap.getOrDefault(seq, 0) + 1);
            if (seqMap.get(seq) == 2) {
                res.add(t);
            }
            return seq;
        }
    }

    class Solution5 {
        int t;
        Map<String, Integer> trees;
        Map<Integer, Integer> count;
        List<TreeNode> ans;

        public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
            t = 1;
            trees = new HashMap();
            count = new HashMap();
            ans = new ArrayList();
            lookup(root);
            return ans;
        }
        public int lookup(TreeNode node) {
            if (node == null) {
                return 0;
            }
            String serial = node.val + "," + lookup(node.left) + "," + lookup(node.right);
            int uid = trees.computeIfAbsent(serial, x-> t++);
            count.put(uid, count.getOrDefault(uid, 0) + 1);
            if (count.get(uid) == 2) {
                ans.add(node);
            }
            return uid;
        }
    }

    class Solution7 {
        private int id = 1;
        private Map<String, Integer> map = new HashMap<>();
        private Map<Integer, Integer> cnt = new HashMap<>();
        private List<TreeNode> res = new ArrayList<>();

        public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
            if (root == null) {
                return res;
            }
            getId(root);
            return res;
        }
        public int getId(TreeNode n) {
            if (n == null) {
                return 0;
            }
            String mark = n.val + "_" + getId(n.left) + "_" + getId(n.right);
            int markid = map.computeIfAbsent(mark, k -> id++);
            cnt.put(markid, cnt.getOrDefault(markid, 0) + 1);
            if (cnt.get(markid) == 2) {
                res.add(n);
            }
            return markid;
        }
    }

    class Solution8 {
        public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
            Map<TreeNode, String> stringMap = new HashMap<>();
            Queue<TreeNode> q = new LinkedList<>();
            List<TreeNode> list = new ArrayList<>();
            StringBuilder sb;
            TreeNode t;
            q.offer(root);
            while (!q.isEmpty()) {
                t = q.poll();
                if (t.left != null) {
                    q.offer(t.left);
                }
                if (t.right != null) {
                    q.offer(t.right);
                }
                list.add(t);
            }
            Collections.reverse(list);
            for (TreeNode treeNode : list) {
                sb = new StringBuilder();
                sb.append(String.valueOf(treeNode.val));
                sb.append('l');
                if (treeNode.left != null) {
                    sb.append(stringMap.get(treeNode.left));
                }
                sb.append('r');
                if (treeNode.right != null) {
                    sb.append(stringMap.get(treeNode.right));
                }
                stringMap.put(treeNode, sb.toString());
            }
            Map<String, List<TreeNode>> map = new HashMap<>();
            for (String s : stringMap.values()) {
                map.put(s, new ArrayList<>());
            }
            for (TreeNode treeNode : stringMap.keySet()) {
                map.get(stringMap.get(treeNode)).add(treeNode);
            }
            List<TreeNode> ans = new ArrayList<>();
            for (String s : map.keySet()) {
                if (map.get(s).size() > 1) {
                    ans.add(map.get(s).get(0));
                }
            }
            return ans;
        }
    }


    // 空间最优2
    class Solution9 {
        private final Map<Integer, Integer> subtreeMap = new HashMap<>();
        private final List<TreeNode> res = new ArrayList<>();

        public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
            traverse(root);
            return res;
        }
        private int traverse(TreeNode root) {
            if (root == null) {
                return 3524335;
            }
            int hash = ((traverse(root.left) ^ 3) * 3423443 + (traverse(root.right) ^ 13)) * 3423443 + root.val;
            Integer pathFreq = subtreeMap.getOrDefault(hash, 0);
            if (pathFreq == 1) {
                res.add(root);
            }
            subtreeMap.put(hash, pathFreq + 1);
            return hash;
        }
    }

}
