# CompletableFuture
> 비동기 프로그래밍을 가능하게 하는 인터페이스


## Future의 한계
- Future를 외부에서 완료 시킬 수 없다. 취소하거나, `get()`에 타임아웃을 설정할 수는 있다.
- Blocking code(`get()`)를 사용하지 않고서는 작업이 끝났을때 콜백을 실행할 수 없다.
- 여러 Future를 조합할 수 없다.
- 예외 처리용 API를 제공하지 않는다.

## CompletableFuture
- implements Future
- implements CompletionStage

### 비동기로 작업 실행하기
- 리턴값이 없는 경우: `runAsync()`
- 리턴값이 있는 경우: `supplyAsync()`
- 원하는 `Excutor`(Thread Pool)를 사용해서 실행할 수도 있다. (기본은 `ForkJoinPool.commonPool()`)

### 콜백 제공하기
- ThenApply(Function): 리턴 값을 받아서 다른 값으로 바꾸는 콜백
- ThenAccept(Consumer): 리턴 값을 받아서 다른 작업을 처리하는 콜백 (리턴 없음)
- ThenRun(Runnable): 리턴 값을 받지 않고 다른 작업을 처리하는 콜백
- 콜백 자체를 또 다른 Thread에서 실행할 수 있다.

### 조합하기
- thenCompose(): 두 작업을 서로 이어서 실행하도록 조합
- thenCombine(): 두 작업을 독립적으로 실행하고 둘 다 종료했을 때 콜백 실행
- allOf(): 여러 작업을 모두 실행하고 모든 작업 결과에 콜백 실행
- anyOf(): 여러 작업 중에 가장 빨리 끝난 하나의 결과에 콜백 실행

### 예외처리
- exceptionally(Funtion)
- handle(BiFuntion)
    ```java
    // 응답 결과(future)와 예외처리를 같이 할 수 있음
    CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
        return "Hello World";
    }).handle((result, ex) -> {
        if (ex != null) {
            // 예외처리
            throw Exception();
        }
        return result;
    })
    ```

## 참고
인프런 백기선 [더 자바, Java 8](https://www.inflearn.com/course/the-java-java8) 강의
