# Project 관련 복기

## Node.js 비동기/동기 처리

- Node.js는 비동기 I/O를 지원하면 Single-Thread 방식으로 동작
- 병렬 Thread가 아니기에 Multi-Thread가 갖는 근원적인 문제에서 자유롭다
  - 병렬 Thread는 클라이언트 요청마다 Thread 발생
  - 동시 접속자 수가 늘어나면 Thread도 늘어난다
  - 하지만 서버의 자원은 제한되어 있으므로 일정 수 이상의 Thread는 발생시킬 수 없음
  - 그래서 서버 업그레이드 or Load-Balancing

<img src="https://user-images.githubusercontent.com/41468004/141021923-6469daa1-014b-4cd0-968a-4255abdaf725.png" alt="image"  />

### 이벤트 루프

- 클라이언트의 요청을 비동기로 처리하기 위해 이벤트 발생 -> 서버 내부에 메시지 형태로 전달
- 이 메시지를 Event Loop가 처리
- Event Loop가 작업을 처리하는 동안 제어권은 다음 요청으로 넘어가고 처리가 완료되면 Callback 호출해서 처리완료를 호출측에 알려줌(비동기)

### Node.js의 약점

- Event Loop는 Single-Thread로 되어있음
- 즉, 요청 처리는 하나의 Thread 안에서 처리된다
- 그래서 이벤트 호출은 비동기(콜백)으로 처리되지만 그 처리작업 자체가 오래 걸린다면 서버 전체에 영향을 끼칠 수 있음

### Node.js의 올바른 사용

- Single Thread이기에 대용량 파일 작업에는 안좋음
- IO 작업이 별로 없거나 짧은 메시징 처리의 경우에는 빛을 발한다

- 또한 가능한 비동기 처리 로직으로만 짜는 것이 중요하다(callback)

### 올바른 Callback 사용

- 비동기 처리 방식에서 Callback을 사용함으로써 비동기도 챙기고 실행 순서도 보장받을 수 있음
- 다만 콜백 지옥에 빠지는 경우 존재
- 이를 `async`로 해결 가능

### Async & Await

```javascript
async function 함수명() {
  await 비동기_처리_함수명(); //이때 이 함수가 await의 대상이 되는 axios 등 Promise를 반환하는 API호출 함수이어야 함
}

/////////

function fetchItems() {
  return new Promise(function(resolve, reject) {
    let items = [1,2,3];
    resolve(items);
  })
}

async function logItems() {
  let resultItems = await fetchItems();
  console.log(resultItems);
}
```



## Node.js의 메모리 관리 동작 방식

### JavaScript 동작원리(Single-Thread)

<img src="https://user-images.githubusercontent.com/41468004/137964792-51ff6b39-0260-4362-8d3f-d3e65e40776b.png" style="zoom:50%;" />

#### Call Stack

- 메인 쓰레드에서 호출되는 함수들은 콜 스택에 쌓인다
- 이 함수들은 LIFO 방식으로 실행
- 싱글 스레드 기반이기에 하나의 메인 쓰레드와 하나의 콜 스택을 가지고 있다

#### Event Loop

- 이벤트 발생 시 호출되는 콜백 함수들을 관리해서 테스크 큐에 쌓아 놓는다
- 이벤트 루프가 콜 스택을 확인하고 비어있는 경우에만 Callback Queue에 있는 콜백 함수를 넘겨준다

#### Callback Queue

- web api에서 비동기 작업들이 실행된 후 호출된 콜백 함수들이 기다리는 공간
- 이벤트 루프가 정해준 순서로 기다리게 되고 FIFO로 처리



### Node.js의 GC

- 참조형 객체들이 더 이상 참조되지 않을 시에 GC 대상이 된다

#### New Space & Old Space

- New Space는 새로운 할당이 일어나는 곳
- GC가 자주 일어나는 곳
- Old Space는 New Space에서 살아남은 애들이 존재한다
- Old Space에서의 GC는 비용이 비싸기에 자주 일어나지 않는다



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

> 리눅스 컨테이너를 기반으로 하여 특정한 서비스를 패키징하고 배포하는 오픈소스 가상화 플랫폼

### Container(컨테이너)

