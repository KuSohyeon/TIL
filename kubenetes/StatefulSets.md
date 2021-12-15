# StatefulSet
> StatefulSet is the workload API object used to manage stateful applications.

애플리케이션의 상태를 저장하고 관리하는 데 사용되는 쿠버네티스 객체다. 
기존의 pod를 삭제하고 생성할 때 상태가 유지되지 않는 한계가 있다. 
때문에 pod를 삭제하고 생성하면 완전히 새로운 가상환경이 시작된다. 
하지만 필요에 따라 이러한 pod의 상태를 유지하고 싶을 수 있다. 
응용프로그램의 로그나 기타 다른 정보들을 함께 저장하고자 하는 경우 단순히 PV를 하나 마운트해 이를 유지하기는 어렵다. 
스테이트풀셋으로 생성되는 pod는 영구 식별자를 가지고 상태를 유지시킬 수 있다.

## StatefulSet을 사용하면 좋은 케이스
- 안정적이고 고유한 네트워크 식별자가 필요한 경우
- 안정적이고 영구적인 스토리지가 필요한 경우
- 질서 정연한 Pod의 배치와 확장을 원하는 경우
- pod의 자동 롤링 업테이트를 사용하기 원하는 경우

### 구성요소
```yaml
apiVersion: apps/v1
kind: StatefulSet
metadata:
  name: web
spec:
  selector:
    matchLabels:
      app: nginx # has to match .spec.template.metadata.labels
  serviceName: "nginx"
  replicas: 3 # by default is 1
  minReadySeconds: 10 # by default is 0
  template:
    metadata:
      labels:
        app: nginx # has to match .spec.selector.matchLabels
    spec:
      terminationGracePeriodSeconds: 10
      containers:
      - name: nginx
        image: k8s.gcr.io/nginx-slim:0.8
        ports:
        - containerPort: 80
          name: web
        volumeMounts:
        - name: www
          mountPath: /usr/share/nginx/html
  volumeClaimTemplates: // 영구 볼륨을 사용해서 안정적인 스토리지를 제공
  - metadata:
      name: www
    spec:
      accessModes: [ "ReadWriteOnce" ]
      storageClassName: "my-storage-class"
      resources:
        requests:
          storage: 1Gi
```

## 참고
- https://m.blog.naver.com/PostView.naver?isHttpsRedirect=true&blogId=isc0304&logNo=221885403537
