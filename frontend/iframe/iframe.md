## iframe
> HTML Inline Frame 요소<br/>
> iframe 요소를 이용하면 해당 웹 페이지 안에 어떠한 제한 없이 또 다른 하나의 웹 페이지를 삽입할 수 있다.

```javascript
<iframe src="삽입할페이지주소"></iframe>
```

- iframe에 로드된 페이지는 부모 페이지와 별개의 페이지다. 때문에 부모 window context와는 별개의 window context를 가진다.
- 만약 iframe에서 부모 window에 접근하기 위해서는 `window.parant`로 접근이 가능하다.
- iframe 내부에서 부모 창 제어하는 법 테스트 
  - iframe.html, parent.html
  - jquery 사용
