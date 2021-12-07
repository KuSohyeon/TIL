### Excutors가 하는일
- Thread 만들기 : application이 사용할 Thread Pool을 만들어 관리
- Thread 관리 : Thread의 생명 주기를 관리
- 작업 처리 및 실행 : Thread로 실행할 작업을 제공할 수 있는 API 제공

### 주요 인터페이스
- Executor: execute(Runnable)
- ExecutorService: Executor 상속 받은 인터페이스로, Callable도 실행할 수 있으며,
Executor를 종료 시키거나, 여러 Callable을 동시에 실행하는 등의 기능을 제공한다.
- ScheduledExecutorService: ExecutorService를 상속 받은 인터페이스로 특정 시간
이후에 또는 주기적으로 작업을 실행할 수 있다

```java
// 작업 실행
ExecutorService executorService = Executors.newSingleThreadExecutor();
executorService.submit(() -> {
    System.out.println("Hello :" + Thread.currentThread().getName());
});

// 멈추기
executorService.shutdown(); // 처리중인 작업 기다렸다가 종료
executorService.shutdownNow(); // 당장 종료
```

## 참고
인프런 백기선 [더 자바, Java 8](https://www.inflearn.com/course/the-java-java8) 강의
