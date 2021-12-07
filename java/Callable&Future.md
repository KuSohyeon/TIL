### Callable
- Runnable과 유사하지만 차이점은 작업의 결과를 받을 수 있다.
```java
V call() throws Exception
```

### Future
- 비동기적인 작업의 현재 상태를 조회하거나 결과를 가져올 수 있다.
- get(): 결과 가져올 때 사용 (blocking, timeout 설정 가능)
- isDone(): 작업상태 확인할 때 사용
- cancel(): 작업 취소할 때 사용
- invokeAll(): 여러 작업 동시에 실행하기
  - 제일 오래 걸리는 작업이 끝날때까지 시간 소모
- invokeAny(): 여러 작업 중에 하나라도 먼저 응답이 오면 끝내기 (blocking)
```java
ExecutorService executorService = Executors.newSingleThreadExecutor();
Future<String> helloFuture = executorService.submit(() -> {
    Thread.sleep(2000L);
    return "Callable";
});
System.out.println("Hello");
String result = helloFuture.get();  // blocking
System.out.println(result);
executorService.shutdown(); // 종료
```

## 참고
인프런 백기선 [더 자바, Java 8](https://www.inflearn.com/course/the-java-java8) 강의
