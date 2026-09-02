/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public int[] nodesBetweenCriticalPoints(ListNode head) {
        ListNode prev = head;
        ListNode curr = head.next;
        ListNode next = curr.next;
        int index = 2;
        int first = -1;
        int last = -1;
        if(next == null){
            return new int[]{first, last};
        }
        int minDistance = Integer.MAX_VALUE;
        while(next != null){
            if((prev.val < curr.val && next.val < curr.val) || (prev.val > curr.val && next.val > curr.val)){
                if(first == -1){
                    first = index;
                }
                if(last != -1){
                    minDistance = Math.min(minDistance, index-last);
                }
                last = index;
            }
            prev = curr;
            curr = next;
            next = next.next;
            index++;
        }
        if(first == last){
            return new int[]{-1, -1};
        }
        int maxDistance = last-first;
        return new int[]{minDistance, maxDistance};
    }
}