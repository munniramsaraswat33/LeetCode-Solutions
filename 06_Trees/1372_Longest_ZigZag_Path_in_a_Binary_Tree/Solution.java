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
    public int longestZigZag(TreeNode root) {
        return dfs(root)[2];
    }
    public int[] dfs(TreeNode root){
        if(root == null){
            return new int[]{-1, -1, -1};
        }
        int left[] = dfs(root.left);
        int right[] = dfs(root.right);

        int ans = Math.max(Math.max(left[1], right[0])+1, Math.max(left[2], right[2]));

        return new int[]{left[1]+1, right[0]+1, ans};
    }
}