![](https://user-images.githubusercontent.com/55429912/120686767-4dad8a00-c4dc-11eb-8d41-327fe1b45d27.png)

`격리된 공간에서 프로세스가 동작하는 기술`

- 애플리케이션 구동에 필요한 라이브러리 및 실행파일만 존재

### Image(이미지)?

<img src="https://user-images.githubusercontent.com/55429912/121000856-a6737000-c7c5-11eb-9efc-1f9d592eedb5.png" style="zoom:50%;" />

`컨테이너 실행에 필요한 파일과 설정값 등을 포함하고 있는 것`

- 상태값을 가지지 않고 변하지 않는다
- 이미지는 컨테이너를 실행하기 위한 모든 정보를 가지고 있음 그래서 의존성 파일 설치 후 별도의 설치가 필요 없음
  - 새로운 서버 추가되면 미리 만들어져있는 이미지를 다운받고 컨테이너를 생성하면 된다

### 레이저 저장방식

![](https://user-images.githubusercontent.com/55429912/121001380-31546a80-c7c6-11eb-8337-63567cf30c10.png)

`이미지를 빌드할 때마다 이미 생성된 레이어가 캐시되어 재사용되기에 빌드 시간을 단축할 수 있다`

#### 생성되는 결과물들은 어디에?

> 도커 컨테이너가 실행되면 모든 읽기 전용 레이어들을 순서대로 쌓은 다음 마지막에 쓰기 가능한 신규 레이어를 추가한다.
> 그 다음에 컨테이너 안에서 발생하는 결과물들이 쓰기 가능 레이어를 기록하게 되는 것

### 컨테이너 업데이트

![](https://user-images.githubusercontent.com/55429912/121002492-5c8b8980-c7c7-11eb-90be-c5a43934e058.png)

1. 새 버전의 이미지를 다운
2. 기존 컨테이너를 삭제
3. 새 이미지를 기반으로 새 컨테이너 실행

#### 장점

- 애플리케이션 빠르게 배포 가능
- 설치 및 확장이 쉽고 이식성 좋음
- 관리가 쉽다



## Redis

> - 메모리 기반의 Key-Value 구조 데이터 관리 시스템
> - 메모리에 저장하기에 빠른 Read,Write 속도를 보장하는 **비 관계형 데이터베이스**
> - String, Set, Sorted Set, Hash, List의 데이터 형식을 가진다

#### Memcached와 비교하면 다양한 데이터 타입을 제공하며 데이터를 디스크에 저장할 수 있어서 데이터와 빠이빠이 안해도 된다

### Redis 특징

- 마스터-슬레이브 복제를 지원
  - 슬레이브가 마스터에 연결하고 전체 DB의 초기 복사본을 받는 방식
  - 마스터에 쓰기가 수행되는 슬레이브 데이터 세트를 실시간으로 업데이트(마스터 내용 모두 슬레이브로 이동)

- 쓰기 성능 증대를 위한 클라 측 샤딩을 지원
  - 샤딩? == 파티셔닝
  - 같은 테이블 스키마를 가진 데이터를 다수의 DB에 분산해서 저장하는 방법
- 다양한 데이터형 제공
  - Hash, Set, List, String, Sorted Set

### Redis 장점

- 리스트, 배열과 같은 데이터 처리에 용이
- 리스트형 데이터 입력과 삭제가 MySQL대비 10배 빠르다고 한다
- Redis Server는 1개의 싱글 쓰레드로 수행됨, 따라서 서버 하나에 여러개 레디스 서버 띄우기 가능
- 스냅샷 기능을 이용해서 메모리의 내용을 디스크에 저장해서 추후에 복구 가능



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

### 가급적 Where문에서는 인덱스 컬럼 사용

- 인덱스를 만들어 놓아도 Where 조건을 어떻게 명시하냐에 따라서 인덱스 사용 or 미사용할 수도
- COLUMN_B, COLUMN_A를 묶어서 INDEX_A로 해놓을떄 두 컬럼을 모두 Where안에서 사용해야 INDEX_A가 사용된다

### 인덱스 컬럼에 사용하는 연산자는 최대한 동등 연산

- LIKE와 같은 연산을 사용하면 인덱스 효율 떨어진다

### OR 보다는 AND를 사용하자

- OR의 처리는 OR 연산자로 연결된 쿼리를 UNION ALL로 변환한다

### 그루핑 쿼리를 사용할 경우 가급적 HAVING 보다는 WHERE절에서 조건 처리하자

- 그루핑 쿼리 처리 순서는 WHERE가 먼저 처리되므로 가급적 필터링은 WHERE에서
- HAVING 절은 이미 WHERE절에서 처리된 로우들을 대상으로 조건을 걸기에 비교적 좋은 방법이 아니다

