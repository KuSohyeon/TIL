# Validation

> 인프런 김영한님 Spring MVC2편 강의 참고


## BindingResult
스프링이 제공하는 검증 오류를 보관하는 객체


```java
@PostMapping("/add")
public String addItemV1(@ModelAttribute Item item, BindingResult bindingResult,RedirectAttributes redirectAttributes) {
    if (!StringUtils.hasText(item.getItemName())) { 
        bindingResult.addError(new FieldError("item", "itemName", "상품 이름은 필수입니다.")); 
    }
    
    if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
        bindingResult.addError(new FieldError("item", "price", "가격은 1,000 ~ 1,000,000 까지 허용합니다."));
    }
    
    if (item.getQuantity() == null || item.getQuantity() > 10000) { 
        bindingResult.addError(new FieldError("item", "quantity", "수량은 최대 9,999 까지 허용합니다.")); 
    }
    
    //특정 필드 예외가 아닌 전체 예외
    if (item.getPrice() != null && item.getQuantity() != null) {
        int resultPrice = item.getPrice() * item.getQuantity();
        if (resultPrice < 10000) {
            bindingResult.addError(new ObjectError("item", "가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재 값 = " + resultPrice));
        }
    }
    
    if (bindingResult.hasErrors()) {
        log.info("errors={}", bindingResult);
        return "validation/v2/addForm";
    }
    
    //성공 로직
    Item savedItem = itemRepository.save(item); 
    redirectAttributes.addAttribute("itemId", savedItem.getId()); 
    redirectAttributes.addAttribute("status", true);
    return "redirect:/validation/v2/items/{itemId}";
}
```

### 필드 오류 - FieldError
```java
public FieldError(String objectName, String field, String defaultMessage) {}
```

### 글로벌 오류 - ObjectError
```java
public ObjectError(String objectName, String defaultMessage) {}
```

### 특징
- `BindingResult`가 있다면 `@ModelAttribute`에 데이터 바인딩 시 오류가 발생해도 컨트롤러가 호출됨
  - `BindingResult` 가 없으면 400 오류가 발생하면서 컨트롤러가 호출되지 않고, 오류 페이지로 이동한다.
  - `BindingResult` 가 있으면 오류 정보( FieldError`)를 `BindingResult` 에 담아서 컨트롤러를 정상 호출한다.

### BindingResult에서 검증 오류를 적용하는 방법
1. `@ModelAttribute` 객체에 타입 오류 등으로 바인딩이 실패하는 경우 스프링 프레임워크가 `FieldError` 객체를 생성해서 `BindingResult`에 넣어주는 경우
2. 개발자가 직접 넣어주는 경우

### 주의 할점
- `BindingResult` 파라미터 위치는 반드시 `@ModelAttribute` 다음에 위치해야 함

### BindingResult와 Errors
- org.springframework.validation.Errors
- org.springframework.validation.BindingResult


- BindingResult 는 인터페이스이고, Errors 인터페이스를 상속받고 있다.<br/>
  실제 넘어오는 구현체는 BeanPropertyBindingResult 라는 것인데, 둘다 구현하고 있으므로 BindingResult 대신에 Errors 를 사용해도 된다. Errors 인터페이스는 단순한 오류 저장과 조회 기능을 제공한다. BindingResult 는 여기에 더해서 추가적인 기능들을 제공한다.
  addError() 도 BindingResult 가 제공하므로 여기서는 BindingResult 를 사용하자. 주로 관례상 BindingResult 를 많이 사용한다.

## FieldError, ObjectError
```java
@PostMapping("/add")
public String addItemV2(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
    if (!StringUtils.hasText(item.getItemName())) {
        bindingResult.addError(new FieldError("item", "itemName", item.getItemName(), false, null, null, "상품 이름은 필수입니다.")); 
    }
    
    if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
        bindingResult.addError(new FieldError("item", "price", item.getPrice(), false, null, null, "가격은 1,000 ~ 1,000,000 까지 허용합니다."));
    }
        
    if (item.getQuantity() == null || item.getQuantity() > 10000) {
        bindingResult.addError(new FieldError("item", "quantity", item.getQuantity(), false, null, null, "수량은 최대 9,999 까지 허용합니다."));
    }
    
    //특정 필드 예외가 아닌 전체 예외
    if (item.getPrice() != null && item.getQuantity() != null) {
        int resultPrice = item.getPrice() * item.getQuantity();
        if (resultPrice < 10000) {
            bindingResult.addError(new ObjectError("item", null, null, "가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재 값 = " + resultPrice));
        }
    }
        
    if (bindingResult.hasErrors()) {
        log.info("errors={}", bindingResult);
        return "validation/v2/addForm";
    }
    
    //성공 로직
    Item savedItem = itemRepository.save(item); 
    redirectAttributes.addAttribute("itemId", savedItem.getId()); 
    redirectAttributes.addAttribute("status", true);
    return "redirect:/validation/v2/items/{itemId}";
}
```

### FieldError 생성자
```java
public FieldError(String objectName, String field, String defaultMessage);
public FieldError(String objectName, String field, @Nullable Object, rejectedValue, boolean bindingFailure, @Nullable String[] codes, @Nullable Object[] arguments, @Nullable String defaultMessage)
```
- 파라미터 목록
  - objectName : 오류가 발생한 객체 이름
  - field : 오류 필드
  - rejectedValue : 사용자가 입력한 값(거절된 값)
  - bindingFailure : 타입 오류 같은 바인딩 실패인지, 검증 실패인지 구분 값 codes : 메시지 코드
  - arguments : 메시지에서 사용하는 인자
  - defaultMessage : 기본 오류 메시지


- 오류 발생시 사용자 입력 값 유지
사용자의 입력 데이터가 컨트롤러의 `@ModelAttribute` 에 바인딩되는 시점에 오류가 발생하면 모델 객체에 사용자 입력 값을 유지하기 어렵다. <br/>
예를 들어서 가격에 숫자가 아닌 문자가 입력된다면 가격은 Integer 타입이므로 문자를 보관할 수 있는 방법이 없다. 그래서 오류가 발생한 경우 사용자 입력 값을 보관하는 별도의 방법이 필요하다. 그리고 이렇게 보관한 사용자 입력 값을 검증 오류 발생시 화면에 다시 출력하면 된다.
FieldError 는 오류 발생시 사용자 입력 값을 저장하는 기능을 제공한다.
여기서 rejectedValue 가 바로 오류 발생시 사용자 입력 값을 저장하는 필드다.
bindingFailure 는 타입 오류 같은 바인딩이 실패했는지 여부를 적어주면 된다. 여기서는 바인딩이 실패한 것은 아니기 때문에 false 를 사용한다.


## Validator
```java
interface Validator {

