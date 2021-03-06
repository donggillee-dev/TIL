# Network

- [OSI 7 Layer](#osi-7-layer)
- [TCP](#tcp)
- [UDP](#udp)
- [HTTP](#http)
- [HTTPS](#https)
- [대칭키 & 공개키](#대칭키-&-공개키)
- [GET & POST](#get-&-post)
- [Cookie & Session](#cookie-&-session)
- [REST & RESTful](#rest-&-restful)
- [Blocking IO & Non-Blocking IO](#blocking-io-&-non-blocking-io)
- [DNS](#dns)

- [Web Server, WAS의 차이](#web-server,-was의-차이)

## OSI 7 Layer

![](https://user-images.githubusercontent.com/55429912/120319789-5ce5d980-c31c-11eb-8e73-021f9fb224bd.png)

- 프로토콜을 기능별로 나눔
- 각 계층은 하위 계층의 기능만을 이용하고, 상위 계층에게 기능을 제공



| 계층 | 이름                         | 기능                                                         | 단위                | 프로토콜         | 장비                 |
| ---- | ---------------------------- | ------------------------------------------------------------ | ------------------- | ---------------- | -------------------- |
| 7    | 응용<br />(Application)      | - 사용자 인터페이스, 데이터베이스 관리 등의 서비스 제공      | Message, <br />Data | DNS              | L7 Switch            |
| 6    | 표현<br />(Presentation)     | - 파일 인코딩, 암호화, 압축 등 기능을 제공                   |                     |                  | L6 Switch            |
| 5    | 세션<br />(Session)          | - 데이터 통신을 위한 논리적 연결 담당<br />- TCP/IP 세선을 만들고 없애는 책임을 진다 |                     | API,<br />Socket | L5 Switch            |
| 4    | 전송<br />(Transport)        | - TCP / UDP 프로토콜을 통한 통신 활성화<br />- TCP : 신뢰성 / 연결지향적<br />- UDP : 비신뢰성 / 비연결성 / 실시간 | Segment             | TCP,<br />UDP    | L4 Switch            |
| 3    | 네트워크<br />(Network)      | - 데이터를 목적지까지 가장 안전하고 빠르게 전달하는 기능<br />- 라우터를 통해 이동할 경로를 선택, IP주소를 할당하고 해당 경로에 따라 패킷을 저장<br />- 라우팅, 흐름제어, 오류제어, 세그멘테이션 등을 수행<br />- Peer to Peer 단에서 사용된다 | Packet              | IP,<br />ARP     | 라우터               |
| 2    | 데이터 링크<br />(Data link) | - 물리 계층으로 송/수신되는 정보의 오류와 흐름을 관리하여 안전한 정보의 전달을 수행할 수 있도록 도움<br />- MAC 주소를 이용해 통신<br />- Frame에 MAC주소를 부여하고 에러검출, 재전송, 흐름 제어를 진행한다 | Frame               | MAC, <br />PPP   | 브리지,<br /> 스위치 |
| 1    | 물리<br />(Physical)         | 전기적, 기계적, 기능적인 특성을 이용해 데이터 전송           | Bit                 | Ethernet RS-232C | 허브,<br /> 리피터   |



## TCP

> Transport Layer에서 사용하는 프로토콜 중 하나



### TCP(Transmission Control Protocol)

![](https://user-images.githubusercontent.com/55429912/120324955-29a64900-c322-11eb-8274-48587566b752.png)

- 신뢰성 있는 데이터 전송을 지원하는 연결 지향형 프로토콜
- 모든 TCP 연결은 전이중(full-duplex), 점대점(point to point) 방식
  - 전송이 양방향으로 일어날 수 있으며 확실한 2개의 종단점을 가지고 통신
- `흐름제어, 혼잡제어, 오류제어`를 통해 신뢰성을 보장(but, 이것 때문에 UDP보다는 속도 느림)

- 일반적으로 IP와 함께 사용되며 IP가 데이터 배달을 처리한다면 TCP는 패킷을 추적 및 관리한다.
  - 데이터가 여러 개의 패킷들로 나뉘고 패킷 번호를 붙인 후 송신, 수신 측에서는 각 패킷들을 재조립



#### 3-Way-Handshake & 4-Way-Handshake

##### 연결 성립(3-way-handshake)

![](https://camo.githubusercontent.com/4acea6af95884347810f057d00c6c4643a56d4a7dbbdf49740745560cd45cc1f/68747470733a2f2f6d656469612e6765656b73666f726765656b732e6f72672f77702d636f6e74656e742f75706c6f6164732f5443502d636f6e6e656374696f6e2d312e706e67)

1) 클라이언트는 서버에 접속을 요청하는 SYN(x) 패킷을 보냄
   - 클라 : 내 목소리 잘 들림?
2) 서버는 클라이언트의 요청인 SYN(x)를 받고 클라이언트에게 요청을 수락한다는 ACK(x + 1)과 SYN(y)이 설정된 패킷을 발송
   - 서버 : 어 너 목소리 잘 들려, 내 목소리는 잘 들려?
3) 클라이언트는 서버의 수락 응답인 ACK(x + 1)와 SYN(y)가 설정된 패킷을 받고 ACK(y + 1)을 서버로 보내면 연결이 성립
   - 클라 : 어 너의 목소리도 잘 들리네 우리 연결하자



##### 연결 해제(4-way-handshake)

![](https://camo.githubusercontent.com/8bb8960e46a3bfada6a237a7a91bce75a0a3e0e34eab5c1f5143ca6fe34d0b5f/68747470733a2f2f6d656469612e6765656b73666f726765656b732e6f72672f77702d636f6e74656e742f75706c6f6164732f434e2e706e67)

1. 클라이언트는 서버에게 연결을 종료한다는 FIN 플래그를 전송
2. 서버는 FIN 플래그를 받고, 연결 종료한다는 것을 인지했다는 신호(ACK)를 보낸다
   - 이때 모든 데이터를 보내기 위해 CLOSE WAIT 상태가 됨
3. 데이터를 모두 보냈다면 연결이 종료되었다는 FIN 플래그를 클라이언트에게 전송
4. 클라이언트는 FIN을 받고 확인했다는 ACK를 보낸다
   - 이때 아직 서버로부터 받지 못한 데이터들이 있을 수 있으므로 TIME_WAIT를 통해 조금 더 대기
5. ACK를 받은 서버측은 소켓을 닫는다
6. TIME_WAIT 시간이 지나면 클라도 소켓 닫음



### 흐름 제어

`송신측과 수신측의 데이터 처리 속도 차이를 해결하기 위한 기법`

- 수신측이 송신측보다 속도가 빠른 것은 문제가 되지 않음
- 송신측이 수신측보다 속도가 빠르면 문제
  - 제한된 저장용량을 초과해 원래보다 이후에 도착하는 데이터의 손실을 유발할 수 있음

##### 흐름 제어 방식

1. Stop & Wait

   ![](https://user-images.githubusercontent.com/55429912/120322385-50af4b80-c31f-11eb-9bfa-3ac3898f0294.png)

   - 매번 전송한 패킷에 대해 확인응답을 받아야만 그 다음 패킷을 전송하는 순차적인 방법

   

2. 슬라이딩 윈도우

   ![](https://user-images.githubusercontent.com/55429912/120322412-586ef000-c31f-11eb-91d5-9fc8292c93b5.png)

   - 수신 측에서 설정한 윈도우 크기만큼 송신측에서 확인 응답 없이 세그먼트를 전송할 수 있게 하여 데이터 흐름을 동적으로 조절하여 제어하는 기법
     - 송신 버퍼의 범위는 수신 측의 여유 버퍼 공간을 반영하여 동적으로 바뀜



### 혼잡 제어

`송신측의 데이터 전달과 네트워크의 데이터 처리 속도 차이를 해결하기 위한 기법`

- 한 라우터에 데이터가 몰리게 될 경우, 라우터는 자신에게 온 데이터를 모두 처리할 수 없어 오버플로우 또는 손실 발생

##### 혼잡 제어 방식

1. AIMD(Additive Increase / Multiplicative Decrease)

   ![](https://user-images.githubusercontent.com/55429912/120349614-371afd80-c339-11eb-8e5c-c07848c137a8.png)

   > 처음 패킷을 하나씩 보내고 문제없이 잘 도착하면 윈도우 크기(단위 시간에 보내는 패킷 수)를 1씩 증가, 만일 패킷 전송 실패 or TIME_OUT 발생하면 윈도우 크기를 절반으로 줄임

   장점 :

   - 공평한 방식으로 나중에 진입하는 쪽이 처음에는 불리하지만 시간이 흐르면 평형 상태로 수렴

   단점 :

   - 초기에 네트워크의 높은 대역폭을 사용하지 못해 오랜시간이 걸림
   - 네트워크가 혼잡해지는 상황을 미리 감지하지 못함
     - 네트워크가 혼잡해지고 나서 윈도우를 반감시키는 방식임

2. Slow Start

   ![](https://user-images.githubusercontent.com/55429912/120423796-163cc180-c3a6-11eb-9b05-894c6af82b34.png)

   > 윈도우 크기를 2배씩 증가해 나가다가, 혼잡이 발생하면 윈도우 크기를 1로 줄임
   >
   > 임계점은 혼잡이 생긴 부분에서 절반

   - AIMD 방식보다 빠르게 윈도우 크기 확장
   - 임계점을 설정하여 Slow Start 제어
     - slow start threshold(ssthresh) : 임계점
     - 윈도우 크기를 지수적으로(2 ^ n) 증가시키다 보면 크기가 기하급수적으로 커져 관리 힘듬
     - 그러므로 임계점 도달하면 선형으로 1씩 증가

3. Fast Retransmit(빠른 재전송)

   ![](https://user-images.githubusercontent.com/55429912/120423856-32d8f980-c3a6-11eb-98a8-a6b4b788b1a6.png)

   > 수신측에서 마지막으로 잘 도착한 패킷의 번호를 ACK패킷에 실어서 보내고, 동일 번호 ACK 중복 3번 되면 손실 부분 재전송

   - 패킷을 받는 수신자 입장에서는 세그먼트로 분할된 내용들이 순서대로 도착하지 않는 경우가 생길 수 있음
   - 송신 측은 자신이 설정한 타임아웃 시간이 지나지 않아도 바로 해당 패킷을 재전송할 수 있기에 빠른 전송률 유지 가능
   - 송신측에서 설정한 타임아웃까지 ACK를 받지 못하면 혼잡이 발생한 것으로 판단해 혼잡 회피한다

4. Fast Recovery

   ![](https://user-images.githubusercontent.com/55429912/120423932-63209800-c3a6-11eb-9274-ef18e1897dac.png)

   > 혼잡한 상태가 되면 임계점은 기존에서 1낮게
   >
   > 그리고 윈도우 크기는 1로
   
   - 혼잡 상황 후 Slow Start에서 윈도우 크기를 다시 키울 때 너무 오래 걸린다는 단점을 보완한 방식
   - 혼잡 상황 이후에는 AIMD방식으로 동작한다



### 오류제어

`오류를 검출하고 정정하는 기능`

- ARQ(Automatic Repeat Request) 기법을 이용해 프레임이 손상되었거나 손실된 경우, 재전송을 통해 오류를 복구

1. Stop and Wait ARQ

   - 송신측에서 1개의 프레임을 송신하고, 수신측에서 수신된 프레임의 에러 유무에 따라 ACK/NAK를 보내는 방식
   - 수신측이 데이터를 받지 못한 경우, NAK를 보내고 NAK를 받은 송신측은 데이터를 재전송
   - 데이터나 ACK가 분실되었을 경우 일정 간격의 시간을 두고 타임아웃이 발생하면 송신측은 데이터를 알아서 재전송

2. Go-Back-N ARQ(슬라이딩 윈도우)

   - 전송된 프레임이 손상되었거나 분실된 경우, 확인된 마지막 프레임 이후의 모든 프레임을 재전송

     ![](https://user-images.githubusercontent.com/55429912/120425976-28206380-c3aa-11eb-8f8f-c7317b2183b0.png)

3. SR(Selective-Reject) ARQ

   - 손상, 손실된 프레임만 재전송(GBN ARQ의 손실 프레임 이후 정상 전송된 모든 프레임을 다시 보내는 단점을 보완)
   - 별도의 데이터 재정렬을 수행(손실된 프레임의 원래 자리 찾아줘야함)
   - 수신측에 별도의 버퍼를 두어 받은 데이터의 정렬을 수행한다

   |               GBN(Go-Back-N) ARQ               |              SR(Selective-Reject) ARQ               |
   | :--------------------------------------------: | :-------------------------------------------------: |
   | 손상, 손실된 프레임 이후의 모든 프레임 재전송  |            손상, 손실된 프레임만 재전송             |
   |         구조가 비교적 간단, 구현 단순          |            구조가 복잡(프레임 재정렬 등)            |
   | 데이터 폐기 방식을 사용해 추가적인 버퍼 필요 x | 순차적으지 않은 프레임에 대한 정렬을 위해 버퍼 필요 |
   |               비용이 비교적 저렴               |               비용 및 유지비용이 높음               |

   

### TCP Header

> TCP는 상위계층으로부터 데이터를 받아 헤더를 추가해 IP로 전송

![](https://user-images.githubusercontent.com/55429912/120325539-b8b36100-c322-11eb-995c-0b53bfcb99ce.png)

- Source Port
  - 16 Bit
  - TCP로 연결되는 가상 회선 양단 중 송신 프로세스의 포트 주소
- Destination Port
  - 16 BIt
  - TCP로 연결되는 가상 회선 양단 중 수신 프로세스의 포트 주소
- Sequence Number
  - 32 Bit
  - 송신자가 지정하는 순서 번호, 전송되는 바이트 수 기준으로 증가
  - 클라에서 초기에 SYN을 보낼때 Sequence Number에 난수를 넣음
    - **Why?**
    - Connection을 맺을 때 사용하는 포트는 유한 범위 내에서 사용, 그렇기에 재사용될 수도
    - 따라서 두 통신 호스트가 과거에 동일하게 사용된 포트 쌍을 사용하게 되는 경우도 존재
    - 서버 측에서는 패킷의 SYN을 보고 패킷을 구분하는데 난수가 아니라 순차적 number로 되어있으면 이전 connection으로 부터 오는 패킷이라고 처리해버릴 수도 있기에 난수로 처리
- Acknowledgment Number
  - 32 Bit
  - 수신 프로세스가 제대로 수신한 바이트의 수 응답 용
- Header Length(Data Offset)
  - 4 Bit
  - TCP 헤더 길이를 표시
- Resv(Reserved)
  - 6 Bit
  - 나중을 위해 0으로 채워진 예약 필드
- Flag Bit
  - 6 Bit
  - URG : 긴급 위치 필드 유효 여부 설정
  - ACK : 응답 유효 여부 설정(정상 수신시 ACK(=SYN + 1)전송)
  - PSH : 수신측에 버퍼링된 데이터 상위 계층에 즉시 전달
  - RST : 연결 리셋 응답 혹은 유효하지 않은 세그먼트 응답
  - SYN : 연결 설정 요청. 양쪽이 보낸 최초 패킷에만 SYN 플래그 설정
  - FIN : 연결 종료 의사 표시
- Window Size
  - 16 Bit
  - 수신 윈도우의 버퍼 크기 지정(0이면 송신 중지)
  - 상대방의 확인 없이 전송가능한 최대 바이트 수
- TCP Checksum
  - 16 Bit
  - 헤더와 데이터의 에러 확인 용도
- Urgent Pointer(긴급 위치)
  - 16 Bit
  - 현재 순서 번호부터 표시된 바이트 까지 긴급한 데이터임을 표시
  - URG 플래그 비트가 지정된 경우에만 유효
- Options
  - 0 ~ 40 Bit
  - 추가 옵션이 있을 경우 표시



## UDP

![](https://user-images.githubusercontent.com/55429912/120325014-332fb100-c322-11eb-8f72-66cdd55743b9.png)

- 비연결형, 신뢰성이 없는 프로토콜
- 데이터를 데이터그램 단위로 처리하는 프로토콜
- 데이터 전송의 신속성(실시간 방송, 온라인 게임 등에서 사용)
- 신뢰성보다는 연속성이 중요한 서비스에 사용(DNS)
- UDP헤더의 Checksum 필드를 통해 최소한의 오류만 검출



##### UDP Header

![](https://user-images.githubusercontent.com/55429912/120427071-38394280-c3ac-11eb-80ce-490c1c7abbc8.png)

- Source Port
  - 16 Bit
  - 송신 포트 번호
- Destination Port
  - 16 Bit
  - 수신 포트 번호
- Length
  - 16 Bit
  - 헤더와 데이터 포함 전체 길이
- Checksum
  - 16 Bit
  - 헤더와 데이터의 에러 확인 용도
  - UDP는 에러 복구를 위한 필드가 불필요하기에 TCP보다 훨씬 간단



##### DNS의 프로토콜은 UDP? TCP?

```
UDP
```



##### UDP를 사용하는 이유는?

```
- DNS 요청은 일반적으로 매우 작고, UDP 세그먼트에 적합
- UDP는 신뢰할 순 없지만, Application layer에서 Time out, resend를 통해 안정성을 보완할 수 있음
- UDP가 훨씬 빠름(DNS에서 서버의 로드도 중요한 요소이기 때문)
```



##### 그럼 TCP는 아예 안씀?

```
- 메시지가 512바이트를 초과하는 경우, TCP를 통해 질의 응답을 진행
- Zone-Transfer, Master-Slaver 구성시 Zone Transfer가 이루어지는데, 이때는 TCP를 사용하여 Zone File을 주고 받음
```



## HTTP

> HyperText Transfer Protocol
>
> 인터넷 상에서 클라이언트와 서버간에 요청/응답으로 정보를 주고 받을 수 있는 프로토콜

- 주로 HTML 문서를 주고받는데 사용한다

- TCP와 UDP를 사용하며 80번 포트를 사용

  - 통상적으로 TCP 소켓을 사용, 예외적으로 스트리밍 서비스에서는 UDP를 사용하는 경우도 존재

- TCP/IP를 이용하는 응용 프로토콜이다(Application protocol)

- HTTP는 **연결 상태를 유지하지 않는 비연결성 프로토콜**

  - 비연결 : 클라이언트가 요청을 서버에 보내고 서버가 적절한 응답을 보내면 바로 응답이 끊김

    ```
    이전 요청과 다음 요청이 연결되어 있지 않다는 의미
    하나의 요청 / 응답안에서는 연결된 상태
    HTTP프로토콜이 TCP기반으로 동작 : 하나의 요청 ~ 응답에 대해서 연결적
    ```

  - 연결을 끊는 순간 클라와 서버의 통신은 끝나며 상태 정보를 유지하지 않는다

  - 이런 단점을 해결하기 위해 Cookie Session Cache 사용

### HTTP 공통헤더

1. Date
2. Connection
3. Content-Length
4. Cache-Control
5. Content-Type
6. Content-Language
7. Content-Encoding

### HTTP 요청헤더

1. Host
2. Cookie
3. Origin

### HTTP 응답헤더

1. Server
2. Access-Control-Allow-Origin

### HTTP/0.9 - 원-라인 프로토콜

- 단일 라인으로 구성된 요청
- 가능한 메서드: GET
- 단순한 응답
- 파일 내용 자체로 구성

### HTTP/1.0

- 1번에 연결에 1번의 Request & 1번의 Response
- 매번 새로운 연결로 성능 저하
- 서버 부하 비용 증가

### HTTP/1.1

- TCP Connection 사용
- HTTP 헤더 + 바디로 구성
- HTTP Body가 사람이 읽을 수 있는 문자열 그대로 전송된다
- Persistent Connection
  - 지정한 Timeout 동안 커넥션을 닫지 않는 방식

- Pipelining
  - 하나의 커넥션에서 응답을 기다리지 않고 순차적인 여러 요청을 연속적으로 보내 그 순서에 맞춰 응답을 받는 방식으로 지연 시간을 줄이는 방법

- Head Of Line Blocking
  - 1개의 TCP 연결당 1개의 스트림만 이용 가능
  - 따라서, 패킷이 순서대로 도착해야함
  - 패킷이 도착할떄까지 그 이후의 패킷은 전송되지 못하고 있는 것

- Header 구조의 중복

### HTTP/2

- 기존 HTTP/1.x 버전의 **성능 향상**에 초점을 맞춘 프로토콜

- HTTP Body가 이진 데이터이다

  - 바이너리 프레이밍 계층 사용해서 이진 데이터로 전송
  - 파싱, 전송 속도 ↑, 오류 발생 가능성 ↓

- 요청과 응답의 다중화(Multiplexing)

  - 1개의 TCP연결 당 1개의 스트림만 이용 가능했던 단점을 해결
  - 따라서 TCP연결의 3-way-handshaking 오버헤드가 없음
  - Head Of Line Blocking 해결

- Stream Prioritization

  - 리소스간 우선 순위 설정 가능

- Server Push

  - 클라이언트에 필요한 데이터가 있을떄 직접 요청 전 서버가 미리 데이터를 전송해서 받아볼 수 있게끔 함

  [![image](https://user-images.githubusercontent.com/55429912/123412063-1ff3c680-d5ec-11eb-9893-9c9d5adfbe54.png)](https://user-images.githubusercontent.com/55429912/123412063-1ff3c680-d5ec-11eb-9893-9c9d5adfbe54.png)

- Header Compression

  <img src="https://user-images.githubusercontent.com/41468004/141018607-2788f29f-78b1-4b0d-b2e6-fd2dd68804d7.png" alt="image" style="zoom:50%;" />

  - HPACK 압축을 통해서 헤더를 압축
    - 왜 HPACK?
    - 기존 GZIP은 정보가 노출될 수 있는 단점이 존재
    - 첫번쨰 요청과 두번쨰 요청의 차이나는 바이트 수만 전송하는 방식
  - 헤더의 크기를 줄여 페이지 로드 시간 감소

### 서버와 클라의 HTTP 통신 과정

1. 사용자가 브라우저에 URL 입력
2. DNS 서버에 웹 서버의 호스트 이름을 IP주소로 변경 요청
3. 알아낸 IP로 웹 서버와 TCP연결 시도
   - 3-way handshake
4. 클라이언트가 서버에게 데이터 요청
5. 서버가 클라이언트에게 데이터 응답
6. 서버 클라이언트간 연결 종료
   - 4-way handshake
7. 웹 브라우저가 웹 문서 출력



## HTTPS

> HyperText Transfer Protocol Secure
>
> HTTP 통신하는 소켓 부분을 인터넷 상에서 정보를 암호화하는 SSL이라는 프로토콜로 대체한 것

- HTTP는 TCP와 통신하지만, HTTPS는 SSL과 통신하고 SSL이 TCP와 통신
- 장점
  - 네트워크 상에서 열람, 수정이 불가능하므로 안전
- 단점
  - 암호화를 하는 과정이 웹 서버에 부하를 줌
  - HTTPS는 설치 및 인증서를 유지하는데 추가 비용이 발생
  - 인터넷 연결이 끊긴 경우 재인증 시간이 소요
    - HTTP는 비연결형 웹 페이지를 보는 중 인터넷 연결이 끊겼다가 다시 연결되어도 페이지 계속 보기 가능
    - HTTPS는 소켓 자체에서 인증을 하기에 인터넷이 끊기면 소켓도 끊어져서 HTTPS 재인증 필요



#### 서버와 클라의 HTTPS 통신 과정

![](https://user-images.githubusercontent.com/41468004/137272157-c7d8de02-a874-4f77-8599-0fa17194f054.png)

1. 클라이언트가 SSL로 암호화된 페이지 요청
2. 서버는 클라이언트에게 인증서를 전송
   - 인증서에 포함된 내용
     - 서버측 공개키
     - 공개키 암호화 방법
     - 인증서를 사용한 웹서버의 URL
     - 인증서를 발행한 기관 이름
3. 클라이언트는 인증서가 신용있는 기관(cA)으로부터 서명된 것인지 판단(전자서명 활용)
   - 해당 인증기관으로부터 받은 공개키를 가지고 인증서를 복호화, 검증 서버의 공개키 획득
4. 클라이언트는 서버의 공개키를 이용하여 랜덤 대칭 암호화키(대칭키)를 생성 및 암호화하여 서버로 전송
5. 서버는 자신이 가지고 있는 개인키를 이용해서 이를 복호화해 대칭키, 데이터를 획득
6. 서버는 랜덤 대칭 암호화키로 클라이언트 요청에 대한 응답을 암호화하여 전송
7. 클라이언트는 랜덤 대칭 암호화키를 이용해 복호화하고 데이터를 이용





## 대칭키 & 공개키

### 대칭키

![](https://user-images.githubusercontent.com/55429912/120472340-bc0d2200-c3e0-11eb-8005-e00189af4b33.png)

`암호화와 복호화에 같은 암호키를 사용하는 알고리즘`

- 동일한 키를 주고 받기 때문에 매우 빠르다
- 대칭키 전달과정에서 해킹 위험에 노출될 수 있음



### 공개키

![](https://user-images.githubusercontent.com/55429912/120472372-c7f8e400-c3e0-11eb-9359-0486ad6d5a71.png)

`암호화와 복호화에 사용하는 암호키를 분리한 알고리즘`

- 자신이 가지고 있는 고유한 암호키(개인키, 비밀키)로만 복호화할 수 있는 공개키(암호화키)를 대중에게 공개

  **공개키 암호화 방식 진행 과정**

  1. A가 웹 상에 공개된 `B의 공개키`를 이용해 평문을 암호화하여 B에게 보냄

  2. B는 자신의 비밀키(개인키)로 복호화한 평문을 확인, A의 공개키로 응답을 암호화하여 A에게 보낸다

  3. A는 자신의 비밀키(개인키)로 암호화된 응답문을 복호화한다



#### 공개키는 대칭키의 단점을 완벽하게 해결했지만, 암호화/복호화 과정에 매우 복잡하다(암호화와 복호화에 사용되는 키가 관점에 따라 다르기 때문)

##### `공개키 + 대칭키 => HTTPS 통신`

1. A가 B의 공개키로 암호화 통신에 사용할 대칭키를 암호화하여 B에게 보냄
2. B는 암호문을 받아 자신의 개인키로 복호화
3. B는 복호화한 것 중 얻은 대칭키로 A에게 보낼 평문을 암호화하여 A에게 보냄
4. A는 자신의 대칭키로 암호문을 복호화
5. 이후부터 서로 대칭키로 암호화로 통신



## GET & POST

#### GET

- 클라이언트에서 서버로 어떠한 리소스에서 `데이터를 조회`하기 위해 사용되는 메서드

- GET을 통한 요청은 URL주소 끝에 파라미터로 포함되어 전송되며, 이 부분을 Query String이라고 부른다

  `www.example.com/show?name1=value1&name2=value2`

- URL을 이용하기에 데이터 길이 제한이 있으며 보안에 취약하다
- 요청 정보가 여러개일 경우에는 &로 구분
- GET 방식은 캐싱을 사용할 수 있기에 GET요청과 그 응답 결과는 브라우저에 의해 캐시된다.



#### POST

- 클라이언트에서 서버로 리소스를 생성하거나 업데이트하기 위해 `데이터를 변경`할때 사용되는 메소드
- HTTP Request Message의 Body부분에 데이터를 담아서 서버로 보냄
- 길이 제한이 따로 없어서 용량이 큰 데이터를 보거나, 보안이 필요할 때 사용
  - 데이터 암호 안하면 Body도 뜯어서 볼 수 있음



### GET POST의 가장 큰 차이점 : HTTP Message에 Body 유무



## Cookie & Session

- HTTP 프로토콜은 비연결, 상태정보를 유지하지 않으므로 모든 요청 간 의존관계가 없다
- 즉, 현재 접속한 사용자가 이전에 접속했더 사용자와 같은 사용자인지 아닌지 알 수 있는 방법이 없음
- 연결을 유지하지 않기 때문에 자원 낭비가 줄어드는 것은 큰 장점이지만, 통신마다 새로 연결하기 때문에 클라이언트는 매 요청마다 인증을 해야하는 단점이 존재
- HTTP 프로토콜에서 상태를 유지하기 위한 기술로 Cookie와 Session 존재



### Cookie?

`클라이언트 로컬에 저장되는 키와 값이 들어있는 파일`

- 서버가 HTTP 응답 헤더의 `Set-Cookie`에 내용을 넣어 전달하면, 브라우저는 이 내용을 자체적으로 브라우저에 저장
- 브라우저는 서버에 접속할 때마다 쿠키의 내용을 요청헤더에 넣어서 함께 전달
- 이름, 값, 유효 시간, 경로 등을 포함하고 있음
- 클라이언트의 상태 정보를 브라우저에 저장하여 참조
- 구성 요소
  - 쿠키의 이름(name)
  - 쿠키의 값(value)
  - 쿠키의 만료시간(Expires)
  - 쿠키를 전송할 도메인 이름(Domain)
  - 쿠키를 전송할 경로(Path)
  - 보안 연결 여부(Secure)
  - httpOnly 여부(httpOnly)
    - httpOnly 옵션이 설정된 쿠키는 document.cookie로 쿠키 정보를 읽을 수 없음(쿠키 보호 가능)

- 동작 방식

  ![](https://user-images.githubusercontent.com/55429912/120448562-a559d180-c3c6-11eb-98b4-abdada97f602.png)

  1. 클라이언트가 서버에 요청

  2. 상태를 유지하고 싶은 값을 쿠키로 생성한 후 응답할때 HTTP헤더에 쿠키를 포함해서 전송

     ```java
     Set-Cookie: id=id
     ```

  3. 전달받은 쿠키는 웹브라우저에서 관리하고 있다가, 다음 요청때 쿠키를 HTTP헤더에 넣어서 전송

     ```java
     cookie: id=id
     ```

  4. 서버에서는 쿠키 정보를 읽어 이전 상태 정보를 확인한 후 응답



### Session

`클라이언트와 웹서버 간 네트워크 연결이 지속 유지되고 있는 상태`

1. 클라이언트가 서버에 접속시 세션ID를 발급 받음
2. 클라이언트는 세션ID에 대해 쿠키를 사용해서 저장
3. 클라이언트는 서버에 요청할 때, 쿠키의 세션 ID를 서버에 전달해서 사용
4. 서버는 세션 ID를 전달 받아 별다른 작업없이 세션 ID로 세션에 있는 클라이언트의 정보 가져옴
5. 클라이언트 정보를 가지고 서버 요청을 처리하여 클라이언트에게 응답

**세션도 쿠키를 사용하여 값을 주고 받으며 클라이언트의 상태 정보를 유지한다. 즉, 상태 정보를 유지하는 수단은 쿠키이다.**



#### 쿠키 vs 세션

- 저장 위치
  - 쿠키: 클라이언트(서버 자원 사용 x)
  - 세션: 서버(서버 메모리로 로딩되기 때문에 세션이 생길 때마다 리소스 차지)
- 보안
  - 쿠키: 클라이언트에 저장되므로 보안에 취약
  - 세션: 쿠키를 이용해 Session ID만 저장하고 이 값으로 구분해서 서버에서 처리하므로 비교적? 보안성이 좋음
- 라이프 사이클
  - 쿠키: 만료시간에 따라 브라우저를 종료해서 계속해서 남아있을 수 있음
  - 세션: 만료시간을 정할 수 있지만 브라우저가 종료되면 만료시간에 상관없이 삭제
- 속도
  - 쿠키: 클라이언트의 브라우저에 저장되어서 서버에 요청시 빠름
  - 세션: 실제 저장된 정보가 서버에 있으므로 서버의 처리가 필요해 쿠키보다 느리다
- 용량 제한
  - 쿠키: 한 도메인당 20개, 하나의 쿠키당 4KB로 제한
  - 세션: 개수나 용량의 제한이 없음



## REST & RESTful

### REST(Representational State Transfer)란?

`웹에 존재하는 모든 자원(이미지, 동영상, DB자원 등)에 고유한 URI를 부여해 활용하는 것`

**특징**

- 웹의 장점을 최대한 활용할 수 있는 클라, 서버간 통신 중 하나
- `HTTP URI`를 통해 `자원` 명시, `HTTP Method`를 통해 `행위` 명시, `표현 형태` 명시
  - 즉, REST는 자원 기반의 구조 설계의 중심에 Resource가 있고 HTTP Method를 통해 Resource를 처리하도록 설계된 아키텍처를 의미
  - 웹 사이트의 이미지, 텍스트, DB 등 모든 자원에 고유한 ID인 HTTP URI부여(자원 부여)



**구성 요소**

1. 자원(Resource): URI
   - 모든 자원에 고유한 ID가 존재, 이 자원은 Server에 존재
   - 자원을 구별하는 ID는 '/groups/group_id'와 같은 HTTP URI이다
   - Client는 URI를 이용해 자원을 지정, 해당 자원의 상태에 대한 조작을 Server에 요청
2. 행위(Verb): HTTP Method
   - HTTP 프로토콜의 Method를 사용
     - GET(조회), POST(등록), PUT(수정), DELETE(삭제)
3. 표현(Representation of Resource)
   - Client가 자원의 상태에 대한 조작을 요청하면 Server는 이에 적절한 응답을 보냄
   - REST에서 하나의 자원은 JSON, XML, TEXT, RSS 등 여러 형태로 나타내어 질 수 있음
   - 주로 JSON, XML을 통해 주고받는다



**장점**

- HTTP 표준 프로토콜에 따르는 모든 플랫폼에서 사용 가능
- 서버와 클라이언트의 역할을 명확하게 분리
- HTTP 프로토콜의 인프라를 그대로 사용하므로 REST API 사용을 위한 별도의 인프라를 구축할 필요가 없음



**단점**

- 표준이 없다
- 사용할 수 있는 메소드가 4가지뿐



### REST API?

![](https://user-images.githubusercontent.com/55429912/120452596-451a5e00-c3cd-11eb-9eb7-a98902709588.png)

`REST를 기반으로 서비스 API를 구현한 것`

**API(Application Programming Interface)** : 데이터와 기능의 집합을 제공하여 컴퓨터 프로그램간 상호작용을 촉진하며, 서로간에 정보 교환이 가능하도록 하는 것

**특징**

- 확장성과 재사용성이 높아 유지보수 및 운용이 편리
- HTTP 표준을 기반으로 구현하므로, HTTP를 지원하는 프로그램 언어로 클라이언트와 서버 구현 가능



### RESTful?

![](https://user-images.githubusercontent.com/55429912/120452295-1308fc00-c3cd-11eb-9c89-dd106d121a4c.png)

`REST 하게 짜여진 시스템`

목적

- 이해하기 쉽고 사용하기 쉬운 REST API를 만드는 것



REST하지 못하다?

- CRUD 기능을 모두 POST로만 처리하는 행위
- route에 resource, id외의 정보가 들어가 있는 경우(/Customer/CustomerName)





## Blocking IO & Non-Blocking IO

### Blocking Model

![](https://user-images.githubusercontent.com/55429912/120461591-17d1ae00-c3d5-11eb-97a6-28ed98958dd5.png)

`IO 작업이 진행되는 동안 유저 프로세스는 자신의 작업을 중단한 채 대기하는 방식`

유저 프로세스는 block되어서 다른 작업을 수행하지 못하고 대기하게 되므로 자원이 낭비됨



### Non-Blocking Model

![](https://user-images.githubusercontent.com/55429912/120462110-92023280-c3d5-11eb-9137-db240089eba3.png)

`IO 작업이 진행되는 동안 유저 프로세스의 작업을 중단시키지 않음`

IO 진행시간과 관계가 없기에 작업을 중지하지 않고도 IO 가능, but 지속적으로 확인을 해야하기에 System call 반복호출로 자원 낭비



## DNS

### IP주소

> 컴퓨터 네트워크에서 장치들이 서로를 인식하고 통신하기 위해 사용하는 특수한 번호

- IPv4(32bit)로 구성되어 있으며, 127.0.0.1과 같은 주소를 말함
- 주소 공간의 부족으로 IPv6(128bit)가 탄생함



### 도메인 네임

> IP주소를 문자로 표현한 주소

- 도메인 네임은 사람의 편의성을 위해 만든 주소, 사용시에는 컴퓨터가 이해할 수 있는 IP주소로 변환 필요
- 도메인 네임과 해당하는 IP주소값이 한 쌍으로 저장되어있는 DB가 DNS(Domain Name System)임



#### DNS 처리과정

1. www.google.com을 브라우저 주소창에 침

2. Browser는 캐싱된 DNS 기록을 통해 www.google.com에 대응되는 IP주소가 존재하는지 확인

   - 가장 먼저 브라우저 캐시 확인
   - OS 캐시 확인
   - Router 캐시 확인
   - ISP(a.k.a skt, kt,,,) 캐시 확인

3. 요청한 URL이 캐시에 없다면 ISP의 DNS서버가 www.google.com을 호스팅하고 있는 서버의 IP주소를 찾기 위해 DNS Query 날림

   - `recursive search`를 통해 여기저기 DNS 서버들에서 찾을때까지 검색을 진행

   - 이 때, ISP의 DNS Server를 `DNS recursor`라 부르고 다른 DNS 서버들은 `name server`라고 불리운다

     ![](https://user-images.githubusercontent.com/41468004/137291522-b48a24ed-226f-40c5-89fd-3fa4e1fe71c1.png)

     - 위는 url에 대한 domain level이다
     - 우선 www.google.com에 대해 `DNS recursor`가 `root name server`에 연락
     - `root name server`는 `.com name server`로 리다이렉트
     - `.com name server`는 `google.com name server`로 리다이렉트
     - `google.com name server`는 DNS 기록에서 www.google.com에 매칭되는 IP주소를 찾고 그 주소를 `DNS recursor`로 보냄

4. 브라우저가 서버와 TCP Connection 수행
5. 브라우저가 웹 서버에 HTTP 요청
6. 서버가 요청을 처리하고 response를 생성
7. 서버가 HTTP Response 보냄
8. 브라우저가 HTML Content를 보여준다



### DHCP & ARP

**DHCP(Dynamic Host Configuration Protocol?**

> 호스트의 IP 주소 및 TCP/IP 설정을 클라이언트에 자동으로 제공하는 프로토콜

![](https://user-images.githubusercontent.com/55429912/120500105-8e81a200-c3fb-11eb-8f41-3b602a36c058.png)

1. 사용자의 PC는 DHCP 서버에서 사용자 자신의 IP주소, 가장 가까운 라우터 IP주소, 가장 가까운 DNS 서버의 IP주소를 받는다
2. ARP 프로토콜을 이용해 IP주소를 기반으로 가장 가까운 라우터의 MAC 주소를 얻는다
   - ARP : 네트워크 상에서 IP주소를 물리적 네트워크 주소로 대응시키기 위해 사용되는 프로토콜
     - 127.0.0.1 -> FFFFFF이런식으로
   - MAC : 네트워크 카드 하드웨어에 부여된 고유 물리주소
3. 2의 과정을 통해 외부와 통신 준비 마친 뒤, DNS Query를 DNS 서버에 송신. 이 결과로 웹 서버의 IP를 요청 PC에 돌려줌
4. HTTP Request를 위해 TCP 소켓을 개발하고 연결(3-way handshake)
5. 이에 대한 응답으로 웹 페이지가 사용자의 PC로 들어옴

## Web Server, WAS의 차이

### Static Pages와 Dynamic Pages

<img src="https://user-images.githubusercontent.com/41468004/140633545-9d5d1af9-cb50-4ff6-ba04-ecb5e2835c4a.png" style="zoom:50%;" />

1. Static Pages
   - Web Server는 파일 경로 이름을 받아 경로와 일치하는 file content를 반환
   - 항상 동일한 내용을 반환한다
   - html, css, js와 같은 파일
2. Dynamic Pages
   - 인자의 내용에 맞게 동적인 contents를 제작해 반환한다

### Web Server와 WAS의 차이

<img src="https://user-images.githubusercontent.com/41468004/140633557-c16582b3-cb0f-4757-8e14-cfc285037185.png" style="zoom:50%;" />

#### Web Server

- Web Server?
  - 소프트웨어와 하드웨어로 구분
  - 하드웨어 : Web Server가 설치되어 있는 컴퓨터
  - 소프트웨어 : 정적인 파일(html, css, js, image, ...)을 제공해주는 프로그램
- Web Server의 기능
  - HTTP 요청을 받아 원하는 서비스를 제공하는 기능
  - 정적인 콘텐츠 요청
    - WAS를 거치지 않고 바로 제공
  - 동적인 콘텐츠 요청
    - 동적인 콘텐츠를 위한 요청 전달
    - 클라의 요청을 WAS에게 전달, WAS로 부터 받은 결과를 클라에게 넘겨줌
- Apache Server, Nginx 등이 대표적이다

#### WAS(Web Application Server)

> DB 조회나 다양한 로직 처리를 요구하는 동적인 콘텐츠를 제공하기 위해서 만들어진 Application Server
>
> Web Container, Servlet Container라고 불리기도 한다

- WAS의 역할
  - Web Server + Container
- 주요 기능
  - 프로그램 실행 및 DB 접속 기능 제공
  - 여러 트랜잭션 관리 기능
  - 비즈니스 로직 수행
- Tomcat, JBoss, JEUS등이 대표적

### Web Server, WAS를 구분하는 이유?

##### Web Server 왜?

- 클라에게 정적 파일을 보내는 과정
  - html이 먼저 보내지고 거기서 필요한 이미지들 요청
  - 해당 정적 파일들은 Application Server까지 가지 않고도 Web Server를 통해 앞단에서 빠르게 처리 가능
- 정적 컨텐츠만 처리하도록 기능을 분배해 서버의 부담 줄일 수 있음

##### WAS의 필요성

- 웹 페이지는 동적, 정적 두가지 존재
- 동적 컨텐츠는 사용자의 요청에 맞게 처리
  - 만약 Web Server에서 이를 다 처리하기에는 모든 경우의 수에 알맞게 결과값을 미리 다 만들어놓아야 한다
- 따라서 WAS를 사용하면 그때그때 요청에 맞는 결과를 생성해 클라에게 제공할 수 있음

##### WAS가 Web Server의 기능을 모두 수행하면 안되나?

- WAS는 DB 조회나 비즈니스 로직 처리 등 복잡한 행동을 많이 한다 그렇기에 단순히 전달만 하면되는 정적인 콘텐츠들은 Web Server에게 일임
- 이로써 부하를 방지해 수행 속도를 향상시킴

## CORS

`CORS? => 교차 출처 리소스 공유, 브라우저에서 다른 출처의 리소스를 공유하는 방법`

### URL 구조

<img src="https://user-images.githubusercontent.com/41468004/140862435-1e3e3db9-60ec-4b0a-af1c-93380a0348d5.png" style="zoom:50%;" />

- 다른 출처의 리소스란 무엇인지 알기 위해 URL 구조부터 파악해보자
- 여기서 같은 출처란? 
  - `Protocol`, `Host`, `Port`가 동일해야 같은 출처이다

### 동일 출처 정책(Same-Origin Policy)이란?

- 브라우저가 동일 출처 정책을 지켜서 다른 출처의 리소스 접근을 금지하는 행위
- `giri.github.io`라는 도메인 주소를 사용하는 웹페이지에서 `giri-api.github.io`라는 API 서버로 데이터를 요청한다면 정책을 위반하는 것

장점

- 외부 리소스를 가져오지 못하지만 XSS, XSRF와 같은 보안 취약점을 노린 공격을 방어할 수 있음
- 이때 외부 리소스를 가져오기 위한 예외 조항이 CORS

### CORS 동작 원리

- 단순 요청 방법, 예비 요청을 보내는 방법 두가지가 존재

### Simple Request

말 그대로 서버에게 바로 요청을 보내는 방법

<img src="https://user-images.githubusercontent.com/41468004/140863015-7ca47d15-bb19-4897-8ee3-6d8af0396fa7.png" style="zoom:50%;" />

- 서버에 API를 요청하고 서버는 Access-Control-Allow-Origin 헤더를 포함한 응답을 브라우저에 보내준다
- 브라우저는 Access-Control-Allow-Origin 헤더를 확인해서 CORS 동작을 수행할지 판단

#### Simple Request 조건

- 3가지 조건을 만족시켜야 서버로 전달하는 요청이 단순 요청으로 동작
  - 요청 메서드는 GET, HEAD, POST 중 하나
  - Accept, Accept-Language, Content-Language, Content-Type, DPR, Downlink, Save-Data, Viewport-Width, Width를 제외한 헤더를 사용하면 안된다
  - Content-Type 헤더는 application/x-www-form-urlencoded, multipart/form-data, text/plain 중 하나만 사용
- 2, 3번 조건이 매우 까다롭다 인증 헤더인 Authrization 헤더가 포함되지 않는다

### Preflight Request

서버에 예비 요청을 보내 안전한지 확인 -> 요청을 보냄

<img src="https://user-images.githubusercontent.com/41468004/140863470-efc4ad7a-3028-4297-afb5-c2a8dcd3b92b.png" style="zoom:50%;" />

- OPTIONS 메서드를 통해 실제 요청을 전송할지 판단
- OPTIONS 메서드로 서버에 예비 요청 보내고 서버는 이 예비 요청에 대한 응답으로 Access-Control-Allow-Origin 헤더를 포함한 응답을 보내준다

### CORS 에러 해결 방법

- Access-Control-Allow-Origin 헤더를 포함시켜 브라우저에게 응답을 보내는 방식으로 해결 가능
- 여러 프레임워크에서는 CORS를 해결해주는 라이브러리를 별도로 제공한다

## www.naver.com을 치면 일어나는 일

<img src="https://user-images.githubusercontent.com/41468004/141037123-8cc38678-0277-4fff-b513-8b03759bdceb.png" style="zoom:50%;" />

1. 사용자가 브라우저에 URL 입력
2. 브라우저는 캐싱된 정보를 통해 www.naver.com에 대응되는 IP 주소를 찾는다
   1. 브라우저 캐시 확인
   2. OS 캐시 확인
   3. 라우터 캐시 확인
   4. ISP 캐시 확인
3. 없다면 ISP의 DNS 서버가 Recursive Query를 날림
4. 결과적으로 찾아낸 IP 주소를 가지고 HTTP 프로토콜을 이용해 HTTP 요청 메시지를 발생시킴
5. 이렇게 생성된 HTTP 요청 메시지는 TCP 프로토콜을 통해 3-way handshake 연결을 맺게 된다
   1. 사용자측에서 내 목소리 잘 들리니? => SYN(x)
   2. 서버측에서 너 목소리 잘들려, 내 목소리는? => ack(x + 1), syn(y)
   3. 사용자측에서 목소리 잘 들립니다 => ack(y + 1)
6. 연결이 완료된 후 전송된 HTTP 요청을 받은 서버는 HTTP 프로토콜을 이용해 HTTP 응답 메시지를 생성
7. HTTP 응답 메시지는 TCP 프로토콜을 통해 사용자측으로 전달된다
8. HTTP 프로토콜을 통해 변환된 HTTP 응답 메시지를 사용자의 브라우저에서 렌더링하여 보여준다