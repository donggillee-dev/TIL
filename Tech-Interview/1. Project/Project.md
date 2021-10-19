# Project 관련 복기

## Node.js 비동기/동기 처리

- https://meetup.toast.com/posts/73

## Node.js의 메모리 관리 동작 방식

- https://codingjuny.tistory.com/58
- https://hyunjun19.github.io/2018/02/19/node-js-at-scale-node-js-garbage-collection/



## JPA vs QueryDSL

- JPA에서 제공해주는 메서드로 처리 가능한 쿼리의 경우 JPA로 처리
- 다중 조인, fetch(다중 컬럼 조회) 같은 경우 Custom 인터페이스를 정의 -> 해당 인터페이스를 상속받아 Qclass를 통해 로직을 구현하였습니다

## QueryDSL vs JPQL

- ERD가 상당히 복잡하였기에 다중조인이 필수적인 상황
- QueryDSL을 이용하기에는 복잡한 이슈들 많이 발생
  - 우선 사용법에 대한 숙지가 완벽하지 못했다
  - 프로젝트 기간이 얼마 남지 않았다
  - 즉 비교적 쉬운 쿼리들은 QueryDSL로 처리하고 복잡한 다중조인(5중 조인 ㅎㄷㄷ...)들의 경우에는 JPQL로 쿼리를 짜자
    - 여기서 또 핵심적인 문제가 발생하는데 트랜잭션을 반환해주지 않아 Connection leak발생

## WebRTC란?

- WebRTC는 웹 브라우저 환경 및 Andriod, IOS 애플리케이션에서도 사용 가능한 비디오, 음성 및 일반 데이터가 피어간에 실시간으로 전송되도록 지원하는 오픈 소스

- 공개 웹 표준으로 구현, 모든 주요 브라우저에서 일반 JavaScript API로 제공



### WebRTC의 기술 및 프로토콜

### ICE

- 브라우저가 peer를 통한 연결이 가능하게 해주는 프레임워크



#### Peer간 단순 연결 시 작동하지 않는 이유

1. 연결을 시도하는 방화벽을 통과해야 함
2. 단말에 Public IP가 없다면 유일한 주소값 할당이 필요
3. 라우터가 Peer간의 직접 연결을 허용하지 않을 때 데이터 릴레이 하는 경우



##### ICE는 위의 작업들을 수행하기 위해 STUN, TURN 서버 둘 다 혹은 하나의 서버를 사용한다



### STUN 서버

- 클라이언트 **자신의 Public Address(IP:PORT) 를 알려준다**

- peer간의 직접 연결을 막는 등의 라우터의 제한을 결정하는 프로토콜

  - 현재 다른 peer가 접근 가능한지 여부 결정

