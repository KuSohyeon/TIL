# [아이템 58] 전통적인 for문보다는 for-each문을 사용하라

> for-each 문 : 향상된 for 문(enhanced for statement)
>

전통적인 for문과 비교했을 때 for-each문은 명료하고, 유연하고, 버그를 예방해주며 성능 저하도 없다.

### 전통적인 for문을 사용하여 순회하는 방법

- 컬렉션 순회하기

```java
for (Iterator<Element> i = c.iterator(); i.hasNext();) {
	Element e = i.next();
	// ...
}
```

- 배열 순회하기

```java
for (int i = 0; i < a.length; i++) {
	// ...
}
```

위 두 코드에서 반복자와 인덱스 변수는 모두 코드를 지저분하게 할 뿐 우리에게 진짜 필요한건 요소들뿐이다.

for-each 문을 사용하여 반복자와 인덱스를 제거해보자.

```java
for (Element e : elements) {
	// ...
}
```

반복자와 인덱스 변수를 사용하지 않으니 코드가 깔끔해지고 오류가 날 일도 없다.

```java
enum Suit { CLUB, DIAMOND, HEART, SPADE }
enum Rank { ACE, DEUCE, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, 
       NINE, TEN, JACK, QUEEN, KING }

...
static Collection<Suit> suits = Arrays.asList(Suit.values());
static Collection<Rank> ranks = Arrays.asList(Rank.values());

List<Card> deck = new ArrayList<>();

for (Iterator<Suit> i = suits.iterator(); i.hasNext(); ) {
	for (Iterator<Rank> j = ranks.iterator(); j.hasNext(); ) {
		deck.add(new Card(i.next(), j.next());
	}
}
```

- 위 코드에서 버그를 발견했는가?

  이 코드에서 문제점은 바깥 컬렉션(suits)의 반복자에서 next 메서드가 너무 많이 불린다는 것이다.

  마지막 줄의 `i.next()`에 주목하자. 이 `next()`는 ‘Suit’ 하나당 한 번씩만 불려야하는데 ‘Rank’ 하나당 한 번씩 불리고 있다. 그래서 Suit가 바닥나면 반복문에서 `NosuchElementException`을 던질 것이다.


만약 바깥 컬렉션의 크기가 안쪽 컬렉션 크기의 배수라면 예외를 발생시키지 않고 종료될 수 있다.

다음은 주사위를 두 번 굴렸을 때 나올 수 있는 모든 경우의 수를 출력하는 코드이다.

```java
enum Face { ONE, TWO, THREE, FOUR, FIVE, SIX }
...
Collection<Face> faces = EnumSet.allOf(Face.class);

for (Iterator<Face> i = faces.iterator(); i.hasNext();) {
	for (Iterator<Face> j = faces.iterator(); j.hasNext();) {
		System.out.println(i.next() + " " + j.next());
	}
}
```

이 프로그램은 예외를 던지진 않지만, 가능한 조합을 단 6개(”ONE ONE” - ”SIX SIX”)만 출력하고 프로그램이 종료되어버린다.

이런 문제는 바깥 반복문에 바깥 원소를 저장하는 변수를 하나 추가하면 해결 할 수 있다.

```java
for (Iterator<Suit> i = suits.iterator(); i.hasNext();) {
	Suit suit = i.next();
	for (Iterator<Rank> j = ranks.iterator(); j.hasNext();) {
		deck.add(new Card(suit, j.next()));
	}
}
```

또 다른 방법으로는 for-each문을 중첩하는 것으로 아주 간단히 해결할 수 있다.

```java
for (Suit suit: suits) {
	for (Rank rank : ranks) {
		deck.add(new Card(suit, rank));
	}
}
```

### for-each문을 사용할 수 없는 경우

for-each문을 사용한다면 코드를 간결하게 작성할 수 있지만 아쉽게도 사용할 수 없는 상황이 세 가지 존재한다.

- **파괴적인 필터링** : 컬렉션을 순회하면서 선택된 원소를 제거해야한다면 반복자의 `remove` 메서드를 호출해야한다.
- **변형** : 리스트나 배열을 순회하면서 그 원소의 값 일부 혹은 전체를 교체해야 한다면 리스트의 반복자나 배열의 인덱스를 사용해야 한다.
- **병렬 반복** : 여러 컬렉션을 병렬로 순회해야 한다면 각각의 반복자와 인덱스 변수를 사용해 엄격하고 명시적으로 제어해야 한다.