	boolean supports(Class<?> clazz);

	void validate(Object target, Errors errors);
}
```
- supports() {}: 해당 검증기를 지원하는 여부 확인
- validate(Object target, Errors errors): 검증 대상 객체와 BindingResult


### WebDataBinder를 통해 사용하기
```java
    @InitBinder
    public void init(WebDataBinder dataBinder) {
        dataBinder.addValidators(itemValidator);
    }
```
- `WebDataBinder` 검증기를 추가하면 해당 컨트롤러에서는 검증기를 자동으로 적용할 수 있다
```java
    @PostMapping("/add")
    public String addItemV6(@Validated @ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

        //검증에 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) {
            log.info("errors={} ", bindingResult);
            return "validation/v2/addForm";
        }

        //성공 로직
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }
```
- 검증 대상앞에 `@validated` 사용
- 이 애노테이션이 붙으면 앞서 WebDataBinder 에 등록한 검증기를 찾아서 실행한다. 그런데 여러 검증기를 등록한다면 그 중에 어떤 검증기가 실행되어야 할지 구분이 필요하다. 이때 supports() 가 사용된다.


## Bean Validation
스프링 부트가 `spring-boot-starter-validation` 라이브러리를 넣으면 자동으로 Bean Validator를 인지하고 스프링에 통합한다.

**스프링 부트는 자동으로 글로벌 Validator로 등록한다.<br/>**
LocalValidatorFactoryBean 을 글로벌 Validator로 등록한다. 이 Validator는 @NotNull 같은 애노테이션을 보고 검증을 수행한다. 이렇게 글로벌 Validator가 적용되어 있기 때문에, @Valid , @Validated 만 적용하면 된다.
검증 오류가 발생하면, FieldError , ObjectError 를 생성해서 BindingResult 에 담아준다.

### 검증 순서
1. @ModelAttribute 각각의 필드에 타입 변환 시도
  - 성공하면 다음으로<br/>
  - 실패하면 typeMismatch 로 FieldError 추가 <br/>
2. Validator 적용


### **바인딩에 성공한 필드만 Bean Validation 적용**
BeanValidator는 바인딩에 실패한 필드는 BeanValidation을 적용하지 않는다.<br/>
생각해보면 타입 변환에 성공해서 바인딩에 성공한 필드여야 BeanValidation 적용이 의미 있다. (일단 모델 객체에 바인딩 받는 값이 정상으로 들어와야 검증도 의미가 있다.)<br/>
@ModelAttribute 각각의 필드 타입 변환시도 변환에 성공한 필드만 BeanValidation 적용

### @ModelAttribute vs @RequestBody
HTTP 요청 파리미터를 처리하는 @ModelAttribute 는 각각의 필드 단위로 세밀하게 적용된다. 그래서 특정 필드에 타입이 맞지 않는 오류가 발생해도 나머지 필드는 정상 처리할 수 있었다. <br/>
HttpMessageConverter 는 @ModelAttribute 와 다르게 각각의 필드 단위로 적용되는 것이 아니라, 전체 객체 단위로 적용된다.<br/>
따라서 메시지 컨버터의 작동이 성공해서 Item 객체를 만들어야 @Valid , @Validated 가 적용된다.<br/>

- @ModelAttribute 는 필드 단위로 정교하게 바인딩이 적용된다. 특정 필드가 바인딩 되지 않아도 나머지 필드는 정상 바인딩 되고, Validator를 사용한 검증도 적용할 수 있다.
- @RequestBody 는 HttpMessageConverter 단계에서 JSON 데이터를 객체로 변경하지 못하면 이후 단계 자체가 진행되지 않고 예외가 발생한다. 컨트롤러도 호출되지 않고, Validator도 적용할 수 없다.
  - `HttpMessageConverter` 단계에서 실패하면 예외가 발생한다. 
