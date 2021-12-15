# Deployment
> Deployment는 Pods와 ReplicaSet에 대한 선언적 업데이트를 제공한다.

Deployment는 ReplicaSet의 상위개념으로 볼 수 있다. 
ReplicaSet을 생성하는 Deployment를 정의할 수 있고, 배포 작업을 좀 더 세분화하여 조작할 수 있는 기능을 가지고 있다.


## Use Case
- ReplicaSet을 rollout할 deployment 생성
- deployment의 현 상태가 안정적이지 않은 경우 이전버전으로 롤백
- scale up
- 일시 정지 
- 이전 ReplicaSet 정리

## Deployment 예시
- 3개의 nginx pod를 불러오기 위한 ReplicaSet 생성
```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: nginx-deployment  // 이 이름으로 deployment가 생성됨
  labels:
    app: nginx
spec:
  replicas: 3 // 3개의 Replica pod를 생성
  selector: // deployment가 관리하는 pod를 찾는 방법을 정의
    matchLabels:
      app: nginx
  template:
    metadata:
      labels:
        app: nginx
    spec: // 컨테이너 1개를 생성하고, .spec.template.spec.containers[0].name 필드를 사용해서 nginx 이름을 붙인다.
      containers:
      - name: nginx
        image: nginx:1.14.2
        ports:
        - containerPort: 80

```

## 참고
- https://kubernetes.io/docs/concepts/workloads/controllers/deployment/
- https://nirsa.tistory.com/137
