package DataStructure;

public class Stack {

    public static class StackAsArray {
        static final int MAX = 1000;
        int top;
        int[] arrStack = new int[MAX];

        StackAsArray() {
            top = -1;
        }

        boolean isEmpty() {
            return (top < 0);
        }

        boolean push(int data) {
            System.out.println(data + " pushed to stack");

            if (top > MAX - 1) {
                System.out.println("Stack Overflow");
                return false;
            } else {
                arrStack[++top] = data;
                return true;
            }
        }

        int pop() {
            if (top < 0) {
                System.out.println("Stack is empty");
                return -1;
            } else {
                int x = arrStack[top--];
                return x;
            }
        }

        int peek() {
            return arrStack[top];
        }
    }

    public static class StackAsLinkedList {

        StackAsLinkedList.StackNode root;

        static class StackNode {
            int data;
            StackAsLinkedList.StackNode next;

            StackNode(int data) {
                this.data = data;
            }
        }

        public boolean isEmpty() {
            return root == null;
        }

        public void push(int data) {
            StackAsLinkedList.StackNode newNode = new StackNode(data);
            newNode.next = root;
            root = newNode;
            System.out.println(data + " pushed to stack");
        }

        public int pop() {
            if (root == null) {
                System.out.println("Stack is empty");
            }

            int popped = Integer.MIN_VALUE;
            popped = root.data;
            root = root.next;
            return popped;
        }

        public int peek() {
            if (root == null) {
                System.out.println("Stack is empty");
            }

            return root.data;
        }

    }

    public static void main(String args[])
    {
        StackAsArray s = new StackAsArray();
        s.push(10);
        s.push(20);
        s.push(30);
        System.out.println(s.pop() + " Popped from stack");
        System.out.println("Top element is :" + s.peek());


        StackAsLinkedList sll = new StackAsLinkedList();
        sll.push(10);
        sll.push(20);
        sll.push(30);
        System.out.println(sll.pop() + " popped from stack");
        System.out.println("Top element is " + sll.peek());
    }
}
