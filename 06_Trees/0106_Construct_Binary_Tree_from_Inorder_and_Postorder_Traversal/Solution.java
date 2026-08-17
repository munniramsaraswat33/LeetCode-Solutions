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
    int postIndex;
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        if(inorder == null || postorder == null || inorder.length != postorder.length){
            return null;
        }
        
        postIndex = postorder.length-1;

        HashMap<Integer, Integer> map = new HashMap<>();
        for(int i=0; i<inorder.length; i++){
            map.put(inorder[i], i);
        }

        return helper(postorder, map, 0, inorder.length-1);
    }
    

    private TreeNode helper(int[] postorder, HashMap<Integer, Integer> map, int start, int end){
        if(start > end){
            return null;
        }

        int treeNode = postorder[postIndex];
        TreeNode root = new TreeNode(treeNode);
        postIndex--;

        int inIndex = map.get(treeNode);
        root.right = helper(postorder, map, inIndex+1, end);
        root.left = helper(postorder, map, start, inIndex-1);
        
        return root;
    }
}