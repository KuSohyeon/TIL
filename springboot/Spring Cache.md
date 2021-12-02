# Spring Cache

- Spring Framework 는 프레임워크 레벨에서 캐시의 추상화를 지원

### @Cacheable

> **Cache Key Value 의 등록**

```java
@Cacheable("addresses")
public String getAddress(Customer customer) {...}
```
- 캐시가 등록되어 있을 경우 `getAddress` 메소드를 실행하지 않고 캐시 값 반환
- 그렇지 않다면 메소드 실행 후 결과를 캐시에 저장

### @CacheEvict

> **Cache 데이터 삭제 시 사용**

```java
@CacheEvict("addresses")
public String getAddress(Customer customer) {...}
```

### @CachePut

> **Cache 데이터 갱신 시 사용**

```java
@CachePut(value="addresses")
public String getAddress(Customer customer) {...}
```
- `@Cacheable`과 차이점은 `@CachePut`은 메소드를 항상 실행

### @Caching

> **Cache annotation을 여러 개 지정해야 하는 경우 사용**
>
- **As-is**

```java
@CacheEvict("addresses")
@CacheEvict(value="directory", key=customer.name)
public String getAddress(Customer customer) {...}
```

- **To-be**

```java
@Caching(evict = { 
@CacheEvict("addresses"), 
@CacheEvict(value="directory", key="#customer.name") })
public String getAddress(Customer customer) {...}
```

### Condition Parameter

```java
@CachePut(value="addresses", condition="#customer.name=='Tom'")
public String getAddress(Customer customer) {...}
```
