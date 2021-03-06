# 쉽게 배우는 운영체제
## Chap2. 컴퓨터의 구조와 성능 향상
### 01. 컴퓨터의 기본 구성
#### 1. 하드웨어의 구성
1) CPU와 메모리
- CPU : 명령어를 해석하여 실행하는 장치
- 메모리 : 작업에 필요한 프로그램과 데이터를 저장하는 장소
2) 입출력장치
- 입력장치 : 외부의 데이터를 컴퓨터에 입력하는 장치
- 출력장치 : 컴퓨터에서 처리한 결과를 사용자가 원하는 형태로 출력하는 장치
3) 저장장치
- 메모리는 전자의 이동으로 데이터를 처리하지만 하드디스크나 CD와 같은 저장장치는 구동장치가 있는 기계이므로 속도가 느리다. 이렇게 느린 저장장치를 사용하는 이유는 저장 용량에 비해 가격이 싸기 때문이다.
4) 메인보드
- 메인보드는 CPU와 메모리 등 다양한 부품을 연결하는 커다란 판이다.

#### 2. 폰노이만 구조
> 모든 프로그램은 메모리에 올라와야 실행할 수 있다.

### 02. CPU와 메모리
#### 1. CPU 구성과 동작
- 산술논리 연산장치 : CPU에서 데이터를 연산하는 장치
- 제어장치 : CPU에서 작업을 지시하는 부분
- 레지스터 : CPU 내에 데이터를 임시로 보관하는 곳

|레지스터|특징|
|---|---|
|[사용자 가시 레지스터]<br/>데이터 레지스터(DR)|CPU가 명령어를 처리하는데 필요한 일반 데이터를 임시로 저장하는 범용 레지스터이다.|
|[사용자 가시 레지스터]<br/>주소 레지스터(AR)|데이터 또는 명령어가 저장된 메모리의 주소를 저장한다.|
|[사용자 불가시 레지스터]<br/>프로그램 카운터(PC)|다음에 실행할 명령어의 위치 정보를 저장한다.|
|[사용자 불가시 레지스터]<br/>명령어 레지스터(IR)|현재 실행 중인 명령어를 저장한다.|
|[사용자 불가시 레지스터]<br/>메모리 주소 레지스터(MAR)|메모리 관리자가 접근해야 할 메모리의 주소를 저장한다.|
|[사용자 불가시 레지스터]<br/>메모리 버퍼 레지스터(MBR)|메모리 관리자가 메모리에서 가져온 데이터를 임시로 저장한다.|
|[사용자 불가시 레지스터]<br/>프로그램 상태 레지스터(PSR)|연산 결과를 저장한다.|

#### 2. 메모리의 종류와 부팅
1) 메모리의 종류<br/>
메모리는 크게 두 가지로 나눌 수 있다. 읽거나 쓸 수 있는 램(RAM)과 읽기만 가능한 롬(ROM)으로 구분된다.<br/>
램은 전력이 끊기면 데이터가 사라지는 휘발성 메모리와 전력이 끊겨도 데이터를 보관할 수 있는 비휘발성 메모리로 나뉜다.
![img.png](img/memory.png)
- 휘발성 메모리
  - DRAM(Dynamic RAM) : 데이터가 일정 시간이 지나면 사라지므로 다시 재생시켜야 함
  - SRAM(Static RAM) : 전력이 공급되는 동안에는 데이터를 재생할 필요 없음. 속도는 빠르지만 비쌈
  - SDRAM(Synchronous Dynamic RAM) : DRAM이 발전된 형태로 클록틱이 발생할때마다 데이터를 저장하는 동기 DRAM
- 비휘발성 메모리
  - 비휘발성 메모리는 전력이 끊겨도 데이터를 보관해야 하므로 메모리 내부가 복잡하고 속도가 느리며 가격이 비싸다.
- ROM
  - RAM과 달리 ROM은 전력이 끊겨도 데이터를 보관하는 것이 장점이지만 데이터를 한 번 저장하면 바꿀 수 없다.

2) 부팅
> 컴퓨터를 켰을 때 운영체제를 메모리에 올리는 과정을 부팅이라고 한다.

![img.png](img/booting.png)

- 사용자가 컴퓨터 전원을 키면 ROM에 저장된 BIOS가 실행된다.
- BIOS는 CPU, 메모리, 하드디스크 등 주요 하드웨어 작동 확인을 한다.
- 이상이 없으면 하드디스크의 MRB(Master Boot Record)에 저장된 프로그램을 메모리에 가져와 실행한다.
- 마스터 부트 레코드에 있는 부트스트랩이 메모리에 올라오면 하드디스크에 저장된 운영체제를 메모리로 불러온다.

### 03. 컴퓨터 성능 향상 기술
#### 1. 버퍼
1) 버퍼 : 일정량의 데이터를 모아 옮김으로써 속도의 차이를 완화하는 장치
2) 스폴 : CPU와 입출력장치가 독립적으로 동작하도록 고안된 소프트웨어적인 버퍼
- 기존의 버퍼의 경우 어떤 프로그램이 사용하는 데이터든 버퍼가 차면 이동이 시작하지만 스풀러는 한 인쇄물이 완료 될때까지 다른 인쇄물이 끼어들 수 없으므로 프로그램 간에 배타적임
#### 2. 캐시
> 메모리와 CPU 간의 속도 차이를 완화하기 위해 메모리의 데이터를 미리 가져와 저장해두는 임시 장소

캐시의 변경된 데이터를 메모리에 반영하는 데에는 즉시  쓰기 방식과 지연 방식이 있다.
- 즉시 쓰기
  - 캐시에 있는 데이터가 변경되면 이를 즉시 메모리에 반영하는 방식이다. 
  - 장점 : 메모리의 최신 값이 항상 유지되기 때문에 급작스러운 정전에도 데이터를 잃어버리지 않음
  - 단점 : 메모리와의 빈번한 데이터 전송으로 인해 성능이 느려짐
- 지연 쓰기(copy back)
  - 캐시에 있는 데이터가 변경되면 이를 즉시 메모리에 반영하지 않고 변경된 내용을 모아서 주기적으로 반영하는 방식
  - 장점 : 메모리와의 데이터 전송 횟수가 줄어들어 시스템의 성능을 향상할 수 있음
  - 단점 : 메모리와 캐시된 데이터 사이에 불일치 발생할 수 있
#### 4. 인터럽트
> 입출력 관리자가 CPU에 보내는 완료 신호를 인터럽트라고 한다.
- 인터럽트 방식의 동작 과정
1) CPU가 입출력 관리자에게 입출력 명령을 보낸다.
2) 입출력 관리자는 명령받은 데이터를 메모리에 가져댜놓거나 메모리에 있는 데이터를 저장장치로 옮긴다.
3) 데이터 전송이 완료되면 입출력 관리자는 완료 신호를 CPU에게 보낸다.

### 04. 병렬 처리
#### 1. 병렬 처리의 개념
> 동시에 여러 개의 명령을 처리하여 작업의 능률을 올리는 방식
#### 2. 병렬 처리 시 고려 사항
- 상호 의존성이 없어야 병렬 처리가 가능하다.
- 각 단계의 시간을 거의 일정하게 맞춰야 병렬 처리가 원만하게 이루어진다.
- 전체 작업 시간을 몇 단계로 나눌지 잘 따려보아야 한다.
