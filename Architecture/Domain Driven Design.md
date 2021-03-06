# Domain Driven Design

> 도메인 주도 디자인이란 개발을 함에 있어 도메인이 중심이 되는 개발 방식

- 목적: 소프트웨어의 연관된 부분들을 연결하여 계속해서 진화하는 새로운 모델을 만들어 나가 복잡한 어플리케이션을 만드는 것을 쉽게 해주는 것
- 목표: **Loose Coupling, High Cohension**으로 각 도메인이 연결성이 적고 높은 정도로 연관되어 보다 가벼운 설계를 위해 탄생

### 세가지 주요 원리
1. 핵심 도메인과 그 기능에 집중하라.
2. 도메인의 모델을 정교하게 구축하라.
3. 어플리케이션 모델을 발전시키고 새롭게 생기는 도메인 관련 이슈를 해결하기 위해 도메인 전문가와 끊임없이 협력하라.

### Aggregate Root
DDD에서 entity마다 repository를 만드는 경우가 많은데 이럴 때 여러 entity를 묶어서 하나로 사용할 때가 많다. 이런 연관 객체의 묶음을 **Aggregate**라고 한다.<br/>
여러 entity를 묶어서 가져오는 경우가 많을 땐 개발에선 Aggregate Root에 해당되는 entity에 대해서만 repository를 만드는 경우가 많다.
