# [아이템 24] 멤버 클래스는 되도록 static으로 만들라

### 중첩 클래스란?

다른 클래스 안에 정의된 클래스를 중첩 클래스(nested class)라고 말한다.

중첩 클래스는 자신을 감싼 바깥 클래스에서만 쓰여야 하며, 그 외의 쓰임새가 있다면 톱레벨 클래스로 만들어야 한다.

중첩 클래스의 종류는 정적 멤버 클래스, 비정적 멤버 클래스, 익명 클래스, 지역 클래스가 있다.

### 정적 멤버 클래스

`정적 멤버 클래스`는 다른 클래스 안에 선언되고, 바깥 클래스의 private 멤버에도 접근할 수 있다는 점만 제외하고는 일반 클래스와 똑같다. 정적 멤버 클래스와 비정적 멤버 클래스는 코드 상에서 static의 유무만 보일 수 있으나 의미상의 차이는 더 크다.

### 비정적 멤버 클래스

`비정적 멤버 클래스` 의 인스턴스는 바깥 클래스의 인스턴스와 암묵적으로 연결된다. 그래서 비정적 멤버 클래스의 인스턴스 메서드에서 정규화된 this(`클래스명.this` 형태로 바깥 클래스의 이름을 명시하는 용법)를 사용해 바깥 인스턴스의 메서드를 호출하거나 바깥 인스턴스의 참조를 가져올 수 있다.

**[정적 멤버 클래스와 비정적 멤버 클래스 예제](https://docs.oracle.com/javase/tutorial/java/javaOO/nested.html)**

```java
public class OuterClass {

    String outerField = "Outer field";
    static String staticOuterField = "Static outer field";

    // 비정적 멤버 클래스
    class InnerClass {
        void accessMembers() {
            System.out.println(outerField);
            System.out.println(staticOuterField);
            // 정규화된 this로 참조 가능
            System.out.println(OuterClass.this.outerField);
        }
    }

    // 정적 멤버 클래스
    static class StaticNestedClass {
        void accessMembers(OuterClass outer) {
            // 정적 중첩 클래스는 해당 클래스에 정의된 인스턴스 변수나 메서드를 직접 참조할 수 없음
            // System.out.println(outerField);
            System.out.println(outer.outerField);
            System.out.println(staticOuterField);
        }
    }
}

public class Example {
	public static void main(String[] args) {
				// 비정적 멤버 클래스는 내부 클래스를 인스턴스화하려면 먼저 외부 클래스를 인스턴스화 해야함
				System.out.println("Inner class:");
        System.out.println("------------");
        OuterClass outerObject = new OuterClass();
        OuterClass.InnerClass innerObject = outerObject.new InnerClass();
        innerObject.accessMembers();

        System.out.println("\nStatic nested class:");
        System.out.println("--------------------");
        OuterClass.StaticNestedClass staticNestedObject = new OuterClass.StaticNestedClass();
        staticNestedObject.accessMembers(outerObject);
	}
}
```

멤버 클래스에서 바깥 인스턴스에 접근할 일이 없다면 무조건 static을 붙여서 정적 멤버 클래스를 만드는 것이 좋다. static을 생략하면 바깥 인스턴스로의 숨은 참조를 갖게되는데 이 참조를 저장하려면 시간과 공간이 소비된다. 더 심각한 문제는 가비지 컬렉션이 바깥 클래스의 인스턴스를 수거하지 못해 메무리 누수가 생길 수 있다는 점이다.

### 익명 클래스

`익명 클래스`는 이름이 없으며 바깥 클래스의 멤버가 되지도 않는다. 익명 클래스를 사용하면 코드를 더 간결하게 만들 수 있다. 클래스를 선언하고 동시에 인스턴스화할 수 있다. 이름이 없다는 점을 제외하고는 로컬 클래스와 같으므로 로컬 클래스를 한 번만 사용하는 경우 사용하는 것이 좋다.

익명 클래스는 초기화된 final 기본 타입과 문자열 필드만 가질 수 있으며 instanceof 검사나 클래스의 이름이 필요한 작업을 구현할 수 없다. 또한 여러 인터페이스를 구현할 수 없고, 인터페이스를 구현하는 동시에 다른 클래스도 상속할 수 없다.

```java
HelloWorld frenchGreeting = new HelloWorld() {
            String name = "tout le monde";
            public void greet() {
                greetSomeone("tout le monde");
            }
            public void greetSomeone(String someone) {
                name = someone;
                System.out.println("Salut " + name);
            }
        };
```

### 지역 클래스

지역 클래스는 지역변수를 선언할 수 있는 곳이면 어디서든 선언할 수 있으며 유효 범위도 지역 변수와 같다.

### 어떤 경우에 어떤 클래스를 사용해야 하는가...

- 메서드 밖에서도 사용해야하거나 메서드 안에 정의하기엔 너무 길다면?
    - 멤버 클래스로 만들자.
        - 멤버 클래스의 인스턴스가 바깥 인스턴스를 참조한다면 비정적, 그렇지 않으면 정적
- 중첩클래스가 한 메서드 안에서만 쓰이면서 그 인스턴스를 생성하는 지점이 단 한 곳이고 해당 타입으로 쓰기에 적합한 클래스나 인터페이스가 이미 있다면?
    - 익명 클래스로 만들자.

## 참고

- [https://docs.oracle.com/javase/tutorial/java/javaOO/nested.html](https://docs.oracle.com/javase/tutorial/java/javaOO/nested.html)
- [https://docs.oracle.com/javase/tutorial/java/javaOO/anonymousclasses.html](https://docs.oracle.com/javase/tutorial/java/javaOO/anonymousclasses.html)
