/*
// Definition for a Node.
class Node {
    int val;
    Node next;
    Node random;

    public Node(int val) {
        this.val = val;
        this.next = null;
        this.random = null;
    }
}
*/

class Solution {
    public Node copyRandomList(Node head) {
        if(head == null){
            return head;
        }
        HashMap<Node, Node> hm = new HashMap<>();
        Node curr = head;
        while(curr != null){
            hm.put(curr, new Node(curr.val));
            curr = curr.next;
        }
        curr = head;
        while(curr != null){
            Node temp = hm.get(curr);
            temp.next = hm.get(curr.next);
            temp.random = hm.get(curr.random);
            curr = curr.next;
        }
        return hm.get(head);
    }
}