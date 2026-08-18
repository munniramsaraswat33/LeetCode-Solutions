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
    public ListNode removeNthFromEnd(ListNode head, int n) {
        if(head==null || head.next==null){
            return null;
        }
        ListNode temp = head;
        int m=1;
        while(temp.next!=null){
            temp = temp.next;
            m++;
        }
        if(m==n){
            head = head.next;
            return head;
        }
        ListNode t = head;

        for(int i=1; i<m-n; i++){
            t = t.next;
        }
        t.next = t.next.next;
        return head;
    }
}