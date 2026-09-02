/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    public int goodNodes(TreeNode root) {
        int max = Integer.MIN_VALUE;
        int[] count = {0};
        traverse(max, root, count);
        return count[0];
    }
    public void traverse(int max, TreeNode root, int[] count){
        if(root == null){
            return;
        }
        if(max <= root.val){
            max = root.val;
            count[0]++;
        }
        if(root.left != null){
            traverse(max, root.left, count);
        }
        if(root.right != null){
            traverse(max, root.right, count);
        }
    }
}