### start with (`^`)
- That selector matches all spans that have an id attribute and it starts with foo (e.g. fooblah)
```javascript
$("span[id^=foo]")
```

### end with (`$`)
- That selector matches all spans that have an id attribute and it ends with foo (e.g. blahfoo).
```javascript
$("span[id$=foo]")
```

### any (`*`)
- That selector matches all spans that have an id attribute and it has foo somewhere within in it (e.g. blahfooblah).
```javascript
$("span[id*=foo]")
```



### 참고
https://stackoverflow.com/questions/1487792/jquery-find-element-whose-id-has-a-particular-pattern
