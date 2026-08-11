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
    public ListNode deleteDuplicates(ListNode head) {
        if(head == null || head.next == null){
            return head;
        }
        ListNode curr = head;
        ListNode temp = head.next;
        
        while(temp != null){
            if(curr.val == temp.val){
                temp = temp.next;
            }
            else{
                curr.next = temp;
                curr = curr.next;
                temp = temp.next;
            }
        }
        curr.next = null;
        return head;
    }
}