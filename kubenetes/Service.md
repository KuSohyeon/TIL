## Service

> pod 집합에서 실행중인 애플리테이션을 네트워크 서비스로 노출하는 추상화 방법

Kubernetes pod는 생성될때 랜덤하게 IP 주소를 가지게 된다. 만약 여러 pods에 같은 애플리케이션을 운영할 경우 이 pods 간에 로드밸런싱을 지원해야한다.
고객에게 서비스를 제공하기 위해서는 고정된 IP와 DNS가 필요할 것이며 이때 사용 되는 것이 Service 오브젝트 이다.
Kubernetes Service 오브젝트를 이용하면 pod에게 고유한 IP 주소와 pods 집합에 대한 단일 DNS를 부여하고, 로드밸런싱을 수행할 수 있다.


- Service 예시

```yaml
apiVersion: v1
kind: Service  // 현 yaml이 어떤 object를 생성하기 위함인지를 kind에 정의
metadata:
  name: my-service
spec:
  selector: // service object가 요청을 전달할 pod를 찾기 위한 검색어
    app: MyApp
  ports: // Service object에 설정한 포트 정보
    - protocol: TCP
      port: 80 // service object에 설정할 포트 정보 
      targetPort: 9376 // service object로 들어온 요청을 전달할 target이 되는 pod가 노출하고 있는 포트.deployment

```

## 참고
https://waspro.tistory.com/520
