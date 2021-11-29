## ArgumentResolver
> Controller로 들어온 파라미터를 가공하거나 수정 기능을 제공하는 객체

### HandlerMethodArgumentResolver
- 컨트롤러 메소드에서 특정 조건에 맞는 파라미터가 있을 때 원하는 값을 바인딩 해주는 인터페이스

```java
package org.springframework.web.method.support;

import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;

public interface HandlerMethodArgumentResolver {

	boolean supportsParameter(MethodParameter parameter);

	@Nullable
	Object resolveArgument(MethodParameter parameter, @Nullable ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, @Nullable WebDataBinderFactory binderFactory) throws Exception;

}

```

- ArgumentResolver를 작성한 후에는 WebConfig파일에 등록해 주자.
```java
@Override
public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
    argumentResolvers.add(userArgumentResolver());
}
```
