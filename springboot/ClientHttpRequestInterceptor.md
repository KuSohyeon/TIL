# ClientHttpRequestInterceptor

```java
@FunctionalInterface
public interface ClientHttpRequestInterceptor {

	/**
	 * Intercept the given request, and return a response. The given
	 * {@link ClientHttpRequestExecution} allows the interceptor to pass on the
	 * request and response to the next entity in the chain.
	 * <p>A typical implementation of this method would follow the following pattern:
	 * <ol>
	 * <li>Examine the {@linkplain HttpRequest request} and body</li>
	 * <li>Optionally {@linkplain org.springframework.http.client.support.HttpRequestWrapper
	 * wrap} the request to filter HTTP attributes.</li>
	 * <li>Optionally modify the body of the request.</li>
	 * <li><strong>Either</strong>
	 * <ul>
	 * <li>execute the request using
	 * {@link ClientHttpRequestExecution#execute(org.springframework.http.HttpRequest, byte[])},</li>
	 * <strong>or</strong>
	 * <li>do not execute the request to block the execution altogether.</li>
	 * </ul>
	 * <li>Optionally wrap the response to filter HTTP attributes.</li>
	 * </ol>
	 * @param request the request, containing method, URI, and headers
	 * @param body the body of the request
	 * @param execution the request execution
	 * @return the response
	 * @throws IOException in case of I/O errors
	 */
	ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException;

}

```
Spring RestTemplate를 사용하면 `ClientHttpRequestInterceptor` 인터페이스를 구현하는 인터셉터를 추가할 수 있다.<br/>
이 인터페이스의 `intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)` 메소드는 주어진 요청을 차단하고 request, body 및 execution 객체에 대한 액세스를 제공하며 응답을 반환한다.

### example
```java
public class RestTemplateHeaderModifierInterceptor
  implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(
      HttpRequest request, 
      byte[] body, 
      ClientHttpRequestExecution execution) throws IOException {
 
        ClientHttpResponse response = execution.execute(request, body);
        response.getHeaders().add("Foo", "bar");
        return response;
    }
}
```

인터셉터는 들어오는 모든 요청에 대해 호출되고 실행이 완료되고 반환되면 모든 응답에 사용자 정의 헤더 Foo를 추가한다.<br/>
intercept() 메서드는 요청과 본문을 인수로 포함했기 때문에 요청을 수정하거나 특정 조건에 따라 요청 실행을 거부하는 것도 가능하다.


### 출처
https://www.baeldung.com/spring-rest-template-interceptor
