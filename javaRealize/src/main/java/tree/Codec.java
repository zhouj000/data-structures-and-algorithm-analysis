package tree;

import java.util.*;

/**
 * 二叉树的序列化与反序列化
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

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        if (root == null) {
            return null;
        }
        List<String> list = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            int nullSize = 0;
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                if (node == null) {
                    nullSize++;
                    list.add("null");
                    queue.add(null);
                    queue.add(null);
                } else {
                    list.add(String.valueOf(node.val));
                    queue.add(node.left);
                    queue.add(node.right);
                }
                if (size == nullSize) {
                    queue.clear();
                    list = list.subList(0, list.size() - nullSize);
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        for (String str : list) {
            sb.append(";").append(str);
        }
        return sb.substring(1);
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        if (data == null) {
            return null;
        }
        String[] array = data.split(";");
        TreeNode root = new TreeNode(Integer.parseInt(array[0]));
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        int level = 0;
        int index = 1;
        while (!queue.isEmpty() && index < array.length) {
            int size = (int) Math.pow(2, level++);
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                if (node == null) {
                    queue.add(null);
                    queue.add(null);
                    index += 2;
                } else {
                    String lf = array[index++];
                    TreeNode left = "null".equalsIgnoreCase(lf) ? null : new TreeNode(Integer.parseInt(lf));
                    node.left = left;
                    queue.add(left);
                    String rg = array[index++];
                    TreeNode right = "null".equalsIgnoreCase(rg) ? null : new TreeNode(Integer.parseInt(rg));
                    node.right = right;
                    queue.add(right);
                }
            }
        }
        return root;
    }









    // 深度优先搜索
    class Codec2 {
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
            List<String> data_list = new LinkedList<String>(Arrays.asList(data_array));
            return rdeserialize(data_list);
        }
    }


}
