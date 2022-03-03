package DataStructure;

import java.util.LinkedList;

public class Queue {
    public static class ArrayQueue {
        private int front, rear, size;
        private int capacity;
        private int array[];

        public ArrayQueue(int capacity) {
           this.capacity = capacity;
           front = this.size = 0;
           rear = capacity - 1;
           array = new int[this.capacity];
        }

        public boolean isFull(ArrayQueue queue) {
            return queue.size == queue.capacity;
        }

        public boolean isEmpty(ArrayQueue queue) {
            return queue.size == 0;
        }

        // O(1)
        public void enqueue(int item) {
            if (isFull(this)) return;

            this.rear = (this.rear + 1) % this.capacity;
            this.array[this.rear] = item;
            this.size++;
            System.out.println(item + " enqueued to queue");
        }

        // O(1)
        public int dequeue() {
            if (isEmpty(this)) return Integer.MIN_VALUE;

            int item = this.array[this.front];
            this.front = (this.front + 1) % this.capacity;
            this.size--;
            return item;
        }

        // O(1)
        public int getFront() {
            if (isEmpty(this)) {
                return Integer.MIN_VALUE;
            }
            return this.array[this.front];
        }

        // O(1)
        public int getRear() {
            if (isEmpty(this)) {
                return Integer.MIN_VALUE;
            }
            return this.array[this.rear];
        }
    }

    public static class QNode {
        int key;
        QNode next;

        public QNode(int key) {
            this.key = key;
            this.next = null;
        }
    }

    public static class LinkedListQueue {
        private QNode front, rear;

        public LinkedListQueue() {
            this.front = this.rear = null;
        }

        public void enqueue(int key) {
            QNode temp = new QNode(key);

            if (this.rear == null) {
                this.front = this.rear = temp;
                return;
            }

            this.rear.next = temp;
            this.rear = temp;
        }

        public void dequeue() {
            if (this.front == null) {
                return;
            }

            QNode temp = this.front;
            this.front = this.front.next;

            if (this.front == null) {
                this.rear = null;
            }
        }
    }

    public static void main(String[] args) {
        ArrayQueue arrayQueue = new ArrayQueue(1000);

        arrayQueue.enqueue(10);
        arrayQueue.enqueue(20);
        arrayQueue.enqueue(30);
        arrayQueue.enqueue(40);

        System.out.println(arrayQueue.dequeue() + " dequeued from queue");
        System.out.println("Front item is " + arrayQueue.getFront());
        System.out.println("Rear item is " + arrayQueue.getRear());
        System.out.println("=============================================");

        LinkedListQueue q = new LinkedListQueue();
        q.enqueue(10);
        q.enqueue(20);
        q.dequeue();
        q.dequeue();
        q.enqueue(30);
        q.enqueue(40);
        q.enqueue(50);
        q.dequeue();
        System.out.println("Queue Front : " + q.front.key);
        System.out.println("Queue Rear : " + q.rear.key);
    }
}
