package DataStructure;

public class MyDequeue {
    private MyNode front, rear;

    public void pushFront(int data) {
        if (front == null) {
            this.front = this.rear = new MyNode(data);
        } else {
            MyNode temp = new MyNode(data);
            temp.next = this.front;
            this.front.prev = temp;
            this.front = temp;
        }

        System.out.println(data + " front pushed to dequeue");
    }

    public void pushBack(int data) {
        if (rear == null) {
            this.front = this.rear = new MyNode(data);
        } else {
            MyNode temp = new MyNode(data);
            temp.prev = this.rear;
            this.rear.next = temp;
            this.rear = temp;
        }

        System.out.println(data + " back pushed to dequeue");
    }

    public int popFront() {
        if (this.front == null) {
            System.out.println("Empty Dequeue");
            return -1;
        }
        MyNode temp = this.front;
        this.front = this.front.next;
        this.front.prev = null;
        return temp.data;
    }

    public int popBack() {
        if (this.rear == null) {
            System.out.println("Empty Dequeue");
            return -1;
        }
        MyNode temp = this.rear;
        this.rear = this.rear.prev;
        this.rear.next = null;
        return temp.data;
    }

    public class MyNode {
        MyNode prev;
        MyNode next;
        int data;

        MyNode(int data) {
            this.data = data;
        }
    }
}
