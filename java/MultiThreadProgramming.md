### Thread 상속하는 방법
```java
public static void main(String[] args) {
    HelloThread helloThread = new HelloThread();
    helloThread.start();
    System.out.println("hello : " + Thread.currentThread().getName());
}
static class HelloThread extends Thread {
    @Override
    public void run() {
        System.out.println("world : " + Thread.currentThread().getName());
    }
}
```

### Runnable을 구현하는 방법 or 람다
```java
Thread thread = new Thread(() -> System.out.println("world : " + Thread.currentThread().getName()));
thread.start();
System.out.println("hello : " + Thread.currentThread().getName());
```

### 특징
- sleep : 현재 Thread 멈춰두기
- interupt : 다른 Thread 깨우기
- join : 다른 Thread 기다리기


## 참고
인프런 백기선 [더 자바, Java 8](https://www.inflearn.com/course/the-java-java8) 강의
