# Job

Job은 하나 이상의 파드를 지정하고 지정된 수의 파드를 성공적으로 실행하도록 하는 설정이다. 백업이나 특정 배치 파일처럼 한번 실행하고 종료되는 성격의 작업에 사용될 수 있다.


## Job 예시
```yaml
apiVersion: batch/v1
kind: Job
metadata:
  name: pi      // job 이름으로 설정
spec:
  template:
    spec:
      containers: // 실행할 컨테이너와 설정 저장
      - name: pi
        image: perl
        command: ["perl", "-Mbignum=bpi", "-wle", "print bpi(2000)"]
      restartPolicy: Never // pod의 재시작 정책 (Never: 재시작 하지 않음)
  backoffLimit: 4
```

## 참고
https://nirsa.tistory.com/142ㄴ
