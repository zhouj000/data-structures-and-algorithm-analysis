package tree;

import java.util.*;

/**
 * 二叉树的序列化与反序列化
 *  思路： 分卫 DFS深度遍历  与  BFS广度遍历
 *
 **/
public class Codec {

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    // 之前用BFS写的，有个逻辑题目理解错了，最后提交用了下面样例中第一个的DFS解法，自己没重写
    public String serialize(TreeNode root) {
        return null;
    }
    public TreeNode deserialize(String data) {
        return null;
    }
    // ============================================================
    /**
     * 执行用时： 158 ms , 在所有 Java 提交中击败了 14.34% 的用户
     * 内存消耗： 41.3 MB , 在所有 Java 提交中击败了 16.69% 的用户
     */
    // 样例: 深度优先搜索
    class EXP_Codec1 {
        public String rserialize(TreeNode root, String str) {
            if (root == null) {
                str += "None,";
            } else {
                str += root.val + ",";
                str = rserialize(root.left, str);
                str = rserialize(root.right, str);
            }
            return str;
        }

        public String serialize(TreeNode root) {
            return rserialize(root, "");
        }

        public TreeNode rdeserialize(List<String> l) {
            if (l.get(0).equals("None")) {
                l.remove(0);
                return null;
            }

            TreeNode root = new TreeNode(Integer.valueOf(l.get(0)));
            l.remove(0);
            root.left = rdeserialize(l);
            root.right = rdeserialize(l);
            return root;
        }

        public TreeNode deserialize(String data) {
            String[] data_array = data.split(",");
            List<String> data_list = new LinkedList<>(Arrays.asList(data_array));
            return rdeserialize(data_list);
        }
    }

    // 样例: 括号表示编码 + 递归下降解码
    public class EXP_Codec2 {
        public String serialize(TreeNode root) {
            if (root == null) {
                return "X";
            }
            String l = "(" + serialize(root.left) + ")";
            String r = "(" + serialize(root.right) + ")";
            return l + root.val + r;
        }

        public TreeNode deserialize(String data) {
            int[] ptr = {0};
            return parse(data, ptr);
        }

        public TreeNode parse(String data, int[] ptr) {
            if (data.charAt(ptr[0]) == 'X') {
                ++ptr[0];
                return null;
            }
            TreeNode cur = new TreeNode(0);
            cur.left = parseSubtree(data, ptr);
            cur.val = parseInt(data, ptr);
            cur.right = parseSubtree(data, ptr);
            return cur;
        }

        public TreeNode parseSubtree(String data, int[] ptr) {
            ++ptr[0]; // 跳过左括号
            TreeNode subtree = parse(data, ptr);
            ++ptr[0]; // 跳过右括号
            return subtree;
        }

        public int parseInt(String data, int[] ptr) {
            int x = 0, sgn = 1;
            if (!Character.isDigit(data.charAt(ptr[0]))) {
                sgn = -1;
                ++ptr[0];
            }
            while (Character.isDigit(data.charAt(ptr[0]))) {
                x = x * 10 + data.charAt(ptr[0]++) - '0';
            }
            return x * sgn;
        }
    }
    // ============================================================




    // 1 ms
    public class BestCodec {

        // Encodes a tree to a single string.
        public List<Integer> serialize(TreeNode root) {
            List<Integer> res = new ArrayList();
            hSerialize(root, res);
            return res;
        }

        private void hSerialize(TreeNode root, List<Integer> res) {
            if (root == null) {
                res.add(null);
            } else {
                res.add(root.val);
                hSerialize(root.left, res);
                hSerialize(root.right, res);
            }
        }

        // Decodes your encoded data to tree.
        public TreeNode deserialize(List<Integer> data) {
            return hDeserialize(data, new int[]{0});
        }

        private TreeNode hDeserialize(List<Integer> data, int[] index) {
            if (index[0] >= data.size()) {
                return null;
            }
            Integer val = data.get(index[0]);
            index[0] = index[0] + 1;
            if (val == null) {
                return null;
            }
            TreeNode node = new TreeNode(val);
            node.left = hDeserialize(data, index);
            node.right = hDeserialize(data, index);
            return node;
        }
    }

    // 1 ms
    public class Codec2 {

        // Encodes a tree to a single string.
        public String serialize(TreeNode root) {
            StringBuffer buffer = new StringBuffer();
            serialize(root, buffer);
            return buffer.toString();
        }

        private void serialize(TreeNode root, StringBuffer buffer) {
            if (root == null) {
                buffer.append("n ");
                return;
            }
            buffer.append(root.val).append(' ');
            serialize(root.left, buffer);
            serialize(root.right, buffer);
        }

        // Decodes your encoded data to tree.
        public TreeNode deserialize(String data) {
            return deserialize(data, new IntegerPointer());
        }

        private TreeNode deserialize(String data, IntegerPointer i) {
            if (i.val >= data.length()) {
                return null;
            }
            if (data.charAt(i.val) == 'n') {
                i.val += 2; // consume space;
                return null;
            }
            int val = 0;
            boolean nev = false;
            char c;
            if (data.charAt(i.val) == '-') {
                nev = true;
                i.val++;
            }
            while ((c = data.charAt(i.val)) != ' ') {
                val *= 10;
                val += c - '0';
                i.val++;
            }
            i.val++;

            TreeNode root = new TreeNode(nev ? -val : val);
            root.left = deserialize(data, i);
            root.right = deserialize(data, i);
            return root;
        }

