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

    public int maxLevelSum(TreeNode root){
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        int level = 1;
        int answer = 1;
        int maxsum = Integer.MIN_VALUE;
        while(!q.isEmpty()){
            int size = q.size();
            int levelSum = 0;
            for(int i=0; i<size; i++){
                TreeNode curr = q.remove();
                levelSum += curr.val;

                if(curr.left != null){
                    q.add(curr.left);
                }
                if(curr.right != null){
                    q.add(curr.right);
                }
            }
            if(maxsum < levelSum){
                maxsum = levelSum;
                answer = level;
            }
            level++;
        }
        return answer;
    }
}