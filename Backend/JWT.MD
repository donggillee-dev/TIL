# JWT



## JSON Web Token이란?

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



### JWT 발급/검증 동작 방식

![jwt 발급.검증 동작 방식](https://user-images.githubusercontent.com/41468004/127089069-e127a6b8-df72-4fd9-a46d-713a3a2e2bca.png)

## Spring Security?



### 보안 용어들

- Principal(접근 주체) : 보호된 리소스에 접근하는 대상
- Authentication(인증) : 보호된 리소스에 접근한 대상에 대하여 어떤 유저인지, 작업을 수행해되 되는지에 대해 확인하는 과정
- Authorize(인가) : 해당 리소스에 대해 접근 가능한 권한을 가지고 있는지 확인하는 과정
- Permission(권한) : 어떠한 리소스에 대한 접근 제한, 모든 리소스는 접근 제어 권한이 걸려있다. 즉, 인가 과정에서 해당 리소스에 대한 제한된 최소한의 권한을 가졌는지 확인