        class IntegerPointer {
            int val;
        }
    }

    // 3 ms
    public class Codec3 {

        // Encodes a tree to a single string.
        public String serialize(TreeNode root) {
            StringBuilder sb = new StringBuilder();
            traverse(root, sb);
            return sb.toString();
        }

        private void traverse(TreeNode root, StringBuilder sb) {
            if (root == null) {
                sb.append(",");
                return;
            }
            sb.append(root.val).append(",");
            traverse(root.left, sb);
            traverse(root.right, sb);
        }

        // Decodes your encoded data to tree.
        public TreeNode deserialize(String data) {
            return deserialize(data, new int[] {0});
        }

        private TreeNode deserialize(String data, int[] idx) {
            long rootVal = 0;
            int currIdx = idx[0];
            int sign = 1;
            if (data.charAt(currIdx) == '+' || data.charAt(currIdx) == '-') {
                sign = data.charAt(currIdx) == '-' ? -1 : 1;
                currIdx++;
            }
            while (currIdx < data.length() && Character.isDigit(data.charAt(currIdx))) {
                rootVal = rootVal * 10 + data.charAt(currIdx) - '0';
                currIdx++;
            }
            if (currIdx == idx[0]) {
                idx[0]++;
                return null;
            }
            idx[0] = currIdx + 1;
            TreeNode root = new TreeNode((int) (sign * rootVal));
            root.left = deserialize(data, idx);
            root.right = deserialize(data, idx);
            return root;
        }
    }

    // 5 ms
    public class Codec4 {
        private StringBuilder path;

        // Encodes a tree to a single string.
        public String serialize(TreeNode root) {
            this.path = new StringBuilder();
            dfs_s(root);
            return this.path.toString();
        }

        private void dfs_s(TreeNode root) {
            if (root == null) {
                this.path.append("#,");
            } else {
                this.path.append(root.val).append(",");
                dfs_s(root.left);
                dfs_s(root.right);
            }
        }

        private int location;

        // Decodes your encoded data to tree.
        public TreeNode deserialize(String data) {
            this.location = 0;
            return dfs_d(data);
        }

        private TreeNode dfs_d(String data) {
            if (data.charAt(this.location) == '#') {
                this.location += 2;
                return null;
            } else {
                int k = this.location;
                while (data.charAt(this.location) != ',') {
                    this.location++;
                }
                TreeNode root = new TreeNode(Integer.parseInt(data.substring(k, this.location)));
                this.location++;
                root.left = dfs_d(data);
                root.right = dfs_d(data);
                return root;
            }
        }
    }

    // 6 ms
    public class Codec5 {

        // Encodes a tree to a single string.
        public String serialize(TreeNode root) {
            if (root == null) {
                return "*";
            }
            StringBuilder sb = new StringBuilder();
            sb.append(root.val).append(',')
                    .append(serialize(root.left)).append(',')
                    .append(serialize(root.right));
            return sb.toString();
        }

        private int cur = 0;

        // Decodes your encoded data to tree.
        public TreeNode deserialize(String data) {
            String[] datas = data.split(",");
            cur = 0;
            TreeNode node = dfs(datas);
            return node;
        }

        private TreeNode dfs(String[] data) {
            if (data[cur].equals("*")) {
                return null;
            }
            TreeNode node = new TreeNode(Integer.parseInt(data[cur]));

            cur++;
            node.left = dfs(data);

            cur++;
            node.right = dfs(data);

            return node;
        }
    }




    // 39976 kb
    public class Codec6 {

        // Encodes a tree to a single string.
        public String serialize(TreeNode root) {
            StringBuilder s = new StringBuilder("[");
            Queue<TreeNode> queue = new LinkedList<TreeNode>();
            queue.add(root);
            while (!queue.isEmpty()) {
                int size = queue.size();
                for (int i = 0; i < size; i++) {
                    TreeNode node = queue.poll();
                    if (node != null) {
                        s.append(node.val);
                        queue.add(node.left);
                        queue.add(node.right);
                    } else {
                        s.append("null");
                    }
                    s.append(",");
                }
            }
            s.setCharAt(s.length() - 1, ']');
            return s.toString();
        }

        // Decodes your encoded data to tree.
        public TreeNode deserialize(String data) {
            String[] str = data.substring(1, data.length() - 1).split(",");
            if (str[0].equals("null")) {
                return null;
            }
            TreeNode root = new TreeNode(Integer.parseInt(str[0]));
            LinkedList<TreeNode> queue = new LinkedList();
            queue.add(root);
            for (int i = 1; i < str.length; i++) {
                TreeNode node = queue.poll();
                if (node == null) {
                    continue;
                }
                if (!str[i].equals("null")) {
                    node.left = new TreeNode(Integer.parseInt(str[i++]));
                    queue.add(node.left);
                } else {
                    i++;
                }
                if (!str[i].equals("null")) {
                    node.right = new TreeNode(Integer.parseInt(str[i]));
                    queue.add(node.right);
                }
            }
            return root;
        }
    }



}