- 클라는 인터넷을 통해 클라의 Public Address와 라우터의 NAT 뒤에 있는 클라가 접근 가능한지에 대한 답변을 STUN서버에 요청

  ![STUN Server](https://user-images.githubusercontent.com/41468004/126253431-2735b62a-8954-4ed8-a29f-bdae0c65208d.png)



### NAT

- 단말에게 **Public IP주소를 할당**하기 위해 사용
- 인터넷의 공인 IP주소는 한정되어 있기에 가급적 이를 공유할 수 있도록 하는게 필요
  - NAT을 이용하여 사설 IP사용함과 동시에 이를 공인IP로 상호변환 가능하게 => 다수의 사설IP사용자들이 공인IP를 사용함으로 IP절약 가능
- 공개된 인터넷과 사설망 사이에 방화벽 설치해 **외부 공격으로부터 사용자의 통신망 보호 가능**
  - 외부 통신망과 연결되는 라우터에 NAT을 설정하면 라우터는 자신에게 할당된 공인 IP만 외부로 알려지게 함
  - 라우터 안쪽에서는 사설 IP만 사용함으로 공인 주소 <=> 사설 주소 상호변환
  - 외부에서 내부 공격 위해서는 사설IP를 알아야하는데 이것이 불가능



- 몇몇 라우터들은 **Symmetric NAT** 이라고 불리는 **제한을 위한 NAT** 을 채용
  - 이로 인해 **peer들은 오직 이전에 연결한 적 있는 연결들만 허용**
  - 따라서 **STUN 서버**에 의해 공개 IP를 발견했다고 해도 모두 연결 가능한건 아님 => **TURN 서버** 필요



### TURN 서버

- **TURN 서버와 연결, 모든 정보를 그 서버에 전달**하여 **Symmetric NAT 제한을 우회**하는 것을 의미

- **TURN 서버와 연결**한 후 **모든 peer들에게 저 서버에게 모든 패킷을 보내고 다시 나(TURN서버)에게 전달**

- 명백히 오버헤드가 발생하기에 다른 대안이 없을떄 사용해야함

  [![TURN Server](https://user-images.githubusercontent.com/41468004/126253493-d19be8ef-e51e-4be5-882b-d5a395e67361.png)](#https://user-images.githubusercontent.com/41468004/126253493-d19be8ef-e51e-4be5-882b-d5a395e67361.png)



### Kurento

- 전체 WebRTC 스택의 기능적 구현을 제공하는 미디어 서버 및 Open API



## Docker



## 

## Redis

- 정리해야함

## JSON Web Token(JWT)이란?

### 기본 정보

- 웹표준으로 두 개체(EndPoint)에서 JSON 객체를 사용해 가볍고 자가수용적 방식으로 정보를 안정하게 전달
  - 자가 수용적?
    - JWT 시스템에서 발급된 토큰은 토큰에 대한 기본정보, 전달할 정보(ex. 유저정보), 토큰 검증 여부(signature)를 알려준다
- 서버와 클라이언트 간 정보를 주고 받을 떄 HTTP request Header에  JSON 토큰을 너어서 요청 => 서버는 별도 인증 과정 없이 헤더에 포함되어 있는 토큰을 통해 인증
- HMAC 알고리즘을 사용해 비밀키 or RSA를 이용한 공개키/비밀키 쌍으로 서명 가능
- Header, Payload, Signature로 구성
  - Header
    - Token의 타입(jwt, jws 등) 과 해시 암호화 알고리즘으로 구성
    - Base64Url로 되어있지만 실제 데이터는 JSON형태로
  - Payload
    - 토큰에 담을 claim(사용자) 정보를 포함
    - Payload에 담는 정보의 한 조각을 클레임이라 한다
    - key, value 쌍으로 이루어져 있음
    - 토큰에는 여러개의 클레임 넣을 수 있음
  - Signature
    - secret key를 포함해 암호화되어 있다
    - Header에 대한 Base64Url을 적용한 값 + payload에 대한 Base64Url을 적용한 값들 => 얘네를 Header에 구성된 알고리즘으로 적용한 결과값
    - Header, Payload의 변조 여부 검증
    - Base64 -> 쉽게 디코딩 가능하지만 비밀키 때문에 payload 조작 불가
      - 비밀키는 서버측에 존재

## ModelMapper, MapStruct

위 Entity와 Dto를 매핑해주기 위한 가장 대표적인 매핑 라이브러리

보통은 객체의 프로퍼티 값을 단순하게 매핑(get, set)하거나

일부 형변환 정도가 대부분인 작업이다

- 즉, 반복과 중복이 필연적인 작업
- 매핑 코드에 대한 휴먼 오류가 발생할 가능성이 농후한 작업이다
- 이런 오류와 반복적인 작업을 최소화 할 수 있는 것이 위 두개



### 차이점?

- 트랜드
  - 압도적으로 MapStruct가 많다
  - 단순히 이거만으로 MapStruct 채택?
- 기술적 측면
  - MapStruct가 구현의 방식, 성능적 측면에서 유리한 부분이 더 많다
  - 압도적으로 속도차이가 난다
  - 속도로 인해 처리량의 차이가 많이 난다



## 쿼리 튜닝 방식

- https://cornswrold.tistory.com/87